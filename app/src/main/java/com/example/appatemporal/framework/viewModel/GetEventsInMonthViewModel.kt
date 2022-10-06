package com.example.appatemporal.framework.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appatemporal.data.GetEventsInMonth
import com.example.appatemporal.data.requirements.GetAllEvents
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.domain.models.EventModel
import com.example.appatemporal.domain.models.EventsInMonth
import kotlinx.coroutines.launch

class GetEventsInMonthViewModel : ViewModel()  {

    val eventsMonth = MutableLiveData<MutableList<EventsInMonth>>()
    private var getEventsInMonth = GetEventsInMonth()
    fun getEventsMonth(eD: Int, eM: Int, eY: Int , repository: Repository) {
        viewModelScope.launch {
            val eventsInMonth = getEventsInMonth(eD,eM, eY,repository)
            eventsMonth.postValue(eventsInMonth)
        }
    }

    val allEvents = MutableLiveData<MutableList<EventModel>>()
    private var requirement = GetAllEvents()
    fun getAllEvents(repository: Repository){
        viewModelScope.launch {
            val eventsGeneral = requirement.getAllEvents(repository)
            allEvents.setValue(eventsGeneral)
        }
    }
}