package com.example.appatemporal.data

import com.example.appatemporal.domain.Repository

class GetEventNameRequirement {
    suspend operator fun invoke(eid: String, repository: Repository) : String {
        return repository.getEventName(eid)
    }
}