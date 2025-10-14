package com.example.moldescmms.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moldescmms.R

class SolicitudMantenimientoMaquinaFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solicitud_mantenimiento_maquina_form)

        val departamentoOrigen = intent.getStringExtra("departamento_origen") ?: "Desconocido"
        
        supportActionBar?.title = "Solicitud de Mantenimiento - Máquina"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        findViewById<EditText>(R.id.et_departamento_origen).setText(departamentoOrigen)

        findViewById<Button>(R.id.btn_guardar_solicitud_maquina).setOnClickListener {
            Toast.makeText(this, "Solicitud de mantenimiento de máquina guardada", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
