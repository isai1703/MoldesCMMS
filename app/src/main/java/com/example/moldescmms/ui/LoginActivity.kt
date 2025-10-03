package com.example.moldescmms

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.entities.Usuario

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val db = AppDatabase.getDatabase(this)
        val userDao = db.usuarioDao()

        // crear admin por defecto si no existe
        Thread {
            val admin = userDao.findByUsername("admin")
            if (admin == null) {
                userDao.insert(Usuario(username = "admin", password = "admin123", role = "administrador"))
            }
        }.start()

        val etUser = findViewById<EditText>(R.id.et_user)
        val etPass = findViewById<EditText>(R.id.et_pass)
        val btn = findViewById<Button>(R.id.btn_login)
        btn.setOnClickListener {
            val u = etUser.text.toString()
            val p = etPass.text.toString()
            Thread {
                val usuario = userDao.findByUsername(u)
                runOnUiThread {
                    if (usuario != null && usuario.password == p) {
                        val i = Intent(this, MainActivity::class.java)
                        i.putExtra("username", usuario.username)
                        startActivity(i)
                        finish()
                    } else {
                        etUser.error = "Credenciales incorrectas"
                    }
                }
            }.start()
        }
    }
}
