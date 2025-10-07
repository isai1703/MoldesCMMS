package com.example.moldescmms.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.entities.SolicitudMantenimiento
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class SolicitudesMantenimientoListActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    private lateinit var adapter: ArrayAdapter<String>
    private val solicitudes = mutableListOf<SolicitudMantenimiento>()
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solicitudes_list)
        
        database = AppDatabase.getDatabase(this)
        
        val listView = findViewById<ListView>(R.id.lv_solicitudes)
        val btnNueva = findViewById<Button>(R.id.btn_nueva_solicitud)
        val spFiltro = findViewById<Spinner>(R.id.sp_filtro)
        
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        listView.adapter = adapter
        
        setupFiltro(spFiltro)
        loadSolicitudes("Todas")
        
        btnNueva.setOnClickListener {
            startActivity(Intent(this, SolicitudMantenimientoFormActivity::class.java))
        }
        
        listView.setOnItemClickListener { _, _, position, _ ->
            val solicitud = solicitudes[position]
            val intent = Intent(this, SolicitudDetalleActivity::class.java)
            intent.putExtra("SOLICITUD_ID", solicitud.id)
            startActivity(intent)
        }
        
        spFiltro.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val filtro = parent?.getItemAtPosition(position).toString()
                loadSolicitudes(filtro)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
    
    override fun onResume() {
        super.onResume()
        val filtro = findViewById<Spinner>(R.id.sp_filtro).selectedItem?.toString() ?: "Todas"
        loadSolicitudes(filtro)
    }
    
    private fun setupFiltro(spinner: Spinner) {
        val filtros = arrayOf("Todas", "Pendiente", "Aprobada", "En Proceso", "Completada")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, filtros)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }
    
    private fun loadSolicitudes(filtro: String) {
        lifecycleScope.launch {
            val list = if (filtro == "Todas") {
                database.solicitudMantenimientoDao().getAll().first()
            } else {
                database.solicitudMantenimientoDao().getByEstado(filtro).first()
            }
            
            solicitudes.clear()
            solicitudes.addAll(list)
            
            val pendientes = solicitudes.count { it.estado == "Pendiente" }
            findViewById<TextView>(R.id.tv_contador).text = 
                "Total: ${solicitudes.size} | Pendientes: $pendientes"
            
            val items = solicitudes.map {
                val fecha = dateFormat.format(Date(it.fechaSolicitud))
                val prioridad = when(it.prioridad) {
                    "Urgente" -> "🔴"
                    "Alta" -> "🟠"
                    "Media" -> "🟡"
                    else -> "🟢"
                }
                "$prioridad ${it.departamentoOrigen} - ${it.subtipo}\n${it.estado} | $fecha"
            }
            
            adapter.clear()
            adapter.addAll(items)
            adapter.notifyDataSetChanged()
        }
    }
}
