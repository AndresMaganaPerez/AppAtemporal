package com.example.appatemporal.data.localdatabase.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "area_table", foreignKeys = [ForeignKey(entity = Actividad::class, parentColumns = ["id_actividad"], childColumns = ["id_actividad"])])
data class Area(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_area") val id_area: Int = 0,
    @ColumnInfo(name = "id_actividad") val id_actividad: Int,
    @ColumnInfo (name = "nombre_area") val nombre_area: String,
    @ColumnInfo (name = "created_at") val created_at: String,
    @ColumnInfo (name = "modified_at") val modified_at: String,
)
