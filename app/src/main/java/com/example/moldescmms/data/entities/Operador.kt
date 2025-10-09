package com.example.moldescmms.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "operadores",
    indices = [Index(value = ["numeroEmpleado"], unique = true)]
)
data class Operador(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val numeroEmpleado: String,
    val nombreCompleto: String,
    val puesto: String = "Operador",
    val departamento: String, // Producción, Material Estas
    
    val turno: String = "", // Matutino, Vespertino, Nocturno
    val supervisor: String = "",
    
    // Asignación actual
    val maquinaAsignada: Long? = null,
    val moldeAsignado: Long? = null,
    val productoAsignando: Long? = null,
    
    // Métricas
    val piezasProducidas: Int = 0,
    val horasTrabajadas: Double = 0.0,
    val eficiencia: Double = 0.0,
    
    val activo: Boolean = true,
    val fechaIngreso: Long? = null,
    val fechaCreacion: Long = System.currentTimeMillis()
)
