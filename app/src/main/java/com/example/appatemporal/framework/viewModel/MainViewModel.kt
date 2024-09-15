package com.example.appatemporal.framework.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appatemporal.data.GetUserDataRequirement
import com.example.appatemporal.data.GetUserLocalDBRequirement
import com.example.appatemporal.data.GetUserRoleRequirement
import com.example.appatemporal.data.localdatabase.entities.Usuario
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.domain.models.UserModel
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val userData = MutableLiveData<Usuario>()
    private val getUserLocalDBRequirement = GetUserLocalDBRequirement()

    fun getUserLocalDB(uid: String, repository: Repository) {
        viewModelScope.launch {
            val user = getUserLocalDBRequirement(uid, repository)
            userData.postValue(user)
        }
    }
}