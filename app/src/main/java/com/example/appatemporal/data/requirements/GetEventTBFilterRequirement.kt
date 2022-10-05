package com.example.appatemporal.data.requirements

import com.example.appatemporal.domain.Repository

class GetEventTBFilterRequirement {
    suspend operator fun invoke( eid:String, repository: Repository):List<String>{
        return repository.getCategoriaEventoFilter(eid)
    }
    suspend fun getEventoTipoBoletoFiltered(eid: String, idCategoria: String, repository: Repository){
        repository.addEventoCategoria(eid, idCategoria)
    }
}