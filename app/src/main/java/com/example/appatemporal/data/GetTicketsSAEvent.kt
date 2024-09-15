package com.example.appatemporal.data

import com.example.appatemporal.domain.Repository

class GetTicketsSAEvent {
    suspend operator fun invoke(eid: String, repository: Repository) : Pair<Int,Int> {
        return repository.getEventTicketsSA(eid)
    }
}