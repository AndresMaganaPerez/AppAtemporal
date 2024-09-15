package com.example.appatemporal.data

import com.example.appatemporal.domain.Repository

class GetRatingRequirement {
    suspend operator fun invoke(uid: String, repository: Repository) : Float {
        return repository.getRating(uid)
    }
}
