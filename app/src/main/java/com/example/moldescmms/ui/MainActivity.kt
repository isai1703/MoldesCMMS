package com.example.moldescmms.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.moldescmms.R

class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        supportActionBar?.title = "CMMS - Moldes"
        
        // Módulos principales
        findViewById<Button>(R.id.btn_moldes).setOnClickListener {
            startActivity(Intent(this, MoldesActivity::class.java))
        }
        
        findViewById<Button>(R.id.btn_mantenimientos).setOnClickListener {
            startActivity(Intent(this, MantenimientosActivity::class.java))
        }
        
        findViewById<Button>(R.id.btn_solicitudes).setOnClickListener {
            startActivity(Intent(this, SolicitudesActivity::class.java))
        }
        
        // Nuevos módulos de operaciones
        findViewById<Button>(R.id.btn_operadores).setOnClickListener {
            startActivity(Intent(this, OperadoresActivity::class.java))
        }
        
        findViewById<Button>(R.id.btn_registro_produccion).setOnClickListener {
            startActivity(Intent(this, RegistroProduccionActivity::class.java))
        }
        
        findViewById<Button>(R.id.btn_requerimientos_insumo).setOnClickListener {
            startActivity(Intent(this, RequerimientosInsumoActivity::class.java))
        }
        
        // Módulos de máquinas
        findViewById<Button>(R.id.btn_maquinas).setOnClickListener {
            startActivity(Intent(this, MaquinasActivity::class.java))
        }
        
        findViewById<Button>(R.id.btn_refacciones_maquina).setOnClickListener {
            startActivity(Intent(this, RefaccionesMaquinaActivity::class.java))
        }
        
        // Módulos adicionales
        findViewById<Button>(R.id.btn_calidad).setOnClickListener {
            startActivity(Intent(this, CalidadActivity::class.java))
        }
        
        findViewById<Button>(R.id.btn_inventario).setOnClickListener {
            startActivity(Intent(this, InventarioActivity::class.java))
        }
    }
}
