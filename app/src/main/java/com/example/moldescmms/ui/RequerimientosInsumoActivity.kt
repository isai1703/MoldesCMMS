package com.example.moldescmms.ui

import android.content.Intent
import android.os.Bundle
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
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_requerimientos_insumo)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Requerimientos de Insumos"
        
        database = AppDatabase.getDatabase(this)
        
        val recyclerView = findViewById<RecyclerView>(R.id.rv_requerimientos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        adapter = RequerimientoInsumoAdapter { requerimiento ->
            val intent = Intent(this, RequerimientoInsumoDetailActivity::class.java)
            intent.putExtra("REQUERIMIENTO_ID", requerimiento.id)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
        
        findViewById<FloatingActionButton>(R.id.fab_add_requerimiento)?.setOnClickListener {
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
            try {
                database.requerimientoInsumoDao().getAll().collect { requerimientos ->
                    adapter.submitList(requerimientos)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
