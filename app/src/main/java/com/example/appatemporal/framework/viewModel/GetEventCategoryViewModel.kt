package com.example.appatemporal.framework.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appatemporal.data.requirements.GetEventCategoryRequirement
import com.example.appatemporal.domain.Repository
import kotlinx.coroutines.launch

class GetEventCategoryViewModel :ViewModel(){
    private val geteventcategory = GetEventCategoryRequirement()
    val dropdownList = MutableLiveData<ArrayList<String>>()
    
    fun getcategoria(repository: Repository){
        viewModelScope.launch {
            val categorias = geteventcategory(repository)
            val dropdownarray = arrayListOf<String>()
            for(list in categorias){
              dropdownarray.add(list)
            }
            dropdownList.postValue(dropdownarray)
        }
    }
}
