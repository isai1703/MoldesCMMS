package com.example.moldescmms.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.repositories.SolicitudMantenimientoRepository
import com.example.moldescmms.ui.adapters.SolicitudMantenimientoAdapter
import kotlinx.coroutines.launch

class SolicitudesRecibidasActivity : AppCompatActivity() {
    private lateinit var repository: SolicitudMantenimientoRepository
    private lateinit var adapter: SolicitudMantenimientoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solicitudes_recibidas)

        supportActionBar?.title = "Solicitudes Recibidas"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val db = AppDatabase.getDatabase(applicationContext)
        repository = SolicitudMantenimientoRepository(db.solicitudMantenimientoDao())

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_solicitudes_recibidas)
        adapter = SolicitudMantenimientoAdapter { solicitud ->
            val intent = Intent(this, SolicitudDetalleActivity::class.java)
            intent.putExtra("solicitud_id", solicitud.id)
            startActivity(intent)
        }
        
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            repository.allSolicitudes.collect { solicitudes ->
                adapter.submitList(solicitudes)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
