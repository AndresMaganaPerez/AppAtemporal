package com.example.appatemporal.data

import com.example.appatemporal.domain.Repository

class GetDropDownNamesRequirement {

    suspend operator fun invoke(idEvent: String, repository: Repository) : List<Triple<String, Int, String>> {
        return repository.getTicketDropdown(idEvent)
    }
}