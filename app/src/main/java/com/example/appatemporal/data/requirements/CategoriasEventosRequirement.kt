package com.example.appatemporal.data.requirements

import android.util.Log
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.domain.models.CategoryModel
import com.example.appatemporal.domain.models.EventModel

class CategoriasEventosRequirement {
    suspend fun getCategories(repository: Repository) : List<CategoryModel>{
        Log.d("Test1", "requirement")
        return repository.getCategories()
    }
}