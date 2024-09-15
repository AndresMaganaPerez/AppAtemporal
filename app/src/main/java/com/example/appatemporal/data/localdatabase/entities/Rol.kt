package com.example.appatemporal.data.localdatabase.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rol_table")
data class Rol(
    @PrimaryKey(autoGenerate = true) val idRol: Int = 0,
    @ColumnInfo(name = "rol") val rol: String
)
