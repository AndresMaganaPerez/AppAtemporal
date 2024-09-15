package com.example.appatemporal.domain.models

import java.util.*

data class CommentModel(
    val id_usuario_fk: String,
    val id_evento_fk: String,
    val comentario: String,
    val fecha_creacion: Date
)