package com.example.appatemporal.data

import com.example.appatemporal.domain.Repository
import com.google.firebase.firestore.QuerySnapshot

class GetCommentsRequirement {
    suspend operator fun invoke(idEvent: String, repository: Repository) : QuerySnapshot {
        return repository.getComments(idEvent)
    }
}