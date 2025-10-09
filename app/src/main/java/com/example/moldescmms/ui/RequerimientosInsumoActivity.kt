package com.example.moldescmms.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.ui.adapters.RequerimientoInsumoAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class RequerimientosInsumoActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    private lateinit var adapter: RequerimientoInsumoAdapter
    private lateinit var recyclerView: RecyclerView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_requerimientos_insumo)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Requerimientos de Insumos"
        
        database = AppDatabase.getDatabase(this)
        
        recyclerView = findViewById(R.id.rv_requerimientos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        adapter = RequerimientoInsumoAdapter(
            onItemClick = { requerimiento ->
                val intent = Intent(this, RequerimientoInsumoDetailActivity::class.java)
                intent.putExtra("REQUERIMIENTO_ID", requerimiento.id)
                startActivity(intent)
            }
        )
        recyclerView.adapter = adapter
        
        findViewById<FloatingActionButton>(R.id.fab_add_requerimiento).setOnClickListener {
            startActivity(Intent(this, RequerimientoInsumoFormActivity::class.java))
        }
        
        loadRequerimientos()
    }
    
    override fun onResume() {
        super.onResume()
        loadRequerimientos()
    }
    
    private fun loadRequerimientos() {
        lifecycleScope.launch {
            database.requerimientoInsumoDao().getAll().collect { requerimientos ->
                adapter.submitList(requerimientos)
            }
        }
    }
    
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_requerimientos, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.action_filter_pendientes -> {
                filterByEstado("Pendiente")
                true
            }
            R.id.action_filter_aprobados -> {
                filterByEstado("Aprobado")
                true
            }
            R.id.action_filter_todos -> {
                loadRequerimientos()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    private fun filterByEstado(estado: String) {
        lifecycleScope.launch {
            database.requerimientoInsumoDao().getByEstado(estado).collect { requerimientos ->
                adapter.submitList(requerimientos)
            }
        }
    }
}
