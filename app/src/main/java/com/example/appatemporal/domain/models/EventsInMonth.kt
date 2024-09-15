package com.example.appatemporal.domain.models

data class EventsInMonth(
    val idEvent: String,
    val nombreEvento: String,
    val lugarEvento: String,
    val horaInicioEvento: String,
    val urlImagen: String,
    //add properties
    var descripcion: String,
    var ciudad: String,
    var estado: String,
    var direccion: String,
    var longitud: String,
    var latitud: String,
    var video: String,
    var activo: String,
    var aprobado: String,
    var fecha:String
)