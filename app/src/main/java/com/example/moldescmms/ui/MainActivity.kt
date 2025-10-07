package com.example.moldescmms.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        database = AppDatabase.getDatabase(this)
        
        setupButtons()
        loadEstadisticas(findViewById(R.id.tv_welcome))
    }
    
    private fun setupButtons() {
        // Taller de Moldes
        findViewById<Button>(R.id.btn_moldes).setOnClickListener {
            startActivity(Intent(this, MoldesListActivity::class.java))
        }
        
        findViewById<Button>(R.id.btn_mantenimientos).setOnClickListener {
            startActivity(Intent(this, MantenimientosListActivity::class.java))
        }
        
        findViewById<Button>(R.id.btn_herramientas).setOnClickListener {
            startActivity(Intent(this, HerramientasListActivity::class.java))
        }
        
        findViewById<Button>(R.id.btn_refacciones).setOnClickListener {
            startActivity(Intent(this, RefaccionesListActivity::class.java))
        }
        
        // Producción
        findViewById<Button>(R.id.btn_maquinas).setOnClickListener {
            startActivity(Intent(this, MaquinasListActivity::class.java))
        }
        
        findViewById<Button>(R.id.btn_productos).setOnClickListener {
            startActivity(Intent(this, ProductosListActivity::class.java))
        }
        
        // Almacén
        findViewById<Button>(R.id.btn_inventario).setOnClickListener {
            startActivity(Intent(this, InventarioActivity::class.java))
        }
        
        // Calidad
        findViewById<Button>(R.id.btn_inspecciones).setOnClickListener {
            startActivity(Intent(this, InspeccionesListActivity::class.java))
        }
        
        // Compras
        findViewById<Button>(R.id.btn_ordenes_compra).setOnClickListener {
            startActivity(Intent(this, OrdenesCompraListActivity::class.java))
        }
        
        // Estadísticas
        findViewById<Button>(R.id.btn_estadisticas).setOnClickListener {
            startActivity(Intent(this, EstadisticasActivity::class.java))
        }
    }
    
    override fun onResume() {
        super.onResume()
        loadEstadisticas(findViewById(R.id.tv_welcome))
    }
    
    private fun loadEstadisticas(textView: TextView) {
        lifecycleScope.launch {
            try {
                val countMoldes = database.moldeDao().getCount()
                val countPendientes = database.mantenimientoDao().getPendientesCount()
                val countHerramientas = database.herramientaDao().getAll().first().size
                val refaccionesBajas = database.refaccionDao().getBajoStock().first().size
                
                textView.text = """
                    Sistema Integrado de Gestión
                    
                    Moldes: $countMoldes | Mant. pendientes: $countPendientes
                    Herramientas: $countHerramientas | Refacciones críticas: $refaccionesBajas
                """.trimIndent()
            } catch (e: Exception) {
                textView.text = "Sistema Integrado de Gestión"
            }
        }
    }
}
