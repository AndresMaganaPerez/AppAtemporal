package com.example.appatemporal.data

import android.util.Log
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.domain.models.EventModel01


class GetOrganizerEventRequirement {
    suspend operator fun invoke(uid: String, repository: Repository) : MutableList<EventModel01> {
        val event = repository.getOrganizerEvent(uid)
        // Log.d("LOG Requirement",repository.getUserTickets(uid).toString())
        return event
    }
}