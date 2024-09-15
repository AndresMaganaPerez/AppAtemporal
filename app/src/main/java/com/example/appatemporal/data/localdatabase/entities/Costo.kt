package com.example.appatemporal.data.localdatabase.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
//, foreignKeys = [ForeignKey(entity = Proyecto::class, parentColumns = ["id_proyecto"], childColumns = ["id_proyecto"])]
@Entity(tableName = "costo_table")
data class Costo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_costo") val id_costo : Int,
    @ColumnInfo(name = "nombre_costo") var nombre_costo : String,
    @ColumnInfo(name = "monto") var monto : Int,
    @ColumnInfo(name = "id_proyecto") val id_proyecto : Int,
    //@ColumnInfo(name = "created_at") val created_at : String,
    //@ColumnInfo(name = "modified_at") val modified_at : String,
)
