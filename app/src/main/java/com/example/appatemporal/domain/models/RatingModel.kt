package com.example.appatemporal.domain.models

import java.util.*

data class RatingModel(
    val id_usuario_fk: String,
    val id_evento_fk: String,
    val rating: Float,
    val fecha_creacion: Date

)
