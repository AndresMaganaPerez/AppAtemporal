package com.example.appatemporal.framework.viewModel

import androidx.lifecycle.ViewModel
import com.example.appatemporal.data.requirements.AddNewProjectRequirement
import com.example.appatemporal.data.localdatabase.entities.Proyecto
import com.example.appatemporal.domain.Repository


class AddNewProjectViewModel: ViewModel() {
    private val requirement = AddNewProjectRequirement()
    suspend fun addNewProject(proyecto: Proyecto, repository: Repository){

        requirement.createProject(proyecto, repository)


    }

}