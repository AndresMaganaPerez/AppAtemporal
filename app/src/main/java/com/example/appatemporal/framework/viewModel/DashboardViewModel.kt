package com.example.appatemporal.framework.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appatemporal.data.GetEventCountRequirement
import com.example.appatemporal.data.GetRatingRequirement
import com.example.appatemporal.data.GetRevenueRequirement
import com.example.appatemporal.data.GetVentasCountRequirement
import com.example.appatemporal.domain.Repository
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    val revenue = MutableLiveData<Int>()
    private var getRevenueRequirement = GetRevenueRequirement()
    fun getRevenue(uid: String, repository: Repository) {
        viewModelScope.launch {
            val revenueCount = getRevenueRequirement(uid, repository)
            revenue.postValue(revenueCount)
        }
    }

    val count = MutableLiveData<Int>()
    private var getEventCountRequirement = GetEventCountRequirement()
    fun countEvent(uid: String, repository: Repository) {
        viewModelScope.launch {
            val countEvents = getEventCountRequirement(uid, repository)
            count.postValue(countEvents)
        }
    }

    val ventas = MutableLiveData<Pair<Int, Int>>()
    private var getVentasCountRequirement = GetVentasCountRequirement()
    fun ventasEvent(uid: String, repository: Repository) {
        viewModelScope.launch {
            val countEvents = getVentasCountRequirement(uid, repository)
            ventas.postValue(countEvents)
        }
    }

    val rating = MutableLiveData<Float>()
    private var getRatingRequirement = GetRatingRequirement()
    fun getRating(uid: String, repository: Repository) {
        viewModelScope.launch {
            val ratingCount = getRatingRequirement(uid, repository)
            rating.postValue(ratingCount)
        }
    }

}