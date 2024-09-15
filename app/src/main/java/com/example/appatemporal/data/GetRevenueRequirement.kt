package com.example.appatemporal.data

import com.example.appatemporal.domain.Repository

class GetRevenueRequirement {
    suspend operator fun invoke(uid: String, repository: Repository) : Int {
        return repository.getRevenue(uid)
    }
}