package com.example.appatemporal.domain.models

data class EventModel (
    var id: String,
    var nombre: String,
    var descripcion: String,
    var ciudad: String,
    var estado: String,
    var ubicacion: String,
    var direccion: String,
    var longitud: String,
    var latitud: String,
    var foto_portada: String,
    var video: String,
    var activo: String,
    var aprobado: String
)