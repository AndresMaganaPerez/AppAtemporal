package com.example.appatemporal.data

import com.example.appatemporal.domain.Repository
import com.example.appatemporal.domain.models.EventsInMonth

class GetEventsInMonth {
    suspend operator fun invoke(eD: Int, eM: Int, eY: Int, repository: Repository) : MutableList<EventsInMonth> {
        return repository.getEventsActualMonth(eD,eM,eY)
    }
}