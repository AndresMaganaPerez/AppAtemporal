package com.example.appatemporal.framework.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appatemporal.data.ReportFailureRequirement
import com.example.appatemporal.domain.Repository
import kotlinx.coroutines.launch

class ReportFailureViewModel : ViewModel() {
    private val reportFailureRequirement = ReportFailureRequirement()

    fun addFailure(title: String, description: String, repository: Repository) {
        viewModelScope.launch {
            reportFailureRequirement(title, description, repository)
        }
    }

}