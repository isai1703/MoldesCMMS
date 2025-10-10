package com.example.moldescmms.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.ui.adapters.OperadorAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class OperadoresActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    private lateinit var adapter: OperadorAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operadores)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Gestión de Operadores"
        
        database = AppDatabase.getDatabase(this)
        
        val recyclerView = findViewById<RecyclerView>(R.id.rv_operadores)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        adapter = OperadorAdapter { operador ->
            val intent = Intent(this, OperadorFormActivity::class.java)
            intent.putExtra("OPERADOR_ID", operador.id)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
        
        findViewById<FloatingActionButton>(R.id.fab_add_operador)?.setOnClickListener {
            startActivity(Intent(this, OperadorFormActivity::class.java))
        }
        
        loadOperadores()
    }
    
    override fun onResume() {
        super.onResume()
        loadOperadores()
    }
    
    private fun loadOperadores() {
        lifecycleScope.launch {
            try {
                database.operadorDao().getAllActivos().collect { operadores ->
                    adapter.submitList(operadores)
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
