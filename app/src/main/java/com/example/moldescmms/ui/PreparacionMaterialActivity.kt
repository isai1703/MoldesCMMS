package com.example.moldescmms.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.repositories.PreparacionMaterialRepository
import com.example.moldescmms.ui.adapters.PreparacionMaterialAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class PreparacionMaterialActivity : AppCompatActivity() {
    private lateinit var repository: PreparacionMaterialRepository
    private lateinit var adapter: PreparacionMaterialAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preparacion_material)

        supportActionBar?.title = "Preparación de Material"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val db = AppDatabase.getDatabase(applicationContext)
        repository = PreparacionMaterialRepository(db.preparacionMaterialDao())

        setupRecyclerView()
        
        findViewById<FloatingActionButton>(R.id.fab_add_preparacion).setOnClickListener {
            startActivity(Intent(this, PreparacionMaterialFormActivity::class.java))
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_preparaciones)
        adapter = PreparacionMaterialAdapter { preparacion ->
            val intent = Intent(this, PreparacionMaterialFormActivity::class.java)
            intent.putExtra("preparacion_id", preparacion.id)
            startActivity(intent)
        }
        
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            repository.allPreparaciones.collect { preparaciones ->
                adapter.submitList(preparaciones)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
