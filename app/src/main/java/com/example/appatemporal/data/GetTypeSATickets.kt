package com.example.appatemporal.data

import com.example.appatemporal.domain.Repository

class GetTypeSATickets {
    suspend operator fun invoke(eid: String, repository: Repository) : MutableMap<String, Pair<Int?, Int?>> {
        return repository.getTicketTypeSA(eid)
    }
}