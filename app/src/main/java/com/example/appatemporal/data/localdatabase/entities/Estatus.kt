package com.example.appatemporal.data.localdatabase.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "estatus_table")
data class Estatus(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_estatus") val id_estatus: Int,
    @ColumnInfo(name = "nombre_estatus") val nombre_estatus: String,
    @ColumnInfo(name = "created_at") val created_at: String,
    @ColumnInfo(name = "modified_at") val modified_at: String
)
