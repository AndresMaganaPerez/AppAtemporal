package com.example.appatemporal.domain.models

class GetEventModel (
    val nombre: String = "",
    val descripcion: String= "",
    val ciudad: String= "",
    val estado: String= "",
    val ubicacion: String= "",
    val direccion: String= "",
    val longitud: String= "",
    val latitud: String= "",
    val imagen: String= "",
    val video: String= "",
    val activo: Int=1,
    val divisa: String= "",
    val fecha_creado: String= "",
    val fecha_modificado: String= "",
)