package com.example.appatemporal.data

import com.example.appatemporal.domain.Repository

class ReportFailureRequirement {
    suspend operator fun invoke(title: String, description: String, repository: Repository) {
        repository.addFailure(title, description)
    }
}