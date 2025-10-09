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
        SolicitudMantenimiento::class,
        Herramienta::class,
        Refaccion::class,
        Departamento::class,
        Maquina::class,
        MantenimientoMaquina::class,
        RefaccionMaquina::class,
        Producto::class,
        InspeccionCalidad::class,
        OrdenCompra::class,
        Operador::class,
        RegistroProduccion::class,
        RequerimientoInsumo::class
    ],
    version = 4,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun moldeDao(): MoldeDao
    abstract fun mantenimientoDao(): MantenimientoDao
    abstract fun solicitudMantenimientoDao(): SolicitudMantenimientoDao
    abstract fun herramientaDao(): HerramientaDao
    abstract fun refaccionDao(): RefaccionDao
    abstract fun departamentoDao(): DepartamentoDao
    abstract fun maquinaDao(): MaquinaDao
    abstract fun refaccionMaquinaDao(): RefaccionMaquinaDao
    abstract fun productoDao(): ProductoDao
    abstract fun inspeccionCalidadDao(): InspeccionCalidadDao
    abstract fun ordenCompraDao(): OrdenCompraDao
    abstract fun operadorDao(): OperadorDao
    abstract fun registroProduccionDao(): RegistroProduccionDao
    abstract fun requerimientoInsumoDao(): RequerimientoInsumoDao

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
