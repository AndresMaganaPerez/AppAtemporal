package com.example.appatemporal.data.requirements

import android.util.Log
import com.example.appatemporal.data.localdatabase.entities.Actividad
import com.example.appatemporal.domain.Repository

class AddActivityRequirement {

    suspend fun createActividad(actividad: Actividad, repository:Repository){

        repository.insertActividad(actividad)
       

    }
}