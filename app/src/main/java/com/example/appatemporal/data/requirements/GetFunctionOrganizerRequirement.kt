package com.example.appatemporal.data

import android.util.Log
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.domain.models.FuncionModel
import com.example.appatemporal.domain.models.GetTicketModel

class GetFunctionOrganizerRequirement {
    suspend operator fun invoke(eid: String, repository: Repository) : MutableList<FuncionModel> {
        val funcion = repository.getFunctionOrganizer(eid)
        // Log.d("LOG Requirement",repository.getUserTickets(uid).toString())
        return funcion
    }
}