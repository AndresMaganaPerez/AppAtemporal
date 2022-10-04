package com.example.appatemporal.data

import com.example.appatemporal.data.localdatabase.entities.Usuario
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.domain.models.EventModel
import com.example.appatemporal.domain.models.FunctionModel
import java.util.*

class AddEventRequirement {
    suspend fun AddEvent(event:EventModel,repository:Repository, artista: String, funcion: FunctionModel) {
        repository.addEvent2(event, artista, funcion)
}
}
