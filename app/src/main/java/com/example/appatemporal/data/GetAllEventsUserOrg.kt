package com.example.appatemporal.data

import com.example.appatemporal.domain.Repository
import com.example.appatemporal.domain.models.EventModel

class GetAllEventsUserOrg {
    suspend operator fun invoke(uid:String, repository: Repository) : MutableList<EventModel> {
        return repository.getEventsUserOrg(uid)
    }
}