package com.example.appatemporal.framework.viewModel

import androidx.lifecycle.ViewModel
import com.example.appatemporal.data.localdatabase.entities.Costo
import com.example.appatemporal.data.requirements.TasksCostoRequirement
import com.example.appatemporal.domain.Repository

class DeleteCostoViewModel : ViewModel(){
    private val requirement = TasksCostoRequirement()

    suspend fun removeCosto(costo: Costo, repository: Repository){
        requirement.deleteCosto(costo, repository)
    }
    suspend fun updateCosto(costo: Costo, repository: Repository){
        requirement.updateCosto(costo, repository)
    }

    suspend fun getCosto(repository: Repository, idProject:Int): List<Costo>{
        return requirement.getCostos(repository, idProject)
    }
}