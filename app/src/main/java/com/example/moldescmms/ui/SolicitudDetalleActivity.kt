package com.example.moldescmms.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.entities.SolicitudMantenimiento
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class SolicitudDetalleActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    private var solicitudId: Long = -1
    private var solicitud: SolicitudMantenimiento? = null
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solicitud_detalle)
        
        database = AppDatabase.getDatabase(this)
        solicitudId = intent.getLongExtra("SOLICITUD_ID", -1)
        
        loadSolicitud()
        
        findViewById<Button>(R.id.btn_aprobar).setOnClickListener {
            aprobarSolicitud()
        }
        
        findViewById<Button>(R.id.btn_crear_mantenimiento).setOnClickListener {
            crearMantenimiento()
        }
        
        findViewById<Button>(R.id.btn_rechazar).setOnClickListener {
            rechazarSolicitud()
        }
        
        findViewById<Button>(R.id.btn_guardar_comentarios).setOnClickListener {
            guardarComentarios()
        }
    }
    
    private fun loadSolicitud() {
        lifecycleScope.launch {
            solicitud = database.solicitudMantenimientoDao().getById(solicitudId)
            solicitud?.let { sol ->
                val molde = database.moldeDao().getById(sol.moldeId)
                
                val detalle = """
                    INFORMACIÓN GENERAL
                    Estado: ${sol.estado}
                    Prioridad: ${sol.prioridad}
                    Fecha Solicitud: ${dateFormat.format(Date(sol.fechaSolicitud))}
                    
                    ORIGEN
                    Departamento: ${sol.departamentoOrigen}
                    
                    MOLDE
                    ${molde?.codigo} - ${molde?.nombre}
                    
                    TIPO DE MANTENIMIENTO
                    Tipo: ${sol.tipo}
                    Subtipo: ${sol.subtipo}
                    
                    ${if (sol.detalleConexion.isNotEmpty()) "Tipo Conexión: ${sol.detalleConexion}\n" else ""}
                    ${if (sol.tipoConexionY) "Requiere Conexión Y: Sí\n" else ""}
                    ${if (sol.calibreManguera.isNotEmpty()) "Calibre Manguera: ${sol.calibreManguera}\n" else ""}
                    ${if (sol.cantidadMangueras > 0) "Cantidad Mangueras: ${sol.cantidadMangueras}\n" else ""}
                    
                    PROBLEMA REPORTADO
                    ${sol.problemaReportado}
                    
                    ${if (sol.afectaProduccion) "⚠️ AFECTA PRODUCCIÓN\n" else ""}
                    
                    ${if (sol.comentariosSolicitante.isNotEmpty()) "COMENTARIOS\n${sol.comentariosSolicitante}\n" else ""}
                    
                    ${if (sol.comentariosTaller.isNotEmpty()) "COMENTARIOS TALLER\n${sol.comentariosTaller}" else ""}
                """.trimIndent()
                
                findViewById<TextView>(R.id.tv_detalle).text = detalle
                findViewById<EditText>(R.id.et_comentarios_taller).setText(sol.comentariosTaller)
                
                val btnAprobar = findViewById<Button>(R.id.btn_aprobar)
                val btnCrear = findViewById<Button>(R.id.btn_crear_mantenimiento)
                val btnRechazar = findViewById<Button>(R.id.btn_rechazar)
                
                when (sol.estado) {
                    "Pendiente" -> {
                        btnAprobar.isEnabled = true
                        btnCrear.isEnabled = false
                        btnRechazar.isEnabled = true
                    }
                    "Aprobada" -> {
                        btnAprobar.isEnabled = false
                        btnCrear.isEnabled = true
                        btnRechazar.isEnabled = false
                    }
                    else -> {
                        btnAprobar.isEnabled = false
                        btnCrear.isEnabled = false
                        btnRechazar.isEnabled = false
                    }
                }
            }
        }
    }
    
    private fun aprobarSolicitud() {
        solicitud?.let { sol ->
            lifecycleScope.launch {
                val updated = sol.copy(
                    estado = "Aprobada",
                    fechaAprobacion = System.currentTimeMillis(),
                    aprobadoPor = "Supervisor",
                    fechaActualizacion = System.currentTimeMillis()
                )
                database.solicitudMantenimientoDao().update(updated)
                Toast.makeText(this@SolicitudDetalleActivity, "Solicitud aprobada", Toast.LENGTH_SHORT).show()
                loadSolicitud()
            }
        }
    }
    
    private fun crearMantenimiento() {
        solicitud?.let { sol ->
            val intent = Intent(this, MantenimientoFormActivity::class.java)
            intent.putExtra("SOLICITUD_ID", sol.id)
            intent.putExtra("MOLDE_ID", sol.moldeId)
            startActivity(intent)
        }
    }
    
    private fun rechazarSolicitud() {
        AlertDialog.Builder(this)
            .setTitle("Rechazar Solicitud")
            .setMessage("¿Está seguro de rechazar esta solicitud?")
            .setPositiveButton("Sí") { _, _ ->
                solicitud?.let { sol ->
                    lifecycleScope.launch {
                        val updated = sol.copy(
                            estado = "Rechazada",
                            motivoRechazo = "Rechazado por el taller",
                            fechaActualizacion = System.currentTimeMillis()
                        )
                        database.solicitudMantenimientoDao().update(updated)
                        Toast.makeText(this@SolicitudDetalleActivity, "Solicitud rechazada", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
            .setNegativeButton("No", null)
            .show()
    }
    
    private fun guardarComentarios() {
        solicitud?.let { sol ->
            val comentarios = findViewById<EditText>(R.id.et_comentarios_taller).text.toString()
            lifecycleScope.launch {
                val updated = sol.copy(
                    comentariosTaller = comentarios,
                    fechaActualizacion = System.currentTimeMillis()
                )
                database.solicitudMantenimientoDao().update(updated)
                Toast.makeText(this@SolicitudDetalleActivity, "Comentarios guardados", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
