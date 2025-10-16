package com.example.moldescmms.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.repositories.TecnicoTallerRepository
import com.example.moldescmms.ui.adapters.AuxiliarAdapter
import kotlinx.coroutines.launch

class GestionarAuxiliaresActivity : AppCompatActivity() {
    private lateinit var repository: TecnicoTallerRepository
    private lateinit var adapter: AuxiliarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestionar_auxiliares)

        supportActionBar?.title = "Gestionar Auxiliares"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val db = AppDatabase.getDatabase(applicationContext)
        repository = TecnicoTallerRepository(db.tecnicoTallerDao())

        setupRecyclerView()
        
        findViewById<Button>(R.id.btn_agregar_auxiliar).setOnClickListener {
            startActivity(Intent(this, AuxiliarFormActivity::class.java))
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_auxiliares)
        adapter = AuxiliarAdapter { auxiliar ->
            val intent = Intent(this, AuxiliarDetalleActivity::class.java)
            intent.putExtra("auxiliar_id", auxiliar.id)
            startActivity(intent)
        }
        
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            repository.getTecnicosByRol("Auxiliar").collect { auxiliares ->
                adapter.submitList(auxiliares)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        setupRecyclerView()
    }
}
