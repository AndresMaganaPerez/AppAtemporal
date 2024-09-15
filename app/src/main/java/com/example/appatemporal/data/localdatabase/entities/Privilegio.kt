package com.example.appatemporal.data.localdatabase.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "privilegio_table")
data class Privilegio(
    @PrimaryKey(autoGenerate = true) val idPrivilegio: Int = 0,
    @ColumnInfo(name = "privilegio") val privilegio: String
)
