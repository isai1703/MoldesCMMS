package com.example.moldescmms.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moldescmms.R
import com.example.moldescmms.databinding.ActivityVistaAuxiliarTareasBinding
import com.example.moldescmms.viewmodel.VistaAuxiliarTareasViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch

class VistaAuxiliarTareasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVistaAuxiliarTareasBinding
    private val viewModel: VistaAuxiliarTareasViewModel by viewModels()
    private lateinit var tareasAdapter: AsignacionesTareasAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVistaAuxiliarTareasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Mis Tareas"
        
        val auxiliarId = intent.getLongExtra("AUXILIAR_ID", -1L)
        if (auxiliarId == -1L) {
            finish()
            return
        }
        
        setupRecyclerView()
        setupTabs(auxiliarId)
        setupObservers(auxiliarId)
    }
    
    private fun setupRecyclerView() {
        tareasAdapter = AsignacionesTareasAdapter { asignacion ->
            startActivity(TrabajoDetalleActivity.getIntent(this, asignacion.id))
        }
        binding.recyclerViewTareas.apply {
            adapter = tareasAdapter
            layoutManager = LinearLayoutManager(this@VistaAuxiliarTareasActivity)
        }
    }
    
    private fun setupTabs(auxiliarId: Long) {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Pendientes"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("En Proceso"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Completadas"))
        
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> viewModel.filtrarPorEstado("PENDIENTE")
                    1 -> viewModel.filtrarPorEstado("EN_PROCESO")
                    2 -> viewModel.filtrarPorEstado("COMPLETADA")
                }
            }
            
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
    
    private fun setupObservers(auxiliarId: Long) {
        lifecycleScope.launch {
            viewModel.cargarTareasAuxiliar(auxiliarId)
        }
        
        lifecycleScope.launch {
            viewModel.tareasAgrupadas.collect { tareas ->
                tareasAdapter.submitList(tareas)
                binding.tvTotalTareas.text = "Total: ${tareas.size} tareas"
            }
        }
        
        lifecycleScope.launch {
            viewModel.estadisticas.collect { stats ->
                stats?.let {
                    binding.apply {
                        tvPendientes.text = "Pendientes: ${it.pendientes}"
                        tvEnProceso.text = "En proceso: ${it.enProceso}"
                        tvCompletadas.text = "Completadas: ${it.completadas}"
                    }
                }
            }
        }
    }
    
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_auxiliar_tareas, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.action_actualizar -> {
                val auxiliarId = intent.getLongExtra("AUXILIAR_ID", -1L)
                setupObservers(auxiliarId)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    companion object {
        fun getIntent(context: android.content.Context, auxiliarId: Long) =
            android.content.Intent(context, VistaAuxiliarTareasActivity::class.java).apply {
                putExtra("AUXILIAR_ID", auxiliarId)
            }
    }
}
