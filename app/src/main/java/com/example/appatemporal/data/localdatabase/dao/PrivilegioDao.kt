package com.example.appatemporal.data.localdatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.appatemporal.data.localdatabase.entities.Privilegio

@Dao
interface PrivilegioDao {
    @Insert
    suspend fun insertPrivilegio(vararg privilegio: Privilegio)

    @Query("SELECT * FROM privilegio_table WHERE idPrivilegio = :idPrivilegio")
    suspend fun getPrivilegio(idPrivilegio: Int) : Privilegio

    @Query("SELECT * FROM privilegio_table")
    suspend fun getAllPrivilegios() : List<Privilegio>

    @Query("UPDATE privilegio_table SET privilegio = :privilegio WHERE idPrivilegio = :idPrivilegio")
    suspend fun updatePrivilegio(idPrivilegio: Int, privilegio: String)

    @Query("DELETE FROM privilegio_table WHERE idPrivilegio = :idPrivilegio")
    suspend fun deletePrivilegio(idPrivilegio: Int)
}