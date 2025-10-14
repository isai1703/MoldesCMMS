package com.example.moldescmms.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.moldescmms.R

class ProduccionMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produccion_main)

        supportActionBar?.title = "Producción"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        findViewById<Button>(R.id.btn_productos).setOnClickListener {
            startActivity(Intent(this, ProductosListActivity::class.java))
        }

        findViewById<Button>(R.id.btn_ordenes_produccion).setOnClickListener {
            startActivity(Intent(this, RegistroProduccionActivity::class.java))
        }

        findViewById<Button>(R.id.btn_solicitud_mantenimiento_moldes).setOnClickListener {
            val intent = Intent(this, SolicitudMantenimientoFormActivity::class.java)
            intent.putExtra("departamento_origen", "Producción")
            startActivity(intent)
        }

        findViewById<Button>(R.id.btn_solicitud_mantenimiento_maquinas).setOnClickListener {
            val intent = Intent(this, SolicitudMantenimientoMaquinaFormActivity::class.java)
            intent.putExtra("departamento_origen", "Producción")
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
