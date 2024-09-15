package com.example.appatemporal.data.requirements

import com.example.appatemporal.data.localdatabase.entities.Costo
import com.example.appatemporal.domain.Repository

class TasksCostoRequirement {

    suspend fun deleteCosto(costo: Costo, repository: Repository) {
        var costoToDelete = repository.getCostoById(costo.id_costo)
        repository.deleteCosto(costoToDelete)
    }

    suspend fun updateCosto(costo: Costo, repository: Repository){
        var costoToUpdate = repository.getCostoById(costo.id_costo)
        costoToUpdate.nombre_costo = costo.nombre_costo
        costoToUpdate.monto=costo.monto

        repository.updateCosto(costoToUpdate)
    }

    suspend fun getCostos(repository: Repository, idProject:Int): List<Costo>{
        return repository.getAllCostos(idProject)
    }
}