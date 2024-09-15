package com.example.appatemporal.framework.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appatemporal.data.localdatabase.entities.Proyecto
import com.example.appatemporal.data.requirements.PresupuestoOrganizadorRequirement
import com.example.appatemporal.domain.Repository
import kotlinx.coroutines.launch

class PresupuestoOrganizadorViewModel: ViewModel()  {

        private val requirement = PresupuestoOrganizadorRequirement()
        val project = MutableLiveData<Proyecto>()
        fun updatePrespuesto(presupuestoNew: Double, id: Int, repository: Repository){
            viewModelScope.launch {
                requirement.updatePresupuestos(presupuestoNew, id, repository)
                val proyecto = requirement.getProyectoById(id, repository)
                project.postValue(proyecto)
            }
        }
        fun getProyectoByid(id: Int, repository: Repository){
            viewModelScope.launch {
                val proyecto = requirement.getProyectoById(id, repository)
                project.postValue(proyecto)

            }
        }

        fun updateMeta(metaNew: Double, id: Int, repository: Repository){
            viewModelScope.launch {
                requirement.updateMeta(metaNew, id, repository)
                val proyecto = requirement.getProyectoById(id, repository)
                project.postValue(proyecto)
            }
        }

}