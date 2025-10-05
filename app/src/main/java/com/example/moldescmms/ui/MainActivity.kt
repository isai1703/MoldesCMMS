package com.example.moldescmms.ui

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.moldescmms.R

class MainActivity : AppCompatActivity() {
    
    private val TAG = "MainActivity"
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        try {
            Log.d(TAG, "MainActivity iniciando...")
            setContentView(R.layout.activity_main)
            
            val tvWelcome = findViewById<TextView>(R.id.tv_welcome)
            tvWelcome.text = "¡Bienvenido a MoldesCMMS!\n\nSistema de gestión de mantenimiento"
            
            Log.d(TAG, "MainActivity inicializado correctamente")
        } catch (e: Exception) {
            Log.e(TAG, "Error en MainActivity", e)
        }
    }
}
