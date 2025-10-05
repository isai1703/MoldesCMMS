package com.example.moldescmms.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import kotlinx.coroutines.launch

class EstadisticasActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estadisticas)
        
        database = AppDatabase.getDatabase(this)
        
        loadEstadisticas()
    }
    
    private fun loadEstadisticas() {
        lifecycleScope.launch {
            val tvStats = findViewById<TextView>(R.id.tv_estadisticas)
            
            val totalMoldes = database.moldeDao().getCount()
            val moldesActivos = database.moldeDao().getCountByEstado("Activo")
            val moldesMantenimiento = database.moldeDao().getCountByEstado("En Mantenimiento")
            
            val totalMant = mutableListOf<Int>()
            database.mantenimientoDao().getAll().collect { totalMant.add(it.size) }
            
            val pendientes = database.mantenimientoDao().getPendientesCount()
            
            var completados = 0
            database.mantenimientoDao().getByEstado("Completado").collect { completados = it.size }
            
            var herramientas = 0
            database.herramientaDao().getAll().collect { herramientas = it.size }
            
            var refacciones = 0
            var refaccionesBajas = 0
            database.refaccionDao().getAll().collect { list ->
                refacciones = list.size
                refaccionesBajas = list.count { it.stockActual <= it.stockMinimo }
            }
            
            tvStats.text = """
                📊 ESTADÍSTICAS GENERALES
                
                MOLDES
                • Total: $totalMoldes
                • Activos: $moldesActivos
                • En mantenimiento: $moldesMantenimiento
                
                MANTENIMIENTOS
                • Total registrados: ${totalMant.firstOrNull() ?: 0}
                • Pendientes: $pendientes
                • Completados: $completados
                
                HERRAMIENTAS
                • Total: $herramientas
                
                REFACCIONES
                • Total: $refacciones
                • Con stock bajo: $refaccionesBajas
                ${if (refaccionesBajas > 0) "⚠️ Requiere atención" else "✓ Inventario en orden"}
            """.trimIndent()
        }
    }
}
