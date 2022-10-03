package com.example.appatemporal.data.requirements

import android.util.Log
import com.example.appatemporal.data.localdatabase.entities.Actividad
import com.example.appatemporal.data.localdatabase.entities.Costo
import com.example.appatemporal.domain.Repository

class AddCostoRequirement {
    suspend fun createCosto(costo: Costo, repository: Repository){
        repository.insertCosto(costo)
        repository.getAllProyectos().forEach {
            Log.d("Proyecto", it.toString())
        }

    }
}