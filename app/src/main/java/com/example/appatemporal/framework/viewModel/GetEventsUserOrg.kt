package com.example.appatemporal.framework.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appatemporal.data.GetAllEventsUserOrg
import com.example.appatemporal.data.requirements.GetAllEvents
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.domain.models.EventModel
import kotlinx.coroutines.launch

class GetEventsUserOrg : ViewModel() {

    val allEventsOrg = MutableLiveData<MutableList<EventModel>>()
    private var getAllEventsUserOrg = GetAllEventsUserOrg()
    fun getEventsOrg(uid:String, repository: Repository) {
        viewModelScope.launch {
            val eventsOrg = getAllEventsUserOrg(uid,repository)
            allEventsOrg.postValue(eventsOrg)
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