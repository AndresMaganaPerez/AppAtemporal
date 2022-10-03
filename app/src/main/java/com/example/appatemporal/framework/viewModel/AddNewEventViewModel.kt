package com.example.appatemporal.framework.viewModel

import androidx.lifecycle.ViewModel
import com.example.appatemporal.data.AddEventRequirement
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.domain.models.EventModel

class AddNewEventViewModel: ViewModel() {
    private val requirement = AddEventRequirement()
    suspend fun AddEvent(event:EventModel, repository: Repository){
        requirement.AddEvent(event,repository)

    }

}