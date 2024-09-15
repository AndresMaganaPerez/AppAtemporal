package com.example.appatemporal.framework.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appatemporal.data.*
import com.example.appatemporal.domain.Repository
import kotlinx.coroutines.launch

class DetailedMetricsViewModel : ViewModel() {

    val countPM = MutableLiveData<MutableMap<String, Int?>>()
    private var getPMbyTicketsRequirement = GetPMbyTicketsRequirement()
    fun getPMbyTickets(eid: String, repository: Repository) {
        viewModelScope.launch {
            val PMbyTickets = getPMbyTicketsRequirement(eid, repository)
            countPM.postValue(PMbyTickets)
        }
    }

    val eventName = MutableLiveData<String>()
    private var getEventNameRequirement = GetEventNameRequirement()
    fun getEventName(eid: String, repository: Repository) {
        viewModelScope.launch {
            val event_name = getEventNameRequirement(eid, repository)
            eventName.postValue(event_name)
        }
    }

    val eventProfit = MutableLiveData<Int>()
    private var generalProfitsEvent = GetProfitsEvent()
    fun getEventProfit(eid: String, repository: Repository) {
        viewModelScope.launch {
            val event_profit = generalProfitsEvent(eid, repository)
            eventProfit.postValue(event_profit)
        }
    }

    val eventsTicketsTypeSA = MutableLiveData<MutableMap<String, Pair<Int?, Int?>>>()
    private var getTypeSATickets = GetTypeSATickets()
    fun getTypeSA(eid: String, repository: Repository) {
        viewModelScope.launch {
            val events_TicketsTypeSA = getTypeSATickets(eid, repository)
            eventsTicketsTypeSA.postValue(events_TicketsTypeSA)
        }
    }

    val revenueByPM = MutableLiveData<MutableMap<String, Int?>>()
    private var getRevenuebyPM = GetRevenuebyPM()
    fun getRevenuePM(eid: String, repository: Repository) {
        viewModelScope.launch {
            val eventRevenueByPM = getRevenuebyPM(eid, repository)
            revenueByPM.postValue(eventRevenueByPM)
        }
    }

}