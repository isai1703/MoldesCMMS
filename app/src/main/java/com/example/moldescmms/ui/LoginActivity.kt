package com.example.moldescmms.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.entities.Usuario
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    
    private val TAG = "LoginActivity"
    private lateinit var database: AppDatabase
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        try {
            Log.d(TAG, "LoginActivity iniciando...")
            setContentView(R.layout.activity_login)
            
            database = AppDatabase.getDatabase(this)
            Log.d(TAG, "Base de datos inicializada")
            
            // Crear usuario admin por defecto
            lifecycleScope.launch {
                try {
                    val adminExists = database.usuarioDao().findByUsername("admin")
                    if (adminExists == null) {
                        val admin = Usuario(
                            username = "admin",
                            password = "admin123",
                            email = "admin@moldescmms.com",
                            nombreCompleto = "Administrador",
                            rol = "Administrador",
                            activo = true,
                            verificado = true
                        )
                        database.usuarioDao().insert(admin)
                        Log.d(TAG, "Usuario admin creado")
                    } else {
                        Log.d(TAG, "Usuario admin ya existe")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error al crear usuario admin", e)
                }
            }
            
            val etUsername = findViewById<EditText>(R.id.et_username)
            val etPassword = findViewById<EditText>(R.id.et_password)
            val btnLogin = findViewById<Button>(R.id.btn_login)
            
            btnLogin.setOnClickListener {
                val username = etUsername.text.toString()
                val password = etPassword.text.toString()
                
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                
                lifecycleScope.launch {
                    try {
                        val usuario = database.usuarioDao().login(username, password)
                        
                        if (usuario != null) {
                            database.usuarioDao().updateUltimoAcceso(usuario.id, System.currentTimeMillis())
                            
                            Toast.makeText(this@LoginActivity, "Bienvenido ${usuario.nombreCompleto}", Toast.LENGTH_SHORT).show()
                            
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@LoginActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "Error en login", e)
                        Toast.makeText(this@LoginActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
            
            Log.d(TAG, "LoginActivity inicializado correctamente")
            
        } catch (e: Exception) {
            Log.e(TAG, "Error fatal en onCreate", e)
            Toast.makeText(this, "Error al iniciar: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
