package com.example.appatemporal.data.localdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appatemporal.data.localdatabase.dao.*
import com.example.appatemporal.data.localdatabase.entities.*

@Database(entities = [Actividad::class, Area::class, Costo::class, Estatus::class, Objetivo::class,
                        Proyecto::class, Usuario::class, Privilegio::class ,Rol::class], version = 1)
                        
abstract class LocalDatabase: RoomDatabase() {
    abstract val actividadDao: ActividadDao
    abstract val areaDao: AreaDao
    abstract val costoDao: CostoDao
    abstract val estatusDao: EstatusDao
    abstract val objetivoDao: ObjetivoDao
    abstract val proyectoDao: ProyectoDao
    abstract val usuarioDao: UsuarioDao
    abstract val privilegioDao: PrivilegioDao
    abstract val rolDao: RolDao

    companion object{
        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase{
            synchronized(this){
                return INSTANCE ?: Room.databaseBuilder(
                    context,
                    LocalDatabase::class.java,
                    "local_database"
                ).build().also { INSTANCE = it }
            }
        }
    }



    /*  abstract fun actividadDao(): ActividadDao
      abstract fun areaDao(): AreaDao
      abstract fun costoDao(): CostoDao
      abstract fun estatusDao(): EstatusDao
      abstract fun objetivoDao(): ObjetivoDao
      abstract fun proyectoDao(): ProyectoDao*/
}
