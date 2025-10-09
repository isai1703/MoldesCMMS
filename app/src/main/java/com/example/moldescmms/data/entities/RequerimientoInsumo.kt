package com.example.moldescmms.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "requerimientos_insumos",
    foreignKeys = [
        ForeignKey(entity = Producto::class, parentColumns = ["id"],
                   childColumns = ["productoId"], onDelete = ForeignKey.CASCADE)
    ],
    indices = [Index(value = ["productoId"]), Index(value = ["estado"])]
)
data class RequerimientoInsumo(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val numeroRequerimiento: String,
    val productoId: Long,
    val solicitadoPor: String, // Supervisor
    val departamento: String = "Material Estas",
    
    // Insumos necesarios
    val materiaPrima: String = "", // Tipo y cantidad
    val cantidadMateriaPrima: Double = 0.0, // kg
    val pigmento: String = "",
    val cantidadPigmento: Double = 0.0,
    val tapas: String = "",
    val cantidadTapas: Int = 0,
    val asasPlasticas: Int = 0,
    val asasMetal: Int = 0,
    val otrosInsumos: String = "",
    
    val cantidadProducir: Int = 0,
    val fechaRequerida: Long,
    val prioridad: String = "Media",
    
    val estado: String = "Pendiente", // Pendiente, Aprobado, En Preparación, Completado
    val preparadoPor: Long? = null, // ID del operador de Material Estas
    val fechaPreparacion: Long? = null,
    
    val observaciones: String = "",
    val fechaCreacion: Long = System.currentTimeMillis()
)
