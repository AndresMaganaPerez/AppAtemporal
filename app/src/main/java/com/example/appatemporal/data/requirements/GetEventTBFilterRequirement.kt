package com.example.appatemporal.data.requirements

import com.example.appatemporal.domain.Repository

class GetEventTBFilterRequirement {
    suspend operator fun invoke(eid:String, repository: Repository):List<String>{
        return repository.getEventoTipoBoletoFiltered(eid)
    }
    suspend fun AddEventoTipoBoleto(eid: String, tipoboleto: String, precio: Int, cantidad: Int, repository: Repository){
        repository.AddEventoTipoBoleto(eid, tipoboleto, precio, cantidad)
    }
}