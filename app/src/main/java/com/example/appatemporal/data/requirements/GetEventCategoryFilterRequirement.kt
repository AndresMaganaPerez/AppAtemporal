package com.example.appatemporal.data.requirements

import com.example.appatemporal.domain.Repository

class GetEventCategoryFilterRequirement {
    suspend operator fun invoke( eid:String, repository: Repository):List<String>{
        return repository.getCategoriaEventoFilter(eid)
    }
    suspend fun addEventoCategoria(eid: String, idCategoria: String, repository: Repository){
        repository.addEventoCategoria(eid, idCategoria)
    }
}