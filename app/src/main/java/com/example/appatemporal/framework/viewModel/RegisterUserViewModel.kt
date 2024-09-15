package com.example.appatemporal.framework.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appatemporal.data.AddUserLocalDBRequirement
import com.example.appatemporal.data.RegisterUserRequirement
import com.example.appatemporal.data.localdatabase.entities.Usuario
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.domain.models.UserModel
import kotlinx.coroutines.launch

class RegisterUserViewModel : ViewModel() {

    val registerUserRequirement = RegisterUserRequirement()
    val addUserLocalDBRequirement = AddUserLocalDBRequirement()

    fun addUser(uid: String ,user: UserModel, role: String, repository: Repository) {
        viewModelScope.launch {
            registerUserRequirement(uid, user, role, repository)
        }
    }

    fun addUserLocalDB(user: Usuario, repository: Repository) {
        viewModelScope.launch {
            addUserLocalDBRequirement(user, repository)
        }
    }
}