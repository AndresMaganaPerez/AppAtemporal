package com.example.appatemporal.data.requirements

import android.util.Log
import com.example.appatemporal.data.localdatabase.entities.Proyecto
import com.example.appatemporal.domain.Repository

class AddNewProjectRequirement {

    suspend fun createProject(project: Proyecto, repository:Repository){

        repository.insertProyecto(project)
        repository.getAllProyectos().forEach {
            Log.d("Proyecto", it.toString())
        }

    }
}