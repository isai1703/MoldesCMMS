package com.example.moldescmms.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.moldescmms.R

class CalidadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calidad_main)

        supportActionBar?.title = "Calidad"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Botón para crear solicitud de mantenimiento de moldes
        findViewById<Button>(R.id.btn_solicitar_mantenimiento_moldes).setOnClickListener {
            val intent = Intent(this, SolicitudMantenimientoFormActivity::class.java)
            intent.putExtra("departamento_origen", "Calidad")
            startActivity(intent)
        }

        findViewById<Button>(R.id.btn_inspecciones).setOnClickListener {
            startActivity(Intent(this, InspeccionesListActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
