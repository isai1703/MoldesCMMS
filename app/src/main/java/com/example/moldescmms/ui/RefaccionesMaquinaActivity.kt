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
import com.example.moldescmms.ui.adapters.RefaccionMaquinaAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class RefaccionesMaquinaActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    private lateinit var adapter: RefaccionMaquinaAdapter
    private lateinit var recyclerView: RecyclerView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refacciones_maquina)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Refacciones de Máquinas"
        
        database = AppDatabase.getDatabase(this)
        
        recyclerView = findViewById(R.id.rv_refacciones_maquina)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        adapter = RefaccionMaquinaAdapter(
            onItemClick = { refaccion ->
                val intent = Intent(this, RefaccionMaquinaFormActivity::class.java)
                intent.putExtra("REFACCION_ID", refaccion.id)
                startActivity(intent)
            }
        )
        recyclerView.adapter = adapter
        
        findViewById<FloatingActionButton>(R.id.fab_add_refaccion_maquina).setOnClickListener {
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
            database.refaccionMaquinaDao().getAll().collect { refacciones ->
                adapter.submitList(refacciones)
            }
        }
    }
    
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_refacciones_maquina, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.action_bajo_stock -> {
                showBajoStock()
                true
            }
            R.id.action_todas -> {
                loadRefacciones()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    private fun showBajoStock() {
        lifecycleScope.launch {
            database.refaccionMaquinaDao().getBajoStock().collect { refacciones ->
                adapter.submitList(refacciones)
            }
        }
    }
}
