package com.example.appatemporal.data

import com.example.appatemporal.domain.Repository

class AddRatingRequirement {
    suspend operator fun invoke(idUser: String, idEvent : String, rate : Float, repository: Repository) {
        repository.addRating(idUser, idEvent, rate)
    }


}