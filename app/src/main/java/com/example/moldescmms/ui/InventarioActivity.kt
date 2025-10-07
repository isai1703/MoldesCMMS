package com.example.moldescmms.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class InventarioActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventario)
        
        database = AppDatabase.getDatabase(this)
        
        loadInventario()
    }
    
    private fun loadInventario() {
        lifecycleScope.launch {
            val tvProductos = findViewById<TextView>(R.id.tv_productos)
            val tvRefacciones = findViewById<TextView>(R.id.tv_refacciones)
            val tvHerramientas = findViewById<TextView>(R.id.tv_herramientas)
            
            val productos = database.productoDao().getAllActivos().first()
            val productosBajos = productos.filter { it.stockActual <= it.stockMinimo }
            
            tvProductos.text = """
                Total: ${productos.size} productos
                Stock bajo: ${productosBajos.size}
                ${if (productosBajos.isNotEmpty()) "Requiere reorden" else "Stock normal"}
            """.trimIndent()
            
            val refacciones = database.refaccionDao().getAll().first()
            val refaccionesBajas = refacciones.filter { it.stockActual <= it.stockMinimo }
            
            tvRefacciones.text = """
                Total: ${refacciones.size} refacciones
                Stock bajo: ${refaccionesBajas.size}
                ${if (refaccionesBajas.isNotEmpty()) "Requiere reorden" else "Stock normal"}
            """.trimIndent()
            
            val herramientas = database.herramientaDao().getAll().first()
            val herramientasBajas = herramientas.filter { it.cantidad < it.cantidadMinima }
            
            tvHerramientas.text = """
                Total: ${herramientas.size} herramientas
                Bajo stock: ${herramientasBajas.size}
                ${if (herramientasBajas.isNotEmpty()) "Requiere atención" else "Inventario normal"}
            """.trimIndent()
        }
    }
}
