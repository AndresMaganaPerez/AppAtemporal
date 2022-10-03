package com.example.appatemporal.framework.viewModel

import androidx.lifecycle.ViewModel
import com.example.appatemporal.data.localdatabase.entities.Costo
import com.example.appatemporal.data.requirements.AddCostoRequirement
import com.example.appatemporal.domain.Repository

class AddNewCostoViewModel: ViewModel() {
    private val requirement = AddCostoRequirement()
    suspend fun addNewCosto(costo: Costo, repository: Repository){
        requirement.createCosto(costo, repository)
    }
}