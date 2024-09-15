package com.example.appatemporal.framework.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appatemporal.data.localdatabase.entities.Actividad
import com.example.appatemporal.data.localdatabase.entities.Estatus
import com.example.appatemporal.data.localdatabase.entities.Proyecto
import com.example.appatemporal.data.requirements.TasksActivityRequirement
import com.example.appatemporal.domain.Repository
import kotlinx.coroutines.launch

class DeleteActivityViewModel : ViewModel(){
    private val requirement = TasksActivityRequirement()
    val activities = MutableLiveData<List<Actividad>>()
    val projects = MutableLiveData<List<Proyecto>>()
    val project = MutableLiveData<Proyecto>()

    fun removeActividad(id:Int, actividad: Actividad, repository: Repository){
        viewModelScope.launch {
            requirement.deleteActividad(actividad, repository)
            val list = requirement.getAllActivitiesId(id,repository)
            activities.setValue(list)
        }
    }
    fun updateActividad(idProyecto:Int, nombre:String, estatus:String, area:String, prioridad:String, id: Int, repository: Repository){
        viewModelScope.launch {
            requirement.updateActividad(nombre, estatus, area, prioridad, id, repository)
        }
    }

    fun getActivities(repository: Repository){
        viewModelScope.launch {
            val list = requirement.getActivities(repository)
            activities.setValue(list)

        }
    }
    fun getAllActivitiesid(id:Int, repository: Repository){
        viewModelScope.launch {
            val list = requirement.getAllActivitiesId(id,repository)
            activities.setValue(list)
        }
    }
    fun getAllActivitiesPrioridad(id:Int, prioridad: String, repository: Repository) {
        viewModelScope.launch {
            val list = requirement.filterActivitiesByPriority(id,prioridad, repository)
            activities.setValue(list)
        }
    }

    fun getAllActivitiesArea(id:Int, area: String, repository: Repository) {
        viewModelScope.launch {
            val list = requirement.filterActivitiesByArea(id,area, repository)
            activities.setValue(list)
        }
    }

    fun getAllActivitiesEstatus(id:Int, area: String, repository: Repository) {
        viewModelScope.launch {
            val list = requirement.filterActivitiesByStatus(id,area, repository)
            activities.setValue(list)
        }
    }

    fun countDoneActivities(repository: Repository, id_a: Int, stringStatus: String): Int{
        return requirement.countDoneActivities(repository, id_a, stringStatus)
    }

    fun countAllActivities(repository: Repository, id_a: Int): Int{
        return requirement.countAllActivities(repository, id_a)
    }

    fun updateEstatusCompletado(estatusNew: Boolean, id: Int, repository: Repository){
        viewModelScope.launch {
            requirement.updateEstatusCompletado(estatusNew, id, repository)
            val proyecto = requirement.getProyectoById(id, repository)
            project.postValue(proyecto)
        }
    }

}
