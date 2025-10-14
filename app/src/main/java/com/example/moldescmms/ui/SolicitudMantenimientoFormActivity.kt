package com.example.moldescmms.ui

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.entities.SolicitudMantenimiento
import com.example.moldescmms.data.repositories.MoldeRepository
import kotlinx.coroutines.launch

class SolicitudMantenimientoFormActivity : AppCompatActivity() {
    
    private lateinit var spinnerTipoEquipo: Spinner
    private lateinit var spinnerEquipo: Spinner
    private lateinit var spinnerTipo: Spinner
    private lateinit var spinnerSubtipo: Spinner
    private lateinit var tvLabelEquipo: TextView
    private lateinit var tvLabelTipo: TextView
    private lateinit var tvLabelSubtipo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solicitud_mantenimiento_form)

        supportActionBar?.title = "Nueva Solicitud"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val departamentoOrigen = intent.getStringExtra("departamento_origen") ?: "Producción"

        initViews()
        setupSpinners(departamentoOrigen)
        setupListeners()
    }

    private fun initViews() {
        spinnerTipoEquipo = findViewById(R.id.spinner_tipo_equipo)
        spinnerEquipo = findViewById(R.id.spinner_equipo)
        spinnerTipo = findViewById(R.id.spinner_tipo)
        spinnerSubtipo = findViewById(R.id.spinner_subtipo)
        tvLabelEquipo = findViewById(R.id.tv_label_equipo)
        tvLabelTipo = findViewById(R.id.tv_label_tipo)
        tvLabelSubtipo = findViewById(R.id.tv_label_subtipo)
    }

    private fun setupSpinners(departamento: String) {
        // Tipo de Equipo (Molde o Máquina)
        val tiposEquipo = arrayOf("Molde", "Máquina")
        spinnerTipoEquipo.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tiposEquipo)

        // Departamento
        val departamentos = arrayOf("Producción", "Calidad", "Mantenimiento")
        val spinnerDepartamento = findViewById<Spinner>(R.id.spinner_departamento)
        spinnerDepartamento.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, departamentos)
        spinnerDepartamento.setSelection(departamentos.indexOf(departamento))

        // Prioridad
        val prioridades = arrayOf("Baja", "Media", "Alta", "Urgente")
        findViewById<Spinner>(R.id.spinner_prioridad).adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, prioridades)

        // Cargar opciones iniciales para Molde (por defecto)
        cargarOpcionesMolde()
    }

    private fun setupListeners() {
        // Cambiar opciones según el tipo de equipo seleccionado
        spinnerTipoEquipo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> cargarOpcionesMolde()
                    1 -> cargarOpcionesMaquina()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Cambiar subtipos según el tipo seleccionado
        spinnerTipo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                actualizarSubtipos()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Botón Cancelar
        findViewById<Button>(R.id.btn_cancelar).setOnClickListener {
            finish()
        }

        // Botón Enviar
        findViewById<Button>(R.id.btn_enviar).setOnClickListener {
            guardarSolicitud()
        }
    }

    private fun cargarOpcionesMolde() {
        tvLabelEquipo.text = "Molde"
        
        // Aquí deberías cargar los moldes desde la BD
        val moldes = arrayOf("Seleccionar molde...", "Molde 001", "Molde 002", "Molde 003", "Molde 004")
        spinnerEquipo.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, moldes)

        // Tipos de mantenimiento para MOLDES
        val tiposMolde = arrayOf("Preventivo", "Correctivo", "Predictivo")
        spinnerTipo.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tiposMolde)
        
        tvLabelTipo.text = "Tipo"
        tvLabelSubtipo.visibility = View.VISIBLE
        spinnerSubtipo.visibility = View.VISIBLE
        
        actualizarSubtipos()
    }

    private fun cargarOpcionesMaquina() {
        tvLabelEquipo.text = "Máquina"
        
        // Aquí deberías cargar las máquinas desde la BD
        val maquinas = arrayOf("Seleccionar máquina...", "Máquina 001", "Máquina 002", "Máquina 003")
        spinnerEquipo.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, maquinas)

        // Tipos de mantenimiento para MÁQUINAS
        val tiposMaquina = arrayOf("Preventivo", "Correctivo", "Predictivo", "Emergencia")
        spinnerTipo.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tiposMaquina)
        
        tvLabelTipo.text = "Tipo"
        tvLabelSubtipo.visibility = View.VISIBLE
        spinnerSubtipo.visibility = View.VISIBLE
        
        actualizarSubtipos()
    }

    private fun actualizarSubtipos() {
        val tipoSeleccionado = spinnerTipo.selectedItem?.toString() ?: ""
        val tipoEquipo = spinnerTipoEquipo.selectedItem?.toString() ?: ""

        val subtipos = when {
            tipoEquipo == "Molde" && tipoSeleccionado == "Preventivo" -> 
                arrayOf("Pulido", "Limpieza", "Lubricación", "Inspección General")
            
            tipoEquipo == "Molde" && tipoSeleccionado == "Correctivo" -> 
                arrayOf("Reparación de Cavidad", "Soldadura", "Rectificación", "Ajuste de Expulsores", "Cambio de Insertos")
            
            tipoEquipo == "Molde" && tipoSeleccionado == "Predictivo" -> 
                arrayOf("Análisis de Desgaste", "Medición de Tolerancias", "Inspección Dimensional")
            
            tipoEquipo == "Máquina" && tipoSeleccionado == "Preventivo" -> 
                arrayOf("Cambio de Aceite", "Revisión de Sistema Hidráulico", "Calibración", "Limpieza General")
            
            tipoEquipo == "Máquina" && tipoSeleccionado == "Correctivo" -> 
                arrayOf("Reparación de Fuga", "Cambio de Componente", "Ajuste Mecánico", "Reparación Eléctrica")
            
            tipoEquipo == "Máquina" && tipoSeleccionado == "Predictivo" -> 
                arrayOf("Análisis de Vibración", "Termografía", "Análisis de Aceite")
            
            tipoEquipo == "Máquina" && tipoSeleccionado == "Emergencia" -> 
                arrayOf("Paro Inmediato", "Falla Crítica", "Accidente")
            
            else -> arrayOf("Seleccionar subtipo...")
        }

        spinnerSubtipo.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, subtipos)
    }

    private fun guardarSolicitud() {
        val tipoEquipo = spinnerTipoEquipo.selectedItem.toString()
        val equipo = spinnerEquipo.selectedItem.toString()
        val tipo = spinnerTipo.selectedItem.toString()
        val subtipo = spinnerSubtipo.selectedItem.toString()
        val problema = findViewById<EditText>(R.id.et_problema).text.toString()
        val prioridad = findViewById<Spinner>(R.id.spinner_prioridad).selectedItem.toString()
        val departamento = findViewById<Spinner>(R.id.spinner_departamento).selectedItem.toString()
        val afectaProduccion = findViewById<CheckBox>(R.id.cb_afecta_produccion).isChecked
        val comentarios = findViewById<EditText>(R.id.et_comentarios).text.toString()

        if (equipo == "Seleccionar molde..." || equipo == "Seleccionar máquina...") {
            Toast.makeText(this, "Por favor selecciona un equipo", Toast.LENGTH_SHORT).show()
            return
        }

        if (problema.isBlank()) {
            Toast.makeText(this, "Por favor describe el problema", Toast.LENGTH_SHORT).show()
            return
        }

        Toast.makeText(this, "Solicitud enviada al Taller de Moldes", Toast.LENGTH_LONG).show()
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
