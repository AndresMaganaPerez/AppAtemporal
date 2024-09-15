package com.example.appatemporal.data.requirements

import com.example.appatemporal.data.localdatabase.entities.Objetivo
import com.example.appatemporal.data.localdatabase.entities.Proyecto
import com.example.appatemporal.domain.Repository

class PresupuestoOrganizadorRequirement {

    suspend fun updatePresupuestos(presupuestoNew: Double, id: Int, repository: Repository){
        repository.updatePresupuesto(presupuestoNew,id)
    }
    suspend fun getProyectoById(id: Int, repository: Repository): Proyecto {
        return repository.getProyectoById(id)
    }
    suspend fun updateMeta(metaNew: Double, id: Int, repository: Repository){
        repository.updateMeta(metaNew,id)
    }
}