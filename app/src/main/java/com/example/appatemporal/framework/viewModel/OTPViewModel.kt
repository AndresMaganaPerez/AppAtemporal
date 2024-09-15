package com.example.appatemporal.framework.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appatemporal.data.AddUserLocalDBRequirement
import com.example.appatemporal.data.GetUserDataRequirement
import com.example.appatemporal.data.GetUserRoleRequirement
import com.example.appatemporal.data.ValidateUserExistenceRequirement
import com.example.appatemporal.data.localdatabase.entities.Usuario
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.domain.models.UserModel
import kotlinx.coroutines.launch

class OTPViewModel : ViewModel() {
    val userExists = MutableLiveData<Boolean>()
    val userData = MutableLiveData<Usuario>()

    private val getUserDataRequirement = GetUserDataRequirement()
    private val getUserRoleRequirement = GetUserRoleRequirement()
    private val addUserLocalDBRequirement = AddUserLocalDBRequirement()

    fun verifyUser(uid: String, repository: Repository) {
        viewModelScope.launch {
            val validateUserExistenceRequirement = ValidateUserExistenceRequirement()
            var existence = validateUserExistenceRequirement(uid, repository)
            userExists.postValue(existence)
        }
    }

    fun addUserLocalDB(user: Usuario, repository: Repository) {
        viewModelScope.launch {
            addUserLocalDBRequirement(user, repository)
        }
    }

    fun getUser(uid: String, repository: Repository) {
        viewModelScope.launch {
            val userInfo = getUserDataRequirement(uid, repository).data
            val role = getUserRoleRequirement(uid, repository).data
            val user = Usuario(uid, userInfo?.get("nombre").toString(), userInfo?.get("apellidos").toString(),
                userInfo?.get("email").toString(), userInfo?.get("fecha_nacimiento").toString(), userInfo?.get("genero").toString(), role?.get("nombre").toString())
            userData.postValue(user)
        }
    }
}