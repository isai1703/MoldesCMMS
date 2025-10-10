package com.example.moldescmms.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "preparacion_material",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id"],
            childColumns = ["materialEstasId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Producto::class,
            parentColumns = ["id"],
            childColumns = ["productoId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["materialEstasId"]),
        Index(value = ["productoId"]),
        Index(value = ["fechaPreparacion"])
    ]
)
data class PreparacionMaterial(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val materialEstasId: Long, // Usuario del sub-departamento Material Estas
    val productoId: Long,
    
    val turno: String,
    val fechaPreparacion: Long = System.currentTimeMillis(),
    
    // Materiales preparados
    val materiaPrimaKg: Double = 0.0,
    val pigmentoKg: Double = 0.0,
    val aditivosKg: Double = 0.0,
    
    val loteMateriaPrima: String = "",
    val lotePigmento: String = "",
    
    // Componentes adicionales
    val tapas: Int = 0,
    val asasPlasticas: Int = 0,
    val asasMetal: Int = 0,
    
    val molino: String = "", // Número de molino utilizado
    val tamiz: String = "", // Tamiz utilizado
    
    val observaciones: String = "",
    val validadoPor: String = "",
    
    val fechaCreacion: Long = System.currentTimeMillis()
)
