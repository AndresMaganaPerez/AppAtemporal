package com.example.appatemporal.data

import com.example.appatemporal.domain.Repository

class GetPMbyTicketsRequirement {
    suspend operator fun invoke(eid: String, repository: Repository) : MutableMap<String, Int?> {
        return repository.getTicketsbyPM(eid)
    }
}