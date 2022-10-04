package com.example.appatemporal.framework.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appatemporal.data.GetFunctionOrganizerRequirement
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.domain.models.FuncionModel
import kotlinx.coroutines.launch

class GetFunctionOrganizerViewModel : ViewModel() {
    private var getFunctionOrganizerRequirement = GetFunctionOrganizerRequirement()

    val funcion = MutableLiveData<MutableList<FuncionModel>>()

    fun getFunctionOrganizer(eid: String, repository: Repository){
        viewModelScope.launch {
            val funcionAux = getFunctionOrganizerRequirement(eid, repository)
            // Log.d("LOG ViewModel", getUserTicketRequirement(uid, repository).toString())
            funcion.postValue(funcionAux)
        }
    }
}