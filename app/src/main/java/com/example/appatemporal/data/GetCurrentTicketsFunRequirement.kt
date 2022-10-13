package com.example.appatemporal.data

import com.example.appatemporal.domain.Repository


/**
 * Class that invokes a function when called
 */
class GetCurrentTicketsFunRequirement {

    /**
     * Invoke function that asks for the repository to
     * get all tickets registered to a given Function in the given event of a each type.
     *
     * @param idEvento: String -> Event Id to get TicketTypes from.
     * @param idFuncion: String -> Function to take count from.
     * @param repository: Repository -> Repository of the application
     * @return List -> List containing Triples, each containing ticket count of each ticketType.
     * EX: ID, TicketCountOfType, Maximum amount of Tickets specific Type can Have.
     */
    suspend operator fun invoke(idEvento: String, idFuncion :String, repository: Repository): List<Triple<String, Int, Int>> {
        return repository.getCurrentTicketsFun(idEvento, idFuncion)
    }
}