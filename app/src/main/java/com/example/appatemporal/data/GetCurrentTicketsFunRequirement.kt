package com.example.appatemporal.data

import com.example.appatemporal.domain.Repository

class GetCurrentTicketsFunRequirement {
    suspend operator fun invoke(idEvento: String, idFuncion :String, repository: Repository): List<Triple<String, Int, Int>> {
        return repository.getCurrentTicketsFun(idEvento, idFuncion)
    }
}