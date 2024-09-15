package com.example.appatemporal.data

import com.example.appatemporal.domain.Repository

class VerifyTicketExistenceRequirement {
    suspend operator fun invoke(result: String, repository: Repository) : Boolean {
        return repository.verifyTicketExistence(result)
    }
}