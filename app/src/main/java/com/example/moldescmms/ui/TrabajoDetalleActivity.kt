package com.example.moldescmms.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moldescmms.R
import com.example.moldescmms.databinding.ActivityTrabajoDetalleBinding
import com.example.moldescmms.viewmodel.TrabajoDetalleViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TrabajoDetalleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTrabajoDetalleBinding
    private val viewModel: TrabajoDetalleViewModel by viewModels()
    private var asignacionId: Long = -1L
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrabajoDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detalle del Trabajo"
        
        asignacionId = intent.getLongExtra("ASIGNACION_ID", -1L)
        if (asignacionId == -1L) {
            finish()
            return
        }
        
        setupListeners()
        setupObservers()
    }
    
    private fun setupListeners() {
        binding.btnCambiarEstado.setOnClickListener {
            mostrarOpcionesEstado()
        }
        
        binding.btnGuardarNotas.setOnClickListener {
            guardarNotas()
        }
        
        binding.btnMarcarCompletada.setOnClickListener {
            marcarComoCompletada()
        }
    }
    
    private fun setupObservers() {
        viewModel.cargarDetalleAsignacion(asignacionId)
        
        lifecycleScope.launch {
            viewModel.asignacionData.collect { asignacion ->
                asignacion?.let {
                    binding.apply {
                        tvSolicitudId.text = "Solicitud #${it.solicitudId}"
                        tvMoldeNombre.text = "Molde: ${it.moldeNombre}"
                        tvEstado.text = "Estado: ${it.estado}"
                        tvDescripcion.text = it.descripcionSolicitud
                        tvFechaAsignacion.text = "Asignado: ${formatDate(it.fechaAsignacion)}"
                        
                        tvSupervisor.text = "Supervisor: ${it.supervisorNombre}"
                        tvAuxiliar.text = "Auxiliar: ${it.auxiliarNombre}"
                        tvTurno.text = "Turno: ${it.turnoAsignado}"
                        
                        tvNotasAsignacion.text = it.notasAsignacion.takeIf { n -> n.isNotEmpty() }
                            ?: "Sin notas del supervisor"
                        
                        btnCambiarEstado.isEnabled = it.estado != "Completada"
                        btnMarcarCompletada.isEnabled = it.estado != "Completada"
                        etNotasTecnico.isEnabled = it.estado != "Completada"
                        
                        if (it.notasTecnico.isNotEmpty()) {
                            etNotasTecnico.setText(it.notasTecnico)
                        }
                    }
                }
            }
        }
        
        lifecycleScope.launch {
            viewModel.mensajeError.collect { mensaje ->
                if (mensaje.isNotEmpty()) {
                    Snackbar.make(binding.root, mensaje, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    private fun mostrarOpcionesEstado() {
        val opciones = arrayOf("En Proceso", "Pausada", "Completada")
        
        MaterialAlertDialogBuilder(this)
            .setTitle("Cambiar estado del trabajo")
            .setItems(opciones) { _, which ->
                val nuevoEstado = when (which) {
                    0 -> "En Proceso"
                    1 -> "Pausada"
                    2 -> "Completada"
                    else -> "Asignada"
                }
                cambiarEstado(nuevoEstado)
            }
            .show()
    }
    
    private fun cambiarEstado(nuevoEstado: String) {
        lifecycleScope.launch {
            viewModel.cambiarEstado(asignacionId, nuevoEstado)
            Snackbar.make(
                binding.root,
                "Estado actualizado a: $nuevoEstado",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }
    
    private fun guardarNotas() {
        val notas = binding.etNotasTecnico.text.toString()
        if (notas.isNotEmpty()) {
            lifecycleScope.launch {
                viewModel.guardarNotasTecnico(asignacionId, notas)
                Snackbar.make(binding.root, "Notas guardadas", Snackbar.LENGTH_SHORT).show()
            }
        } else {
            Snackbar.make(binding.root, "Escribe una nota primero", Snackbar.LENGTH_SHORT).show()
        }
    }
    
    private fun marcarComoCompletada() {
        MaterialAlertDialogBuilder(this)
            .setTitle("¿Completar trabajo?")
            .setMessage("Asegúrate de guardar las notas técnicas antes de marcar como completada.")
            .setPositiveButton("Completar") { _, _ ->
                cambiarEstado("Completada")
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
    
    private fun formatDate(timestamp: Long): String {
        return SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date(timestamp))
    }
    
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_trabajo_detalle, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.action_actualizar -> {
                setupObservers()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    companion object {
        fun getIntent(context: Context, asignacionId: Long) =
            Intent(context, TrabajoDetalleActivity::class.java).apply {
                putExtra("ASIGNACION_ID", asignacionId)
            }
    }
}
