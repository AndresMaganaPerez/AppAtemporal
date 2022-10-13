package com.example.appatemporal.data

import com.example.appatemporal.domain.Repository
import com.google.firebase.firestore.QuerySnapshot
/**
 * Class that invokes a function when called
 */
class GetMetodoPagoUidRequirement {
    /**
     * Invoke function that asks the repository for the PaymentType UID.
     * @param metodoPago Event Id to get TicketTypes from.
     * @param repository -> Repository of the Application.
     *
     * @return QuerySnapshot -> QuerySnapshot containing the ID of the PaymentType from name.
     */
    suspend operator fun invoke(metodoPago: String, repository: Repository) : QuerySnapshot {
        return repository.getMetodoPagoUid(metodoPago)
    }
}