package com.example.appatemporal.data.requirements

import com.example.appatemporal.domain.Repository
import com.example.appatemporal.domain.models.CategoryModel
import com.example.appatemporal.domain.models.EventModel

class EventosByCategoriasRequirement {
    suspend fun getEvents(repository: Repository): List<EventModel>{
        return repository.getEvents()
    }

    suspend fun getCategoryIdByName(repository: Repository, name: String): String{
        return repository.getCategoryIdByName(name)
    }

    suspend fun getIdsOfEventosWithidCategoria(idCategoria: String, repository: Repository): List<String>{
        return repository.getIdsOfEventosWithidCategoria(idCategoria)
    }

}