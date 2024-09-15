package com.example.appatemporal.data

import com.example.appatemporal.data.localdatabase.entities.Usuario
import com.example.appatemporal.domain.Repository

class GetUserLocalDBRequirement {
    suspend operator fun invoke(userUid: String, repository: Repository) : Usuario {
        return repository.getUserLocalDB(userUid)
    }
}