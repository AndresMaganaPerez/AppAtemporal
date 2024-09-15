package com.example.appatemporal.data.localdatabase.dao

import androidx.room.*
import com.example.appatemporal.data.localdatabase.entities.Proyecto

@Dao
interface ProyectoDao {
    // Get all Proyecto
    @Query("SELECT * FROM proyecto_table")
    suspend fun getAll(): List<Proyecto>

    // Insert all Proyecto
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(proyectos: List<Proyecto>)

    // Insert one Proyecto
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(proyecto: Proyecto)

    // Get Proyecto by id
    @Query("SELECT * FROM proyecto_table WHERE id_proyecto = :id")
    suspend fun getById(id: Int): Proyecto

    // Delete all Proyectos
    @Query("DELETE FROM proyecto_table")
    suspend fun deleteAll()

    // Delete a Proyecto
    @Delete
    suspend fun delete(proyecto: Proyecto)

    // Update a Proyecto
    @Update
    suspend fun update(proyecto: Proyecto)

    //Filter completed projects
    @Query("SELECT * FROM proyecto_table WHERE estatus_completado = :stringStatus")
    suspend fun FilterProjectsByStatus (stringStatus: Boolean): List<Proyecto>

    // Update presupuesto
    @Query("UPDATE proyecto_table SET presupuesto=:presupuestoNew WHERE id_proyecto = :id")
    suspend fun updatePresupuesto(presupuestoNew: Double, id: Int)

    // Update meta
    @Query("UPDATE proyecto_table SET meta=:metaNew WHERE id_proyecto = :id")
    suspend fun updateMeta(metaNew: Double, id: Int)

    //Update estatus_completado
    @Query("UPDATE proyecto_table SET estatus_completado=:estatusNew WHERE id_proyecto = :id")
    suspend fun updateEstatusCompletado(estatusNew: Boolean, id: Int)

    @Query("UPDATE proyecto_table SET nombre_proyecto = :name, fecha_inicio = :date,created_at= :time WHERE id_proyecto =:id")
    suspend fun updateModify(name: String, date: String, time:String ,id: Int)

}