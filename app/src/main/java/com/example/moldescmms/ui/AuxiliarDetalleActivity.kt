package com.example.moldescmms.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moldescmms.R
import com.example.moldescmms.databinding.ActivityAuxiliarDetalleBinding
import com.example.moldescmms.viewmodel.AuxiliarDetalleViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class AuxiliarDetalleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuxiliarDetalleBinding
    private val viewModel: AuxiliarDetalleViewModel by viewModels()
    private lateinit var tareasAdapter: AsignacionesTareasAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuxiliarDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detalle del Auxiliar"
        
        val auxiliarId = intent.getLongExtra("AUXILIAR_ID", -1L)
        if (auxiliarId == -1L) {
            finish()
            return
        }
        
        setupRecyclerView()
        setupObservers(auxiliarId)
    }
    
    private fun setupRecyclerView() {
        tareasAdapter = AsignacionesTareasAdapter { asignacion ->
            startActivity(TrabajoDetalleActivity.getIntent(this, asignacion.id))
        }
        binding.recyclerViewTareas.apply {
            adapter = tareasAdapter
            layoutManager = LinearLayoutManager(this@AuxiliarDetalleActivity)
        }
    }
    
    private fun setupObservers(auxiliarId: Long) {
        lifecycleScope.launch {
            viewModel.cargarDetalleAuxiliar(auxiliarId)
        }
        
        lifecycleScope.launch {
            viewModel.auxiliarData.collect { auxiliar ->
                auxiliar?.let {
                    binding.apply {
                        tvNombre.text = it.nombre
                        tvTurno.text = "Turno: ${it.turnoPreferente}"
                        tvDesempenio.text = "Desempeño: ${it.desempenioPromedio}%"
                        tvTareasCompletadas.text = "Tareas completadas: ${it.tareasCompletadas}"
                        tvTiempoPromedio.text = "Tiempo promedio: ${it.tiempoPromedioMinutos} min"
                    }
                }
            }
        }
        
        lifecycleScope.launch {
            viewModel.tareasAsignadas.collect { tareas ->
                tareasAdapter.submitList(tareas)
                binding.tvTotalTareas.text = "Total de tareas: ${tareas.size}"
            }
        }
    }
    
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_auxiliar_detalle, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.action_editar -> {
                val auxiliarId = intent.getLongExtra("AUXILIAR_ID", -1L)
                startActivity(AuxiliarFormActivity.getIntent(this, auxiliarId))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    companion object {
        fun getIntent(context: android.content.Context, auxiliarId: Long) =
            android.content.Intent(context, AuxiliarDetalleActivity::class.java).apply {
                putExtra("AUXILIAR_ID", auxiliarId)
            }
    }
}
