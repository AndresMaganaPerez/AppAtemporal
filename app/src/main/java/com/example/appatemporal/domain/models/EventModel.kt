package com.example.appatemporal.domain.models

import java.util.*

data class EventModel(
    val nombre: String,
    val desc: String,
    val cd: String,
    val est: String,
    val ubi: String,
    val dir: String,
    val long: String,
    val lat: String,
    val foto: String,
    val vid: String,
    val activo: Boolean,
    val divisa: String,
    val fcreado: Date,
    val factualizado: Date
)
