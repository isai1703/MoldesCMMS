package com.example.moldescmms.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.moldescmms.R

class SolicitudMantenimientoFormActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solicitud_form)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Nueva Solicitud"
        
        setupSpinners()
        
        findViewById<Button>(R.id.btn_save)?.setOnClickListener {
            Toast.makeText(this, "Función en desarrollo", Toast.LENGTH_SHORT).show()
        }
        
        findViewById<Button>(R.id.btn_cancel)?.setOnClickListener {
            finish()
        }
    }
    
    private fun setupSpinners() {
        val departamentos = arrayOf("Producción", "Calidad", "Mantenimiento Máquinas")
        findViewById<Spinner>(R.id.sp_departamento)?.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, departamentos)
        
        val tipos = arrayOf("Preventivo", "Correctivo")
        findViewById<Spinner>(R.id.sp_tipo)?.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipos)
        
        val subtipos = arrayOf("Pulido", "Limpieza", "Reparación", "Cambio de Conexión", "Cambio de Manguera")
        findViewById<Spinner>(R.id.sp_subtipo)?.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, subtipos)
        
        val prioridades = arrayOf("Urgente", "Alta", "Media", "Baja")
        findViewById<Spinner>(R.id.sp_prioridad)?.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, prioridades)
        
        val detalles = arrayOf("Agua", "Aire", "Ambas")
        findViewById<Spinner>(R.id.sp_detalle_conexion)?.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, detalles)
        
        val calibres = arrayOf("8", "10", "12")
        findViewById<Spinner>(R.id.sp_calibre)?.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, calibres)
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
