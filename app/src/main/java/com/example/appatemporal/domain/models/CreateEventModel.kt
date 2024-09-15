package com.example.appatemporal.domain.models

import java.util.*

/**
 * This class is a model that will contain the data necessary
 * in order to add a new event successfully in the data base Firebase
 *
 * @param nombre:String
 * @param descripcion:String
 * @param ciudad:String
 * @param estado:String
 * @param ubicacion:String
 * @param direccion:String
 * @param longitud:String
 * @param latitud:String
 * @param foto_portada:String
 * @param video:String
 * @param activo:Int
 * @param aprobado:Int
 * @param divisa:String
 * @param fecha_creado:String
 * @param fecha_modificado:String
 *
 * @author Resendiz & Camalich
 *
 * */
data class CreateEventModel(
    val nombre: String,
    val descripcion: String,
    val ciudad: String,
    val estado: String,
    val ubicacion: String,
    val direccion: String,
    val longitud: String,
    val latitud: String,
    val foto_portada: String,
    val video: String,
    val activo: Int,
    val aprobado: Int,
    val divisa: String,
    val fecha_creado: String,
    val fecha_modificado: String
)

