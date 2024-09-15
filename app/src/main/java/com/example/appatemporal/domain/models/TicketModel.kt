package com.example.appatemporal.domain.models

import java.time.LocalDateTime
import java.util.*

data class TicketModel(
    val activo : Boolean,
    val id_Usuario: String,
    val id_Funcion: String,
    val id_Metodo_Pago: String,
    val id_Tipo_Boleto:String,
    val fecha_Compra: Date,
    val fecha_Assistencia: Date
    )
