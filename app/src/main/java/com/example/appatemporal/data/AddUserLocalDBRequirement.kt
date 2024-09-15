package com.example.appatemporal.data

import com.example.appatemporal.data.localdatabase.entities.Usuario
import com.example.appatemporal.domain.Repository

class AddUserLocalDBRequirement {
    suspend operator fun invoke(user: Usuario, repository: Repository) {
        repository.addUserLocalDB(user)
    }
}