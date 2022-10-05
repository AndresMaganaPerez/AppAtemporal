package com.example.appatemporal.framework.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appatemporal.data.AddEventRequirement
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.domain.models.EventModel
import com.example.appatemporal.domain.models.EventoTipoBoletoModel
import com.example.appatemporal.domain.models.FunctionModel
import com.example.appatemporal.framework.view.AddArtist
import kotlinx.coroutines.launch

class AddNewEventViewModel:ViewModel() {
    private val requirement = AddEventRequirement()
    fun AddEvent(event: EventModel, repository: Repository, artista: String, funcion: FunctionModel, userUid: String,boletos: EventoTipoBoletoModel, cid:String) {
        viewModelScope.launch {
            requirement.AddEvent(event, repository, artista, funcion, userUid, boletos, cid)
        }
    }
    fun AddFunction(event: String, repository: Repository, fechaFuncion: String, HoraInicio:String, HoraFin:String) {
        viewModelScope.launch {
            requirement.AddFunction(event, repository, fechaFuncion, HoraInicio,HoraFin)
        }
    }
    fun AddArtista(eid:String, repository: Repository, artista: String) {
        viewModelScope.launch {
            requirement.AddArtista(eid, repository, artista)
        }
    }

}