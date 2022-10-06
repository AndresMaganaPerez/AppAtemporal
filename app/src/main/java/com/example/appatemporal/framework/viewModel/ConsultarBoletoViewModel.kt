package com.example.appatemporal.framework.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appatemporal.data.AddRatingRequirement
import com.example.appatemporal.data.GetTicketStateRequirement
import com.example.appatemporal.data.VerifyRatingExistanceRequirement
import com.example.appatemporal.data.addCommentRequirement
import com.example.appatemporal.domain.Repository
import kotlinx.coroutines.launch

class ConsultarBoletoViewModel:ViewModel() {

    private  val getTicketStateRequirement = GetTicketStateRequirement()
    private val addRatingRequirement = AddRatingRequirement()
    private val verifyRatingExistanceRequirement = VerifyRatingExistanceRequirement()
    private val addCommentRequirement = addCommentRequirement()

    val ticketState = MutableLiveData<Boolean>()
    val rateState = MutableLiveData<Boolean>()

    fun getStateTicket(hash_Qr:String, idUser:String, repository: Repository){
        viewModelScope.launch {
            val stateT = getTicketStateRequirement(hash_Qr, repository)
            ticketState.postValue(stateT)

        }
    }

    fun addRating(idUser: String, idEvent : String, rate : Float, repository: Repository) {
        viewModelScope.launch {
            addRatingRequirement(idUser, idEvent, rate, repository)
        }
    }

    fun VerifyRate(idUser: String,idEvent: String,repository: Repository){
        viewModelScope.launch {
            val rState = verifyRatingExistanceRequirement(idUser,idEvent,repository)
            rateState.postValue(rState)
        }
    }
    fun addComment(idUser: String,idEvent: String,comment: String, repository: Repository){
        viewModelScope.launch{
            addCommentRequirement(idUser,idEvent,comment,repository)
        }
    }
}