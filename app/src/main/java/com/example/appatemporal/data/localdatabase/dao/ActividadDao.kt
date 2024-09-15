package com.example.appatemporal.data.localdatabase.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.appatemporal.data.localdatabase.entities.Actividad
import com.example.appatemporal.data.localdatabase.entities.Proyecto


@Dao
interface ActividadDao {

    // Get all activities
    @Query("SELECT * FROM actividad_table WHERE id_proyecto = :id")
    suspend fun getAllActivityId(id: Int): List<Actividad>

    // Get all activities
    @Query("SELECT * FROM actividad_table")
    suspend fun getAll(): List<Actividad>

    // Insert all activities
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(activities: List<Actividad>)

    // Insert one activity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(actividad: Actividad)

    // Get activity by id
    @Query("SELECT * FROM actividad_table WHERE id_actividad = :id")
    suspend fun getById(id: Int): Actividad

    // Delete all activities
    @Query("DELETE FROM actividad_table")
    suspend fun deleteAll()

    //Count activities by status
    @Query("SELECT COUNT(*) FROM actividad_table WHERE id_proyecto = :id_a AND estatus = :stringStatus")
    suspend fun countPendingActivities(id_a: Int, stringStatus: String): Int

    //Count activities by status
    @Query("SELECT COUNT(*) FROM actividad_table WHERE id_proyecto = :id_a AND estatus = :stringStatus")
    suspend fun countDoneActivities(id_a: Int, stringStatus: String): Int
    
    //Filter activities by status
    @Query("SELECT * FROM actividad_table WHERE id_proyecto = :id_pro AND estatus = :stringStatus")
    suspend fun FilterActivityByStatus (id_pro: Int, stringStatus: String): List<Actividad>

    //Count all activities
    @Query("SELECT COUNT(*) FROM actividad_table WHERE id_proyecto = :id_a")
    suspend fun countAllActivities(id_a: Int): Int

    //Filter activities by area
    @Query("SELECT * FROM actividad_table WHERE id_proyecto = :id_pro AND area = :stringArea")
    suspend fun FilterActivityByArea (id_pro: Int, stringArea: String): List<Actividad>

    //Filter activities by priority
    @Query("SELECT * FROM actividad_table WHERE id_proyecto = :id_pro AND prioridad = :stringPriority")
    suspend fun FilterActivityByPriority (id_pro: Int, stringPriority: String): List<Actividad>

    // Delete an activity
    @Delete
    suspend fun delete(actividad: Actividad)

    // Update a Actividad
    @Query("UPDATE actividad_table SET nombre_actividad = :nombre,  estatus = :estatus, area = :area, prioridad = :prioridad  WHERE id_actividad = :id")
    suspend fun update(nombre:String, estatus:String, area:String, prioridad:String, id: Int)


}