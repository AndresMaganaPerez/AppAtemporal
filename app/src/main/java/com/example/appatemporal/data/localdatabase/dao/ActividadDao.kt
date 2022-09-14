package com.example.appatemporal.data.localdatabase.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.appatemporal.data.localdatabase.entities.Actividad

@Dao
interface ActividadDao {

    // Get all activities
    @Query("SELECT * FROM actividad_table")
    fun getAll(): List<Actividad>

    // Insert all activities
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(activities: List<Actividad>)

    // Insert one activity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(actividad: Actividad)

    // Get activity by id
    @Query("SELECT * FROM actividad_table WHERE id_actividad = :id")
    fun getTask(id: Int): Actividad

    @Query("Update actividad_table Set isFinished = 1 where id_actividad=:uid")
    fun finishTask(uid:Long)

    @Query("Delete from actividad_table where id_actividad=:uid")
    fun deleteTask(uid:Long)

    // Delete all activities
    @Query("DELETE FROM actividad_table")
    suspend fun deleteAll()

    @Query("Select * from actividad_table where isFinished == 0 and id_proyecto_fk = :idProyecto")
    suspend fun getTasks(idProyecto:Int):List<Actividad>
}