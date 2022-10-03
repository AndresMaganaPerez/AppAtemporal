package com.example.appatemporal.framework.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appatemporal.data.AddEventRequirement
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.domain.models.EventModel
import kotlinx.coroutines.launch

class AddNewEventViewModel:ViewModel() {
    private val requirement = AddEventRequirement()
    fun AddEvent(event: EventModel, repository: Repository) {
        viewModelScope.launch {
            requirement.AddEvent(event, repository)
        }
    }
}