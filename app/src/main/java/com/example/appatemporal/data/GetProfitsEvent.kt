package com.example.appatemporal.data

import com.example.appatemporal.domain.Repository

class GetProfitsEvent {
    suspend operator fun invoke(eid: String, repository: Repository) : Int {
        return repository.generalProfitsEvent(eid)
    }
}