package com.example.appatemporal.data.localdatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.appatemporal.data.localdatabase.entities.Rol

@Dao
interface RolDao {
    @Insert
    suspend fun insertRole(vararg rol: Rol)

    @Query("SELECT * FROM rol_table WHERE idRol = :idRol")
    suspend fun getRol(idRol: Int) : Rol

    @Query("SELECT * FROM rol_table")
    suspend fun getAllRoles() : List<Rol>

    @Query("UPDATE rol_table SET rol = :rol WHERE idRol = :idRol")
    suspend fun updateRol(idRol: Int, rol: String)

    @Query("DELETE FROM rol_table WHERE idRol = :idRol")
    suspend fun deleteRol(idRol: Int)
}