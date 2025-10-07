package com.example.moldescmms.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moldescmms.data.entities.*
import com.example.moldescmms.data.daos.*

@Database(
    entities = [
        Usuario::class,
        Molde::class,
        Mantenimiento::class,
        Herramienta::class,
        Refaccion::class,
        Departamento::class,
        Maquina::class,
        Producto::class,
        InspeccionCalidad::class,
        OrdenCompra::class
    ],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun moldeDao(): MoldeDao
    abstract fun mantenimientoDao(): MantenimientoDao
    abstract fun herramientaDao(): HerramientaDao
    abstract fun refaccionDao(): RefaccionDao
    abstract fun departamentoDao(): DepartamentoDao
    abstract fun maquinaDao(): MaquinaDao
    abstract fun productoDao(): ProductoDao
    abstract fun inspeccionCalidadDao(): InspeccionCalidadDao
    abstract fun ordenCompraDao(): OrdenCompraDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "moldes_cmms_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
