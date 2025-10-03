package $PKG.data.daos

import androidx.room.*
import $PKG.data.entities.Usuario

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM usuarios WHERE username = :u LIMIT 1")
    fun findByUsername(u: String): Usuario?

    @Insert
    fun insert(u: Usuario): Long
}
