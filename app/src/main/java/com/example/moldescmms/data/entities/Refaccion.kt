package $PKG.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "refacciones")
data class Refaccion(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombre: String,
    val cantidad: Int,
    val proveedor: String?
)
