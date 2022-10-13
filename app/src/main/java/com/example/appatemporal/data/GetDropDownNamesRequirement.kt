package com.example.appatemporal.data

import com.example.appatemporal.domain.Repository
/**
 * Class that invokes a function when called
 */
class GetDropDownNamesRequirement {
    /**
     * Invoke function that asks the repository for the Ticket Type for specific event.
     * @param idEvent -> EventId to get TicketTypes of.
     * @param repository -> Repository of the Application.
     *
     * @return List -> Returns a list of Triples, each triple containing the TicketType.
     */
    suspend operator fun invoke(idEvent: String, repository: Repository) : List<Triple<String, Int, String>> {
        return repository.getTicketDropdown(idEvent)
    }
}