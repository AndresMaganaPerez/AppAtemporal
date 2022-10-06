package com.example.appatemporal.framework.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appatemporal.data.GetEEventRating
import com.example.appatemporal.data.GetTicketsSAEvent
import com.example.appatemporal.domain.Repository
import kotlinx.coroutines.launch

class GraphicsEventDetailViewModel : ViewModel() {

    val ratingExt = MutableLiveData<MutableList<Float>>()
    private var getEEventRating = GetEEventRating()
    fun getExtEventRating(eid: String, repository: Repository) {
        viewModelScope.launch {
            val event_rating = getEEventRating(eid, repository)
            ratingExt.postValue(event_rating)
        }
    }

    val eventTicketsSAEvent = MutableLiveData<Pair<Int,Int>>()
    private var getTicketsSAEvent = GetTicketsSAEvent()
    fun getTicketsSA(eid: String, repository: Repository) {
        viewModelScope.launch {
            val eventTSA = getTicketsSAEvent(eid, repository)
            eventTicketsSAEvent.postValue(eventTSA)
        }
    }

}