package com.example.appatemporal.data

import com.example.appatemporal.domain.Repository
import com.google.firebase.firestore.DocumentSnapshot

class GetEventCountRequirement {
    suspend operator fun invoke(uid: String, repository: Repository) : Int {
        return repository.eventCount(uid)
    }
}