package com.example.appatemporal.framework.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appatemporal.data.requirements.GetEventCategoryFilterRequirement
import androidx.lifecycle.viewModelScope
import com.example.appatemporal.data.constants.Constantes.Companion.idCategoria
import com.example.appatemporal.domain.Repository
import kotlinx.coroutines.launch


class GetCategoryFilterViewModel : ViewModel(){
    private val getEventCategoryFilter = GetEventCategoryFilterRequirement()
    val dropdownList = MutableLiveData<ArrayList<String>>()

    fun getCategoryFilter(eid:String,repository: Repository){
        viewModelScope.launch {
            val categorias = getEventCategoryFilter(eid,repository)
            val dropdownarray = arrayListOf<String>()
            for (list in categorias){
                dropdownarray.add(list)
            }
            dropdownList.postValue((dropdownarray))
        }
    }

    fun addEventoCategoria(eid: String, idCategoria: String, repository: Repository) {
        viewModelScope.launch {
            getEventCategoryFilter.addEventoCategoria(eid, idCategoria, repository)
        }
    }

}