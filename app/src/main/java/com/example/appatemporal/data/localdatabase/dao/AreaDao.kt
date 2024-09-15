package com.example.appatemporal.data.localdatabase.dao

import androidx.room.*
import com.example.appatemporal.data.localdatabase.entities.Area

@Dao
interface AreaDao {
    // Get all areas
    @Query("SELECT * FROM area_table")
    suspend fun getAll(): List<Area>

    // Insert all areas
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(areas: List<Area>)

    // Insert one area
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(area: Area)

    // Get area by id
    @Query("SELECT * FROM area_table WHERE id_area = :id")
    suspend fun getById(id: Int): Area

    // Delete all areas
    @Query("DELETE FROM area_table")
    suspend fun deleteAll()

    // Delete an area
    @Delete
    suspend fun delete(area: Area)
}