package com.example.appatemporal.data.requirements

import com.example.appatemporal.data.localdatabase.entities.Actividad
import com.example.appatemporal.data.localdatabase.entities.Proyecto
import com.example.appatemporal.domain.Repository

class TasksActivityRequirement {

    suspend fun deleteActividad(actividad: Actividad, repository: Repository) {
        var actividadToDelete = repository.getActividadById(actividad.id_actividad)
        repository.deleteActividad(actividadToDelete)
    }

    suspend fun updateActividad(nombre:String, estatus:String, area:String, prioridad:String, id: Int, repository: Repository){
        repository.updateActividad(nombre, estatus, area, prioridad, id)
    }

    suspend fun getActivities(repository: Repository): List<Actividad> {

       return repository.getAllActividades()
    }

    suspend fun getAllActivitiesId(id: Int,repository: Repository): List<Actividad> {
        return repository.getAllActividadById(id)
    }

    suspend fun filterActivitiesByStatus(idProyecto:Int, stringStatus:String ,repository: Repository): List<Actividad> {
        return repository.filterActivitiesByStatus(idProyecto, stringStatus)
    }

    suspend fun filterActivitiesByArea(idProyecto:Int, stringStatus:String ,repository: Repository): List<Actividad> {
        return repository.filterActivitiesByArea(idProyecto, stringStatus)
    }

    suspend fun filterActivitiesByPriority(idProyecto:Int, stringStatus:String ,repository: Repository): List<Actividad> {
        return repository.filterActivitiesByPriority(idProyecto, stringStatus)
    }

    //Things for filter completed projects

    fun countDoneActivities(repository: Repository, id_a: Int, stringStatus: String): Int{
        return repository.countDoneActivities(id_a, stringStatus)
    }

    fun countAllActivities(repository: Repository, id_a: Int): Int{
        return repository.countAllActivities(id_a)
    }

    suspend fun getProyectoById(id: Int, repository: Repository): Proyecto {
        return repository.getProyectoById(id)
    }

    suspend fun updateEstatusCompletado(estatusNew: Boolean, id: Int, repository: Repository){
        repository.updateEstatusCompletado(estatusNew,id)
    }
}