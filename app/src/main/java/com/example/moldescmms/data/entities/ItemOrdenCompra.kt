package com.example.moldescmms.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "items_orden_compra",
    foreignKeys = [
        ForeignKey(
            entity = OrdenCompra::class,
            parentColumns = ["id"],
            childColumns = ["ordenCompraId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["ordenCompraId"])]
)
data class ItemOrdenCompra(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val ordenCompraId: Long,
    
    val numeroLinea: Int = 0,
    val codigo: String = "",
    val descripcion: String,
    
    val cantidad: Double,
    val unidadMedida: String = "PZA",
    
    val precioUnitario: Double,
    val descuento: Double = 0.0,
    val subtotal: Double,
    
    val cantidadRecibida: Double = 0.0,
    val cantidadPendiente: Double,
    
    val notas: String = "",
    
    val fechaCreacion: Long = System.currentTimeMillis()
)
