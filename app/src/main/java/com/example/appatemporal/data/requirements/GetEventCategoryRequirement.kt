package com.example.appatemporal.data.requirements

import android.util.Log
import com.example.appatemporal.domain.Repository


class GetEventCategoryRequirement  {
    suspend operator fun invoke( repository: Repository):List<String>{
       return repository.getCategoriaEvento()
    }
}