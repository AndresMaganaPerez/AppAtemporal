package com.example.appatemporal.data.localdatabase.dao

import androidx.room.*
import com.example.appatemporal.data.localdatabase.entities.Actividad
import com.example.appatemporal.data.localdatabase.entities.Costo

@Dao
interface CostoDao {
    // Get all costos
    @Query("SELECT * FROM costo_table where id_proyecto = :id")
    suspend fun getAll(id: Int): List<Costo>

    // Insert all costos
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(costos: List<Costo>)

    // Insert one costo
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(costo: Costo)

    // Get costo by id
    @Query("SELECT * FROM costo_table WHERE id_costo = :id")
    suspend fun getById(id: Int): Costo

    // Delete all costos
    @Query("DELETE FROM costo_table")
    suspend fun deleteAll()

    // Delete a costo
    @Delete
    suspend fun delete(costo: Costo)

    @Update
    suspend fun update(costo: Costo)

}