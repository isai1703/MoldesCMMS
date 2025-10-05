package com.example.moldescmms.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        database = AppDatabase.getDatabase(this)
        
        val tvWelcome = findViewById<TextView>(R.id.tv_welcome)
        val btnMoldes = findViewById<Button>(R.id.btn_moldes)
        val btnMantenimientos = findViewById<Button>(R.id.btn_mantenimientos)
        val btnHerramientas = findViewById<Button>(R.id.btn_herramientas)
        val btnRefacciones = findViewById<Button>(R.id.btn_refacciones)
        val btnEstadisticas = findViewById<Button>(R.id.btn_estadisticas)
        
        loadEstadisticas(tvWelcome)
        
        btnMoldes.setOnClickListener {
            startActivity(Intent(this, MoldesListActivity::class.java))
        }
        
        btnMantenimientos.setOnClickListener {
            startActivity(Intent(this, MantenimientosListActivity::class.java))
        }
        
        btnHerramientas.setOnClickListener {
            startActivity(Intent(this, HerramientasListActivity::class.java))
        }
        
        btnRefacciones.setOnClickListener {
            startActivity(Intent(this, RefaccionesListActivity::class.java))
        }
        
        btnEstadisticas.setOnClickListener {
            startActivity(Intent(this, EstadisticasActivity::class.java))
        }
    }
    
    override fun onResume() {
        super.onResume()
        loadEstadisticas(findViewById(R.id.tv_welcome))
    }
    
    private fun loadEstadisticas(textView: TextView) {
        lifecycleScope.launch {
            val countMoldes = database.moldeDao().getCount()
            val countPendientes = database.mantenimientoDao().getPendientesCount()
            
            var countHerramientas = 0
            database.herramientaDao().getAll().collect { list ->
                countHerramientas = list.size
            }
            
            var refaccionesBajas = 0
            database.refaccionDao().getBajoStock().collect { list ->
                refaccionesBajas = list.size
            }
            
            textView.text = """
                Sistema CMMS de Moldes
                
                📦 Moldes registrados: $countMoldes
                🔧 Mantenimientos pendientes: $countPendientes
                🔨 Herramientas: $countHerramientas
                ⚙️ Refacciones bajo stock: $refaccionesBajas
            """.trimIndent()
        }
    }
}
