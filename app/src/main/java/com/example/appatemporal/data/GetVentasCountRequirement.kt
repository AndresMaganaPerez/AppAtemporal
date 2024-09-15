package com.example.appatemporal.data

import com.example.appatemporal.domain.Repository

class GetVentasCountRequirement {
    suspend operator fun invoke(uid: String, repository: Repository) : Pair<Int, Int> {
        return repository.ventasCount(uid)
    }
}