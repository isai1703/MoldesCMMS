package com.example.moldescmms.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.entities.Usuario
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        
        database = AppDatabase.getDatabase(this)
        
        // Crear usuario admin por defecto si no existe
        lifecycleScope.launch {
            val adminExists = database.usuarioDao().findByUsername("admin")
            if (adminExists == null) {
                val admin = Usuario(
                    username = "admin",
                    password = "admin123", // En producción, debe estar hasheada
                    email = "admin@moldescmms.com",
                    nombreCompleto = "Administrador",
                    rol = "Administrador",
                    activo = true,
                    verificado = true
                )
                database.usuarioDao().insert(admin)
            }
        }
        
        val etUsername = findViewById<TextInputEditText>(R.id.et_username)
        val etPassword = findViewById<TextInputEditText>(R.id.et_password)
        val btnLogin = findViewById<MaterialButton>(R.id.btn_login)
        
        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            lifecycleScope.launch {
                val usuario = database.usuarioDao().login(username, password)
                
                if (usuario != null) {
                    // Actualizar último acceso
                    database.usuarioDao().updateUltimoAcceso(usuario.id, System.currentTimeMillis())
                    
                    // Guardar sesión (usar SharedPreferences en producción)
                    Toast.makeText(this@LoginActivity, "Bienvenido ${usuario.nombreCompleto}", Toast.LENGTH_SHORT).show()
                    
                    // Ir a MainActivity
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
