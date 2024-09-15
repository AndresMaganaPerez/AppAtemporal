package com.example.appatemporal.data

import com.example.appatemporal.domain.Repository

class GetEEventRating {
    suspend operator fun invoke(eid: String, repository: Repository) : MutableList<Float> {
        return repository.getRatingByEvent(eid)
    }
}