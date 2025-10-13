package com.example.moldescmms.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.repositories.AsignacionProduccionRepository
import com.example.moldescmms.ui.adapters.AsignacionProduccionAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class AsignacionProduccionActivity : AppCompatActivity() {
    private lateinit var repository: AsignacionProduccionRepository
    private lateinit var adapter: AsignacionProduccionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asignacion_produccion)

        supportActionBar?.title = "Asignaciones de Producción"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val db = AppDatabase.getDatabase(applicationContext)
        repository = AsignacionProduccionRepository(db.asignacionProduccionDao())

        setupRecyclerView()
        
        findViewById<FloatingActionButton>(R.id.fab_add_asignacion).setOnClickListener {
            startActivity(Intent(this, AsignacionProduccionFormActivity::class.java))
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_asignaciones)
        adapter = AsignacionProduccionAdapter { asignacion ->
            val intent = Intent(this, AsignacionProduccionFormActivity::class.java)
            intent.putExtra("asignacion_id", asignacion.id)
            startActivity(intent)
        }
        
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            repository.allAsignaciones.collect { asignaciones ->
                adapter.submitList(asignaciones)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
