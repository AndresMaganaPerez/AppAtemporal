package com.example.appatemporal.data

import com.example.appatemporal.domain.Repository

class GetRevenuebyPM {
    suspend operator fun invoke(eid: String, repository: Repository) : MutableMap<String, Int?> {
        return repository.getRevenuebyPM(eid)
    }
}