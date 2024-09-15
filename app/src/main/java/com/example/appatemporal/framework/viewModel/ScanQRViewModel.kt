package com.example.appatemporal.framework.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appatemporal.data.GetUserLocalDBRequirement
import com.example.appatemporal.data.UpdateTicketValueRequirement
import com.example.appatemporal.data.VerifyRatingExistanceRequirement
import com.example.appatemporal.data.VerifyTicketExistenceRequirement
import com.example.appatemporal.data.localdatabase.entities.Usuario
import com.example.appatemporal.domain.Repository
import kotlinx.coroutines.launch

class ScanQRViewModel : ViewModel() {
    private val updateTicketValueRequirement = UpdateTicketValueRequirement()
    private val verifyTicketExistenceRequirement = VerifyTicketExistenceRequirement()

    fun verifyTicketExistence(resulted: String, repository: Repository, result: (Boolean) -> Unit) {
        viewModelScope.launch {
            val existence = verifyTicketExistenceRequirement(resulted, repository)
            result(existence)
        }
    }

    fun updateTicketValue(resulted: String, repository:Repository, result: (Boolean) -> Unit) {
        viewModelScope.launch {
            val qrHash = updateTicketValueRequirement(resulted, repository)
            result(qrHash)
            //qrcode.postValue(qrHash)
        }
    }
}