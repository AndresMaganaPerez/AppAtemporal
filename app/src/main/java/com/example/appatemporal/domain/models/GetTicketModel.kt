package com.example.appatemporal.domain.models

data class GetTicketModel(
    var id_evento: String,
    var nombre_evento: String,
    //var foto_evento: String,
    var fecha: String,
    var horario: String,
    var lugar: String,
    var direccion: String,
    var ciudad: String,
    var estado: String,
    var hash_qr: String
)
