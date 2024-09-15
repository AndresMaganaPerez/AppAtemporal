package com.example.appatemporal.framework.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appatemporal.data.requirements.GetEventTBFilterRequirement
import com.example.appatemporal.domain.Repository
import kotlinx.coroutines.launch

class GetEventTBFilterViewModel : ViewModel() {
    private val getEventtbFilter = GetEventTBFilterRequirement()
    val dropdownList = MutableLiveData<ArrayList<String>>()

    fun getTBFilter(eid:String,repository: Repository){
        viewModelScope.launch {
            val tb = getEventtbFilter(eid,repository)
            val dropdownarray = arrayListOf<String>()
            for (list in tb){
                dropdownarray.add(list)
            }
            dropdownList.postValue((dropdownarray))
        }
    }

    fun AddEventoTipoBoleto(eid: String, tipoboleto: String, precio: Int, cantidad: Int, repository: Repository) {
        viewModelScope.launch {
            getEventtbFilter.AddEventoTipoBoleto(eid, tipoboleto, precio, cantidad, repository)
        }
    }
}