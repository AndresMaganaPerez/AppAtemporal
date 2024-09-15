package com.example.appatemporal.data

import com.example.appatemporal.domain.Repository
import com.google.firebase.firestore.QuerySnapshot

class GetMetodoPagoUidRequirement {
    suspend operator fun invoke(metodoPago: String, repository: Repository) : QuerySnapshot {
        return repository.getMetodoPagoUid(metodoPago)
    }
}