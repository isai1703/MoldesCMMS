package $PKG.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mantenimientos")
data class Mantenimiento(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val moldeId: Long,
    val tecnico: String,
    val fechaMillis: Long,
    val actividad: String,
    val observaciones: String?
)
