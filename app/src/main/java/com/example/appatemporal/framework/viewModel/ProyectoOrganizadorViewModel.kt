package com.example.appatemporal.framework.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appatemporal.data.localdatabase.entities.Estatus
import com.example.appatemporal.data.localdatabase.entities.Proyecto
import com.example.appatemporal.data.requirements.ProyectoOrganizadorRequirement
import com.example.appatemporal.domain.Repository
import kotlinx.coroutines.launch

class ProyectoOrganizadorViewModel: ViewModel() {
    private val requirement = ProyectoOrganizadorRequirement()

    val projects = MutableLiveData<List<Proyecto>>()
    val project = MutableLiveData<Proyecto>()
    fun removeProject(proyecto: Proyecto, repository: Repository){
        viewModelScope.launch {
            requirement.deleteProject(proyecto, repository)
            val project = requirement.getProjects(repository)
            projects.setValue(project)
        }
    }

    fun updateModify(name: String, date: String,time:String,id:Int ,repository: Repository){
        viewModelScope.launch {
            requirement.updateMod(name,date,time,id,repository)
        }

    }

    fun getProjects(repository: Repository){
        viewModelScope.launch {
            val project = requirement.getProjects(repository)
            projects.setValue(project)
        }
    }

    fun countPendingActivities(repository: Repository, id_a: Int, stringStatus: String): Int{
        return requirement.countPendingActivities(repository, id_a, stringStatus)
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
    fun getAllProjectsCompleted(stringStatus: Boolean, repository: Repository) {
        viewModelScope.launch {
            val list = requirement.filterProjectsByStatus(stringStatus, repository)
            projects.setValue(list)
        }
    }
}