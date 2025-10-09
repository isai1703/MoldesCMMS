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
import com.example.moldescmms.ui.adapters.OperadorAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class OperadoresActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    private lateinit var adapter: OperadorAdapter
    private lateinit var recyclerView: RecyclerView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operadores)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Gestión de Operadores"
        
        database = AppDatabase.getDatabase(this)
        
        recyclerView = findViewById(R.id.rv_operadores)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        adapter = OperadorAdapter(
            onItemClick = { operador ->
                val intent = Intent(this, OperadorFormActivity::class.java)
                intent.putExtra("OPERADOR_ID", operador.id)
                startActivity(intent)
            }
        )
        recyclerView.adapter = adapter
        
        findViewById<FloatingActionButton>(R.id.fab_add_operador).setOnClickListener {
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
            database.operadorDao().getAllActivos().collect { operadores ->
                adapter.submitList(operadores)
            }
        }
    }
    
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_operadores, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.action_filter_turno -> {
                // TODO: Implementar filtro por turno
                true
            }
            R.id.action_filter_departamento -> {
                // TODO: Implementar filtro por departamento
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
