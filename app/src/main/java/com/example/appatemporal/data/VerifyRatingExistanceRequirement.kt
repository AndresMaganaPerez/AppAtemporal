package com.example.appatemporal.data

import com.example.appatemporal.domain.Repository

class VerifyRatingExistanceRequirement {
    suspend operator fun  invoke(idUser:String, idEvent:String,repository: Repository ):Boolean{
        return repository.verifyRatingExistence(idUser,idEvent)
    }
}