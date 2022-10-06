package com.example.appatemporal.data.requirements

import com.example.appatemporal.domain.Repository
import com.example.appatemporal.domain.models.EventModel

class GetAllEvents {
    suspend fun getAllEvents(repository: Repository): MutableList<EventModel>{
        return repository.getEvents()
    }
}