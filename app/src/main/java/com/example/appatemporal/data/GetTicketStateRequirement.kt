package com.example.appatemporal.data

import com.example.appatemporal.domain.Repository

class GetTicketStateRequirement {

    suspend operator fun invoke(hash_Qr:String,repository: Repository): Boolean{
        return repository.getState(hash_Qr)
    }
}