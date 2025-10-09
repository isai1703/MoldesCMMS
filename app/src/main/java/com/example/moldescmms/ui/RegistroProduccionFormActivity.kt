package com.example.moldescmms.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.entities.Maquina
import com.example.moldescmms.data.entities.Molde
import com.example.moldescmms.data.entities.Operador
import com.example.moldescmms.data.entities.RegistroProduccion
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class RegistroProduccionFormActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    private var registroId: Long = 0
    private var isEditMode = false
    
    private val operadores = mutableListOf<Operador>()
    private val maquinas = mutableListOf<Maquina>()
    private val moldes = mutableListOf<Molde>()
    
    private var fechaInicio: Long = System.currentTimeMillis()
    private var fechaFin: Long? = null
    
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_produccion_form)
        
        database = AppDatabase.getDatabase(this)
        
        registroId = intent.getLongExtra("REGISTRO_ID", 0)
        isEditMode = registroId > 0
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = if (isEditMode) "Editar Registro" else "Nuevo Registro"
        
        setupSpinners()
        setupDateTimePickers()
        setupCalculos()
        
        findViewById<Button>(R.id.btn_save_registro).setOnClickListener {
            saveRegistro()
        }
        
        findViewById<Button>(R.id.btn_cancel_registro).setOnClickListener {
            finish()
        }
    }
    
    private fun setupSpinners() {
        lifecycleScope.launch {
            // Cargar operadores
            database.operadorDao().getAllActivos().collect { ops ->
                operadores.clear()
                operadores.addAll(ops)
                val operadorNames = operadores.map { "${it.numeroEmpleado} - ${it.nombreCompleto}" }
                val operadorAdapter = ArrayAdapter(this@RegistroProduccionFormActivity,
                    android.R.layout.simple_spinner_item, operadorNames)
                operadorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                findViewById<Spinner>(R.id.sp_operador_registro)?.adapter = operadorAdapter
            }
        }
        
        lifecycleScope.launch {
            maquinas.clear()
            maquinas.addAll(database.maquinaDao().getAllMaquinasList())
            val maquinaNames = maquinas.map { "${it.codigo} - ${it.nombre}" }
            val maquinaAdapter = ArrayAdapter(this@RegistroProduccionFormActivity,
                android.R.layout.simple_spinner_item, maquinaNames)
            maquinaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            findViewById<Spinner>(R.id.sp_maquina_registro)?.adapter = maquinaAdapter
        }
        
        lifecycleScope.launch {
            moldes.clear()
            moldes.addAll(database.moldeDao().getAllMoldesList())
            val moldeNames = moldes.map { "${it.codigo} - ${it.nombre}" }
            val moldeAdapter = ArrayAdapter(this@RegistroProduccionFormActivity,
                android.R.layout.simple_spinner_item, moldeNames)
            moldeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            findViewById<Spinner>(R.id.sp_molde_registro)?.adapter = moldeAdapter
        }
        
        val turnos = arrayOf("Matutino", "Vespertino", "Nocturno")
        val turnoAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, turnos)
        turnoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(R.id.sp_turno_registro)?.adapter = turnoAdapter
    }
    
    private fun setupDateTimePickers() {
        findViewById<Button>(R.id.btn_fecha_inicio)?.setOnClickListener {
            showDateTimePicker { timestamp ->
                fechaInicio = timestamp
                findViewById<TextView>(R.id.tv_fecha_inicio)?.text = dateFormat.format(Date(timestamp))
                calcularTiempoTotal()
            }
        }
        
        findViewById<Button>(R.id.btn_fecha_fin)?.setOnClickListener {
            showDateTimePicker { timestamp ->
                fechaFin = timestamp
                findViewById<TextView>(R.id.tv_fecha_fin)?.text = dateFormat.format(Date(timestamp))
                calcularTiempoTotal()
            }
        }
        
        findViewById<TextView>(R.id.tv_fecha_inicio)?.text = dateFormat.format(Date(fechaInicio))
    }
    
    private fun setupCalculos() {
        val etPiezasProducidas = findViewById<EditText>(R.id.et_piezas_producidas)
        val etPiezasDefectuosas = findViewById<EditText>(R.id.et_piezas_defectuosas)
        
        etPiezasProducidas?.setOnFocusChangeListener { _, _ -> calcularRendimiento() }
        etPiezasDefectuosas?.setOnFocusChangeListener { _, _ -> calcularRendimiento() }
    }
    
    private fun showDateTimePicker(onDateTimeSet: (Long) -> Unit) {
        val calendar = Calendar.getInstance()
        
        DatePickerDialog(this, { _, year, month, day ->
            calendar.set(year, month, day)
            
            TimePickerDialog(this, { _, hour, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                onDateTimeSet(calendar.timeInMillis)
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
            
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }
    
    private fun calcularTiempoTotal() {
        fechaFin?.let { fin ->
            val duracionMs = fin - fechaInicio
            val horas = duracionMs / (1000 * 60 * 60)
            val minutos = (duracionMs % (1000 * 60 * 60)) / (1000 * 60)
            
            findViewById<TextView>(R.id.tv_tiempo_total)?.text = "${horas}h ${minutos}m"
            findViewById<TextView>(R.id.tv_tiempo_total)?.visibility = View.VISIBLE
        }
    }
    
    private fun calcularRendimiento() {
        val producidas = findViewById<EditText>(R.id.et_piezas_producidas)?.text.toString().toIntOrNull() ?: 0
        val defectuosas = findViewById<EditText>(R.id.et_piezas_defectuosas)?.text.toString().toIntOrNull() ?: 0
        
        if (producidas > 0) {
            val porcentajeDefectos = (defectuosas.toFloat() / producidas * 100)
            val eficiencia = 100 - porcentajeDefectos
            
            findViewById<TextView>(R.id.tv_eficiencia)?.text = "Eficiencia: ${"%.2f".format(eficiencia)}%"
            findViewById<TextView>(R.id.tv_eficiencia)?.visibility = View.VISIBLE
        }
    }
    
    private fun saveRegistro() {
        if (operadores.isEmpty() || maquinas.isEmpty() || moldes.isEmpty()) {
            Toast.makeText(this, "Cargando datos...", Toast.LENGTH_SHORT).show()
            return
        }
        
        val spOperador = findViewById<Spinner>(R.id.sp_operador_registro)
        val spMaquina = findViewById<Spinner>(R.id.sp_maquina_registro)
        val spMolde = findViewById<Spinner>(R.id.sp_molde_registro)
        
        val piezasProducidas = findViewById<EditText>(R.id.et_piezas_producidas)?.text.toString().toIntOrNull()
        
        if (piezasProducidas == null || piezasProducidas <= 0) {
            Toast.makeText(this, "Ingrese cantidad de piezas producidas", Toast.LENGTH_SHORT).show()
            return
        }
        
        val registro = RegistroProduccion(
            id = if (isEditMode) registroId else 0,
            operadorId = operadores[spOperador.selectedItemPosition].id,
            maquinaId = maquinas[spMaquina.selectedItemPosition].id,
            moldeId = moldes[spMolde.selectedItemPosition].id,
            turno = findViewById<Spinner>(R.id.sp_turno_registro).selectedItem.toString(),
            fechaInicio = fechaInicio,
            fechaFin = fechaFin,
            piezasProducidas = piezasProducidas,
            piezasDefectuosas = findViewById<EditText>(R.id.et_piezas_defectuosas)?.text.toString().toIntOrNull() ?: 0,
            tiempoParoMinutos = findViewById<EditText>(R.id.et_tiempo_paro)?.text.toString().toIntOrNull() ?: 0,
            motivoParo = findViewById<EditText>(R.id.et_motivo_paro)?.text.toString(),
            observaciones = findViewById<EditText>(R.id.et_observaciones_registro)?.text.toString()
        )
        
        lifecycleScope.launch {
            try {
                database.registroProduccionDao().insert(registro)
                Toast.makeText(this@RegistroProduccionFormActivity,
                    "Registro guardado exitosamente", Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@RegistroProduccionFormActivity,
                    "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
