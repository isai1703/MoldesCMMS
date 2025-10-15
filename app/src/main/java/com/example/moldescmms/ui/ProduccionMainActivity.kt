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

        // Botón para crear solicitud de mantenimiento de moldes
        findViewById<Button>(R.id.btn_solicitar_mantenimiento_moldes).setOnClickListener {
            val intent = Intent(this, SolicitudMantenimientoFormActivity::class.java)
            intent.putExtra("departamento_origen", "Producción")
            startActivity(intent)
        }

        findViewById<Button>(R.id.btn_productos).setOnClickListener {
            startActivity(Intent(this, ProductosListActivity::class.java))
        }

        findViewById<Button>(R.id.btn_ordenes_produccion).setOnClickListener {
            startActivity(Intent(this, RegistroProduccionActivity::class.java))
        }

        findViewById<Button>(R.id.btn_operadores).setOnClickListener {
            startActivity(Intent(this, OperadoresActivity::class.java))
        }

        findViewById<Button>(R.id.btn_registro_produccion).setOnClickListener {
            startActivity(Intent(this, RegistroProduccionActivity::class.java))
        }

        findViewById<Button>(R.id.btn_asignacion_produccion).setOnClickListener {
            startActivity(Intent(this, AsignacionProduccionActivity::class.java))
        }

        findViewById<Button>(R.id.btn_preparacion_material).setOnClickListener {
            startActivity(Intent(this, PreparacionMaterialActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
