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
    private var moldeId: Long = 0
    private var isEditMode = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_molde_form)
        
        database = AppDatabase.getDatabase(this)
        
        moldeId = intent.getLongExtra("MOLDE_ID", 0)
        isEditMode = moldeId > 0
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = if (isEditMode) "Editar Molde" else "Nuevo Molde"
        
        if (isEditMode) {
            loadMolde()
        }
        
        findViewById<Button>(R.id.btn_save_molde)?.setOnClickListener {
            saveMolde()
        }
        
        findViewById<Button>(R.id.btn_cancel_molde)?.setOnClickListener {
            finish()
        }
    }
    
    private fun loadMolde() {
        lifecycleScope.launch {
            try {
                val molde = database.moldeDao().getById(moldeId)
                molde?.let {
                    findViewById<EditText>(R.id.et_codigo_molde)?.setText(it.codigo)
                    findViewById<EditText>(R.id.et_nombre_molde)?.setText(it.nombre)
                    findViewById<EditText>(R.id.et_descripcion_molde)?.setText(it.descripcion)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    private fun saveMolde() {
        val codigo = findViewById<EditText>(R.id.et_codigo_molde)?.text.toString()
        val nombre = findViewById<EditText>(R.id.et_nombre_molde)?.text.toString()
        
        if (codigo.isEmpty() || nombre.isEmpty()) {
            Toast.makeText(this, "Complete los campos obligatorios", Toast.LENGTH_SHORT).show()
            return
        }
        
        val molde = Molde(
            id = if (isEditMode) moldeId else 0,
            codigo = codigo,
            nombre = nombre,
            descripcion = findViewById<EditText>(R.id.et_descripcion_molde)?.text.toString()
        )
        
        lifecycleScope.launch {
            try {
                database.moldeDao().insert(molde)
                Toast.makeText(this@MoldeFormActivity, "Guardado", Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@MoldeFormActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
