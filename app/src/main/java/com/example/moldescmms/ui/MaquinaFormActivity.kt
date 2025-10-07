package com.example.moldescmms.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.entities.Maquina
import kotlinx.coroutines.launch

class MaquinaFormActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    private var maquinaId: Long = -1
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maquina_form)
        
        database = AppDatabase.getDatabase(this)
        maquinaId = intent.getLongExtra("MAQUINA_ID", -1)
        
        setupSpinners()
        
        if (maquinaId != -1L) {
            loadMaquina()
        }
        
        findViewById<Button>(R.id.btn_save).setOnClickListener {
            saveMaquina()
        }
        
        findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            finish()
        }
    }
    
    private fun setupSpinners() {
        val tipos = arrayOf("Inyectora", "Sopladora", "Extrusora", "Termoformadora", "Otra")
        val tipoAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipos)
        tipoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(R.id.sp_tipo).adapter = tipoAdapter
        
        val estados = arrayOf("Operativa", "En Mantenimiento", "Fuera de Servicio")
        val estadoAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, estados)
        estadoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(R.id.sp_estado).adapter = estadoAdapter
    }
    
    private fun loadMaquina() {
        lifecycleScope.launch {
            val maq = database.maquinaDao().getById(maquinaId)
            maq?.let {
                findViewById<EditText>(R.id.et_codigo).setText(it.codigo)
                findViewById<EditText>(R.id.et_nombre).setText(it.nombre)
                findViewById<EditText>(R.id.et_marca).setText(it.marca)
                findViewById<EditText>(R.id.et_modelo).setText(it.modelo)
                findViewById<EditText>(R.id.et_tonelaje).setText(it.tonelaje.toString())
                findViewById<EditText>(R.id.et_ubicacion).setText(it.ubicacion)
                findViewById<EditText>(R.id.et_observaciones).setText(it.observaciones)
            }
        }
    }
    
    private fun saveMaquina() {
        val codigo = findViewById<EditText>(R.id.et_codigo).text.toString()
        val nombre = findViewById<EditText>(R.id.et_nombre).text.toString()
        
        if (codigo.isEmpty() || nombre.isEmpty()) {
            Toast.makeText(this, "Código y nombre son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }
        
        val maquina = Maquina(
            id = if (maquinaId == -1L) 0 else maquinaId,
            codigo = codigo,
            nombre = nombre,
            marca = findViewById<EditText>(R.id.et_marca).text.toString(),
            modelo = findViewById<EditText>(R.id.et_modelo).text.toString(),
            tipo = findViewById<Spinner>(R.id.sp_tipo).selectedItem.toString(),
            tonelaje = findViewById<EditText>(R.id.et_tonelaje).text.toString().toIntOrNull() ?: 0,
            ubicacion = findViewById<EditText>(R.id.et_ubicacion).text.toString(),
            estado = findViewById<Spinner>(R.id.sp_estado).selectedItem.toString(),
            observaciones = findViewById<EditText>(R.id.et_observaciones).text.toString()
        )
        
        lifecycleScope.launch {
            database.maquinaDao().insert(maquina)
            Toast.makeText(this@MaquinaFormActivity, "Máquina guardada", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
