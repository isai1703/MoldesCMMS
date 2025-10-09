package com.example.moldescmms.ui

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.entities.RequerimientoInsumo
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class RequerimientoInsumoDetailActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    private var requerimientoId: Long = 0
    private var requerimiento: RequerimientoInsumo? = null
    
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_requerimiento_detail)
        
        database = AppDatabase.getDatabase(this)
        
        requerimientoId = intent.getLongExtra("REQUERIMIENTO_ID", 0)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detalle de Requerimiento"
        
        setupButtons()
        loadRequerimiento()
    }
    
    private fun setupButtons() {
        findViewById<Button>(R.id.btn_aprobar).setOnClickListener {
            showApprovalDialog()
        }
        
        findViewById<Button>(R.id.btn_rechazar).setOnClickListener {
            showRejectionDialog()
        }
        
        findViewById<Button>(R.id.btn_marcar_comprado).setOnClickListener {
            marcarComprado()
        }
        
        findViewById<Button>(R.id.btn_marcar_entregado).setOnClickListener {
            marcarEntregado()
        }
    }
    
    private fun loadRequerimiento() {
        lifecycleScope.launch {
            requerimiento = database.requerimientoInsumoDao().getById(requerimientoId)
            requerimiento?.let { displayRequerimiento(it) }
        }
    }
    
    private fun displayRequerimiento(req: RequerimientoInsumo) {
        findViewById<TextView>(R.id.tv_detail_tipo).text = req.tipoInsumo
        findViewById<TextView>(R.id.tv_detail_articulo).text = req.articulo
        findViewById<TextView>(R.id.tv_detail_cantidad).text = "${req.cantidad} ${req.unidadMedida}"
        findViewById<TextView>(R.id.tv_detail_descripcion).text = req.descripcion
        findViewById<TextView>(R.id.tv_detail_area).text = req.areaSolicitante
        findViewById<TextView>(R.id.tv_detail_solicitante).text = req.solicitadoPor
        findViewById<TextView>(R.id.tv_detail_fecha_requerida).text = dateFormat.format(Date(req.fechaRequerida))
        findViewById<TextView>(R.id.tv_detail_prioridad).text = req.prioridad
        findViewById<TextView>(R.id.tv_detail_estado).text = req.estado
        findViewById<TextView>(R.id.tv_detail_justificacion).text = req.justificacion
        findViewById<TextView>(R.id.tv_detail_costo).text = "$${String.format("%.2f", req.costoEstimado)}"
        
        if (req.proveedorSugerido.isNotEmpty()) {
            findViewById<TextView>(R.id.tv_detail_proveedor).text = req.proveedorSugerido
            findViewById<TextView>(R.id.tv_detail_proveedor).visibility = View.VISIBLE
        }
        
        if (req.especificacionesTecnicas.isNotEmpty()) {
            findViewById<TextView>(R.id.tv_detail_especificaciones).text = req.especificacionesTecnicas
            findViewById<TextView>(R.id.tv_detail_especificaciones).visibility = View.VISIBLE
        }
        
        updateButtons(req.estado)
    }
    
    private fun updateButtons(estado: String) {
        val btnAprobar = findViewById<Button>(R.id.btn_aprobar)
        val btnRechazar = findViewById<Button>(R.id.btn_rechazar)
        val btnComprado = findViewById<Button>(R.id.btn_marcar_comprado)
        val btnEntregado = findViewById<Button>(R.id.btn_marcar_entregado)
        
        when (estado) {
            "Pendiente" -> {
                btnAprobar.visibility = View.VISIBLE
                btnRechazar.visibility = View.VISIBLE
                btnComprado.visibility = View.GONE
                btnEntregado.visibility = View.GONE
            }
            "Aprobado" -> {
                btnAprobar.visibility = View.GONE
                btnRechazar.visibility = View.GONE
                btnComprado.visibility = View.VISIBLE
                btnEntregado.visibility = View.GONE
            }
            "Comprado" -> {
                btnAprobar.visibility = View.GONE
                btnRechazar.visibility = View.GONE
                btnComprado.visibility = View.GONE
                btnEntregado.visibility = View.VISIBLE
            }
            else -> {
                btnAprobar.visibility = View.GONE
                btnRechazar.visibility = View.GONE
                btnComprado.visibility = View.GONE
                btnEntregado.visibility = View.GONE
            }
        }
    }
    
    private fun showApprovalDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Aprobar Requerimiento")
        builder.setMessage("¿Está seguro de aprobar este requerimiento?")
        
        val input = EditText(this)
        input.hint = "Observaciones (opcional)"
        builder.setView(input)
        
        builder.setPositiveButton("Aprobar") { _, _ ->
            aprobarRequerimiento(input.text.toString())
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }
    
    private fun showRejectionDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Rechazar Requerimiento")
        builder.setMessage("Indique el motivo del rechazo:")
        
        val input = EditText(this)
        input.hint = "Motivo del rechazo"
        builder.setView(input)
        
        builder.setPositiveButton("Rechazar") { _, _ ->
            val motivo = input.text.toString()
            if (motivo.isEmpty()) {
                Toast.makeText(this, "Debe indicar el motivo", Toast.LENGTH_SHORT).show()
            } else {
                rechazarRequerimiento(motivo)
            }
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }
    
    private fun aprobarRequerimiento(observaciones: String) {
        requerimiento?.let { req ->
            val updated = req.copy(
                estado = "Aprobado",
                aprobadoPor = "Sistema", // TODO: Usar usuario actual
                fechaAprobacion = System.currentTimeMillis(),
                observacionesAprobador = observaciones,
                fechaActualizacion = System.currentTimeMillis()
            )
            
            lifecycleScope.launch {
                database.requerimientoInsumoDao().update(updated)
                Toast.makeText(this@RequerimientoInsumoDetailActivity,
                    "Requerimiento aprobado", Toast.LENGTH_SHORT).show()
                loadRequerimiento()
            }
        }
    }
    
    private fun rechazarRequerimiento(motivo: String) {
        requerimiento?.let { req ->
            val updated = req.copy(
                estado = "Rechazado",
                motivoRechazo = motivo,
                fechaActualizacion = System.currentTimeMillis()
            )
            
            lifecycleScope.launch {
                database.requerimientoInsumoDao().update(updated)
                Toast.makeText(this@RequerimientoInsumoDetailActivity,
                    "Requerimiento rechazado", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
    
    private fun marcarComprado() {
        requerimiento?.let { req ->
            val updated = req.copy(
                estado = "Comprado",
                fechaCompra = System.currentTimeMillis(),
                fechaActualizacion = System.currentTimeMillis()
            )
            
            lifecycleScope.launch {
                database.requerimientoInsumoDao().update(updated)
                Toast.makeText(this@RequerimientoInsumoDetailActivity,
                    "Marcado como comprado", Toast.LENGTH_SHORT).show()
                loadRequerimiento()
            }
        }
    }
    
    private fun marcarEntregado() {
        requerimiento?.let { req ->
            val updated = req.copy(
                estado = "Entregado",
                fechaEntrega = System.currentTimeMillis(),
                fechaActualizacion = System.currentTimeMillis()
            )
            
            lifecycleScope.launch {
                database.requerimientoInsumoDao().update(updated)
                Toast.makeText(this@RequerimientoInsumoDetailActivity,
                    "Marcado como entregado", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
