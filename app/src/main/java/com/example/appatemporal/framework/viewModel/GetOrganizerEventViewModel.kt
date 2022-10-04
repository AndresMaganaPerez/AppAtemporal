package com.example.appatemporal.framework.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appatemporal.data.GetOrganizerEventRequirement
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.domain.models.EventModel01
import kotlinx.coroutines.launch

class GetOrganizerEventViewModel : ViewModel() {

    private val getOrganizerEventRequirement = GetOrganizerEventRequirement()

    val event = MutableLiveData<MutableList<EventModel01>>()

    fun getOrganizerEvent(uid: String, repository: Repository){
        viewModelScope.launch {
            val eventAux = getOrganizerEventRequirement(uid, repository)
            // Log.d("LOG ViewModel", getUserTicketRequirement(uid, repository).toString())
            event.postValue(eventAux)
        }
    }
}