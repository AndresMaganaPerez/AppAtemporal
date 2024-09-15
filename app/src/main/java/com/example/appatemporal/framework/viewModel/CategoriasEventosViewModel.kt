package com.example.appatemporal.framework.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appatemporal.data.localdatabase.entities.Proyecto
import com.example.appatemporal.data.requirements.CategoriasEventosRequirement
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.domain.models.CategoryModel
import kotlinx.coroutines.launch

class CategoriasEventosViewModel: ViewModel() {
    private val requirement = CategoriasEventosRequirement()
    val categories = MutableLiveData<List<CategoryModel>>()
    fun getCategories(repository: Repository)  {

        viewModelScope.launch {
          val categorias =  requirement.getCategories(repository)
            categories.setValue(categorias)
        }

    }
}
