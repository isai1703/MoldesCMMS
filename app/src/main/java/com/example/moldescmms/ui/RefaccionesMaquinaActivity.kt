package com.example.moldescmms.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.ui.adapters.RefaccionMaquinaAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class RefaccionesMaquinaActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    private lateinit var adapter: RefaccionMaquinaAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refacciones_maquina)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Refacciones de Máquinas"
        
        database = AppDatabase.getDatabase(this)
        
        val recyclerView = findViewById<RecyclerView>(R.id.rv_refacciones_maquina)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        adapter = RefaccionMaquinaAdapter { refaccion ->
            val intent = Intent(this, RefaccionMaquinaFormActivity::class.java)
            intent.putExtra("REFACCION_ID", refaccion.id)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
        
        findViewById<FloatingActionButton>(R.id.fab_add_refaccion_maquina)?.setOnClickListener {
            startActivity(Intent(this, RefaccionMaquinaFormActivity::class.java))
        }
        
        loadRefacciones()
    }
    
    override fun onResume() {
        super.onResume()
        loadRefacciones()
    }
    
    private fun loadRefacciones() {
        lifecycleScope.launch {
            try {
                database.refaccionMaquinaDao().getAll().collect { refacciones ->
                    adapter.submitList(refacciones)
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
