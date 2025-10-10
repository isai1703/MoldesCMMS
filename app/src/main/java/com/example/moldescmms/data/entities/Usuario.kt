package com.example.moldescmms.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val numeroEmpleado: String,
    val nombre: String,
    val email: String = "",
    val telefono: String = "",
    
    val password: String,
    
    // Roles: Admin, Supervisor, MaterialEstas, Operador, Compras, Almacen, Calidad, MantenimientoMaquinas, TallerMoldes
    val rol: String = "Operador",
    val departamento: String = "",
    
    val activo: Boolean = true,
    
    val fechaCreacion: Long = System.currentTimeMillis(),
    val fechaActualizacion: Long = System.currentTimeMillis()
)
