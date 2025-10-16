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
import com.example.moldescmms.ui.adapters.SolicitudPendienteAdapter
import kotlinx.coroutines.launch

class SolicitudesPendientesActivity : AppCompatActivity() {
    private lateinit var repository: SolicitudMantenimientoRepository
    private lateinit var adapter: SolicitudPendienteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solicitudes_pendientes)

        supportActionBar?.title = "Solicitudes Pendientes"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val db = AppDatabase.getDatabase(applicationContext)
        repository = SolicitudMantenimientoRepository(db.solicitudMantenimientoDao())

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_solicitudes_pendientes)
        adapter = SolicitudPendienteAdapter { solicitud ->
            val intent = Intent(this, AsignarSolicitudActivity::class.java)
            intent.putExtra("solicitud_id", solicitud.id)
            startActivity(intent)
        }
        
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            repository.getSolicitudesByEstado("Pendiente").collect { solicitudes ->
                adapter.submitList(solicitudes)
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
