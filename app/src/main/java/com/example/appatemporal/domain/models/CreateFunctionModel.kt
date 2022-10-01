package com.example.appatemporal.domain.models

import java.util.*

data class CreateFunctionModel(
    var fecha_fun : Date,
    var hora_inicio : String,
    var hora_fin : String,
)
