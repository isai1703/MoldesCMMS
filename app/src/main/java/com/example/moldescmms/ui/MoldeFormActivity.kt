package com.example.moldescmms.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.entities.Molde
import kotlinx.coroutines.launch

class MoldeFormActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    private var moldeId: Long = -1
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_molde_form)
        
        database = AppDatabase.getDatabase(this)
        moldeId = intent.getLongExtra("MOLDE_ID", -1)
        
        setupSpinners()
        setupCheckboxes()
        
        if (moldeId != -1L) {
            loadMolde()
        }
        
        findViewById<Button>(R.id.btn_save).setOnClickListener {
            saveMolde()
        }
        
        findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            finish()
        }
    }
    
    private fun setupSpinners() {
        val tiposColada = arrayOf("Seleccionar", "Fría", "Caliente")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tiposColada)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(R.id.sp_tipo_colada).adapter = adapter
    }
    
    private fun setupCheckboxes() {
        val cbMuelas = findViewById<CheckBox>(R.id.cb_muelas_rosca)
        val etNumMuelas = findViewById<EditText>(R.id.et_num_muelas)
        
        cbMuelas.setOnCheckedChangeListener { _, isChecked ->
            etNumMuelas.visibility = if (isChecked) android.view.View.VISIBLE else android.view.View.GONE
        }
    }
    
    private fun loadMolde() {
        lifecycleScope.launch {
            val molde = database.moldeDao().getById(moldeId)
            molde?.let {
                findViewById<EditText>(R.id.et_codigo).setText(it.codigo)
                findViewById<EditText>(R.id.et_nombre).setText(it.nombre)
                findViewById<EditText>(R.id.et_descripcion).setText(it.descripcion)
                findViewById<EditText>(R.id.et_ubicacion).setText(it.ubicacion)
                findViewById<EditText>(R.id.et_cavidades).setText(it.numeroCavidades.toString())
                findViewById<EditText>(R.id.et_tipo_expulsor).setText(it.tipoExpulsor)
                findViewById<EditText>(R.id.et_num_expulsores).setText(it.numeroExpulsores.toString())
                findViewById<EditText>(R.id.et_circuitos_refrigerante).setText(it.numeroCircuitosRefrigerante.toString())
                findViewById<EditText>(R.id.et_conexiones_rapidas).setText(it.numeroConexionesRapidas.toString())
                findViewById<EditText>(R.id.et_circuitos_aire).setText(it.numeroCircuitosAire.toString())
                findViewById<EditText>(R.id.et_conexiones_aire).setText(it.numeroConexionesAire.toString())
                findViewById<EditText>(R.id.et_observaciones).setText(it.observaciones)
                
                findViewById<CheckBox>(R.id.cb_muelas_rosca).isChecked = it.tieneMuelasGeneradorasRosca
                findViewById<CheckBox>(R.id.cb_insertos).isChecked = it.tieneInsertos
                findViewById<CheckBox>(R.id.cb_correderas).isChecked = it.tieneCorrederas
                findViewById<CheckBox>(R.id.cb_levantadores).isChecked = it.tieneLevantadores
                
                val spTipoColada = findViewById<Spinner>(R.id.sp_tipo_colada)
                when (it.tipoColada) {
                    "Fría" -> spTipoColada.setSelection(1)
                    "Caliente" -> spTipoColada.setSelection(2)
                }
            }
        }
    }
    
    private fun saveMolde() {
        val codigo = findViewById<EditText>(R.id.et_codigo).text.toString()
        val nombre = findViewById<EditText>(R.id.et_nombre).text.toString()
        
        if (codigo.isEmpty() || nombre.isEmpty()) {
            Toast.makeText(this, "Código y nombre son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }
        
        val molde = Molde(
            id = if (moldeId == -1L) 0 else moldeId,
            codigo = codigo,
            nombre = nombre,
            descripcion = findViewById<EditText>(R.id.et_descripcion).text.toString(),
            ubicacion = findViewById<EditText>(R.id.et_ubicacion).text.toString(),
            numeroCavidades = findViewById<EditText>(R.id.et_cavidades).text.toString().toIntOrNull() ?: 1,
            tipoColada = findViewById<Spinner>(R.id.sp_tipo_colada).selectedItem.toString(),
            tipoExpulsor = findViewById<EditText>(R.id.et_tipo_expulsor).text.toString(),
            numeroExpulsores = findViewById<EditText>(R.id.et_num_expulsores).text.toString().toIntOrNull() ?: 0,
            numeroCircuitosRefrigerante = findViewById<EditText>(R.id.et_circuitos_refrigerante).text.toString().toIntOrNull() ?: 0,
            numeroConexionesRapidas = findViewById<EditText>(R.id.et_conexiones_rapidas).text.toString().toIntOrNull() ?: 0,
            numeroCircuitosAire = findViewById<EditText>(R.id.et_circuitos_aire).text.toString().toIntOrNull() ?: 0,
            numeroConexionesAire = findViewById<EditText>(R.id.et_conexiones_aire).text.toString().toIntOrNull() ?: 0,
            tieneMuelasGeneradorasRosca = findViewById<CheckBox>(R.id.cb_muelas_rosca).isChecked,
            tieneInsertos = findViewById<CheckBox>(R.id.cb_insertos).isChecked,
            tieneCorrederas = findViewById<CheckBox>(R.id.cb_correderas).isChecked,
            tieneLevantadores = findViewById<CheckBox>(R.id.cb_levantadores).isChecked,
            observaciones = findViewById<EditText>(R.id.et_observaciones).text.toString()
        )
        
        lifecycleScope.launch {
            database.moldeDao().insert(molde)
            Toast.makeText(this@MoldeFormActivity, "Molde guardado", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
