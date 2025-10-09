package com.example.moldescmms.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.entities.Operador
import kotlinx.coroutines.launch

class OperadorFormActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    private var operadorId: Long = 0
    private var isEditMode = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operador_form)
        
        database = AppDatabase.getDatabase(this)
        
        operadorId = intent.getLongExtra("OPERADOR_ID", 0)
        isEditMode = operadorId > 0
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = if (isEditMode) "Editar Operador" else "Nuevo Operador"
        
        setupSpinners()
        
        if (isEditMode) {
            loadOperador()
        }
        
        findViewById<Button>(R.id.btn_save_operador).setOnClickListener {
            saveOperador()
        }
        
        findViewById<Button>(R.id.btn_cancel_operador).setOnClickListener {
            finish()
        }
    }
    
    private fun setupSpinners() {
        val departamentos = arrayOf("Producción", "Calidad", "Mantenimiento", "Almacén")
        val deptAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, departamentos)
        deptAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(R.id.sp_departamento_operador).adapter = deptAdapter
        
        val turnos = arrayOf("Matutino", "Vespertino", "Nocturno", "Mixto")
        val turnoAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, turnos)
        turnoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(R.id.sp_turno_operador).adapter = turnoAdapter
        
        val niveles = arrayOf("Operador Jr", "Operador Sr", "Líder de Línea", "Supervisor")
        val nivelAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, niveles)
        nivelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(R.id.sp_nivel_operador).adapter = nivelAdapter
    }
    
    private fun loadOperador() {
        lifecycleScope.launch {
            val operador = database.operadorDao().getById(operadorId)
            operador?.let {
                findViewById<EditText>(R.id.et_nombre_operador).setText(it.nombreCompleto)
                findViewById<EditText>(R.id.et_numero_empleado).setText(it.numeroEmpleado)
                findViewById<EditText>(R.id.et_telefono_operador).setText(it.telefono)
                findViewById<EditText>(R.id.et_email_operador).setText(it.email)
                
                val deptPos = (findViewById<Spinner>(R.id.sp_departamento_operador).adapter as ArrayAdapter<String>)
                    .getPosition(it.departamento)
                findViewById<Spinner>(R.id.sp_departamento_operador).setSelection(deptPos)
                
                val turnoPos = (findViewById<Spinner>(R.id.sp_turno_operador).adapter as ArrayAdapter<String>)
                    .getPosition(it.turno)
                findViewById<Spinner>(R.id.sp_turno_operador).setSelection(turnoPos)
                
                val nivelPos = (findViewById<Spinner>(R.id.sp_nivel_operador).adapter as ArrayAdapter<String>)
                    .getPosition(it.nivelExperiencia)
                findViewById<Spinner>(R.id.sp_nivel_operador).setSelection(nivelPos)
                
                findViewById<EditText>(R.id.et_certificaciones).setText(it.certificaciones)
                findViewById<CheckBox>(R.id.cb_operador_activo).isChecked = it.activo
            }
        }
    }
    
    private fun saveOperador() {
        val nombre = findViewById<EditText>(R.id.et_nombre_operador).text.toString()
        val numeroEmpleado = findViewById<EditText>(R.id.et_numero_empleado).text.toString()
        
        if (nombre.isEmpty() || numeroEmpleado.isEmpty()) {
            Toast.makeText(this, "Complete los campos obligatorios", Toast.LENGTH_SHORT).show()
            return
        }
        
        val operador = Operador(
            id = if (isEditMode) operadorId else 0,
            numeroEmpleado = numeroEmpleado,
            nombreCompleto = nombre,
            departamento = findViewById<Spinner>(R.id.sp_departamento_operador).selectedItem.toString(),
            turno = findViewById<Spinner>(R.id.sp_turno_operador).selectedItem.toString(),
            telefono = findViewById<EditText>(R.id.et_telefono_operador).text.toString(),
            email = findViewById<EditText>(R.id.et_email_operador).text.toString(),
            nivelExperiencia = findViewById<Spinner>(R.id.sp_nivel_operador).selectedItem.toString(),
            certificaciones = findViewById<EditText>(R.id.et_certificaciones).text.toString(),
            activo = findViewById<CheckBox>(R.id.cb_operador_activo).isChecked,
            fechaActualizacion = System.currentTimeMillis()
        )
        
        lifecycleScope.launch {
            try {
                database.operadorDao().insert(operador)
                Toast.makeText(this@OperadorFormActivity, 
                    "Operador guardado exitosamente", Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@OperadorFormActivity, 
                    "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
