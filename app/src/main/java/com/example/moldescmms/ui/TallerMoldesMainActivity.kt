package com.example.moldescmms.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.moldescmms.R

class TallerMoldesMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taller_moldes_main)

        supportActionBar?.title = "Taller de Moldes"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        findViewById<Button>(R.id.btn_solicitudes_recibidas).setOnClickListener {
            startActivity(Intent(this, SolicitudesMantenimientoListActivity::class.java))
        }

        findViewById<Button>(R.id.btn_gestion_moldes).setOnClickListener {
            startActivity(Intent(this, MoldesListActivity::class.java))
        }

        findViewById<Button>(R.id.btn_mantenimientos_moldes).setOnClickListener {
            startActivity(Intent(this, MantenimientosListActivity::class.java))
        }

        findViewById<Button>(R.id.btn_herramientas).setOnClickListener {
            startActivity(Intent(this, HerramientasListActivity::class.java))
        }

        findViewById<Button>(R.id.btn_refacciones_taller).setOnClickListener {
            startActivity(Intent(this, RefaccionesListActivity::class.java))
        }

        findViewById<Button>(R.id.btn_estadisticas_taller).setOnClickListener {
            startActivity(Intent(this, EstadisticasTallerActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
