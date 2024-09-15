package com.example.appatemporal.framework.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appatemporal.data.localdatabase.entities.Actividad
import com.example.appatemporal.data.requirements.AddActivityRequirement
import com.example.appatemporal.domain.Repository
import kotlinx.coroutines.launch

class AddNewActivityViewModel: ViewModel() {
    private val requirement = AddActivityRequirement()
    fun addNewActividad(actividad: Actividad, repository: Repository){
        viewModelScope.launch {
            requirement.createActividad(actividad, repository)
        }


    }
}