package com.example.appatemporal.data.localdatabase.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "objetivo_table", foreignKeys = [ForeignKey(entity = Proyecto::class, parentColumns = ["id_proyecto"], childColumns = ["id_proyecto"])])
data class Objetivo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_objetivo") val id_estatus: Int,
    @ColumnInfo(name = "id_proyecto") val id_proyecto: Int,
    @ColumnInfo(name = "ganancia") val ganancia: Double,
    @ColumnInfo(name = "presupuesto") val presupuesto: Double,
    @ColumnInfo(name = "created_at") val created_at: String,
    @ColumnInfo(name = "modified_at") val modified_at: String
)
