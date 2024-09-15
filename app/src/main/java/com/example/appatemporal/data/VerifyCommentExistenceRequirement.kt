package com.example.appatemporal.data

import com.example.appatemporal.domain.Repository

class VerifyCommentExistenceRequirement {
    suspend operator fun invoke(idUser: String, idEvent: String, repository: Repository) : Boolean {
        return repository.verifyCommentExistence(idUser, idEvent)
    }
}