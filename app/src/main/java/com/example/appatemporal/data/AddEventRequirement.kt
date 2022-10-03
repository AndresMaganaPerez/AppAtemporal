package com.example.appatemporal.data

import com.example.appatemporal.data.localdatabase.entities.Usuario
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.domain.models.EventModel
import java.util.*

class AddEventRequirement {
    suspend fun AddEvent(event:EventModel,repository:Repository) {
        repository.addEvent2(event)
}
}
