package com.example.appatemporal.data

import com.example.appatemporal.data.constants.Constantes.Companion.fechaFuncion
import com.example.appatemporal.data.localdatabase.entities.Usuario
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.domain.models.CreateEventModel
import com.example.appatemporal.domain.models.EventModel
import com.example.appatemporal.domain.models.EventoTipoBoletoModel
import com.example.appatemporal.domain.models.FunctionModel
import com.example.appatemporal.framework.view.AddArtist
import java.util.*

class AddEventRequirement {
    suspend fun AddEvent(event:CreateEventModel,repository:Repository, artista: String, funcion: FunctionModel, userUid: String,boletos: EventoTipoBoletoModel, cid: String) {
        repository.addEvent2(event, artista, funcion, userUid, boletos, cid)
}
    suspend fun AddFunction(event: String, repository: Repository, fechaFuncion: String, HoraInicio:String, HoraFin:String) {
        repository.addFunction(event, fechaFuncion, HoraInicio, HoraFin)
    }
    suspend fun AddArtista(eid:String, repository: Repository, artista: String) {
        repository.addArtista(eid, artista)
    }

}
