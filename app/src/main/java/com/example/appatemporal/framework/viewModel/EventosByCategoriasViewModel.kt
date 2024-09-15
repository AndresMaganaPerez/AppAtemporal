package com.example.appatemporal.framework.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appatemporal.data.requirements.EventosByCategoriasRequirement
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.domain.models.CategoryModel
import com.example.appatemporal.domain.models.EventModel
import kotlinx.coroutines.launch

class EventosByCategoriasViewModel: ViewModel() {
    private var requirement = EventosByCategoriasRequirement()
    val eventsByCategory = MutableLiveData<List<EventModel>>()
    fun getEventsByCategory(repository: Repository, categoryName:String) {
        viewModelScope.launch {

            val events = requirement.getEvents(repository)
            val categoryId = requirement.getCategoryIdByName(repository, categoryName)
            val eventsIds = requirement.getIdsOfEventosWithidCategoria(categoryId, repository)
            val finalEvents: MutableList<EventModel> = mutableListOf()
            for(event in events){
                if(event.id in eventsIds){
                    finalEvents.add(event)
                }
            }
            eventsByCategory.setValue(finalEvents)
        }
    }

}