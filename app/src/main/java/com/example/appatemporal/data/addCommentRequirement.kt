package com.example.appatemporal.data

import com.example.appatemporal.domain.Repository

class addCommentRequirement {
    suspend operator fun invoke(idUser: String, idEvent : String, comment : String, repository: Repository) {
        repository.addComment(idUser, idEvent, comment)
    }
}