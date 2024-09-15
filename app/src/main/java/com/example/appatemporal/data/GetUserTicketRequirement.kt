package com.example.appatemporal.data

import android.util.Log
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.domain.models.GetTicketModel

class GetUserTicketRequirement {

    suspend operator fun invoke(uid: String, repository:Repository) : MutableList<GetTicketModel> {
        val ticket = repository.getUserTickets(uid)
        Log.d("LOG Requirement",repository.getUserTickets(uid).toString())
        return ticket
    }
}