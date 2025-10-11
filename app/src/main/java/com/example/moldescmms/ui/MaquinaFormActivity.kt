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
    private var maquinaId: Long = 0
    private var isEditMode = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maquina_form)
        
        database = AppDatabase.getDatabase(this)
        
        maquinaId = intent.getLongExtra("MAQUINA_ID", 0)
        isEditMode = maquinaId > 0
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = if (isEditMode) "Editar Máquina" else "Nueva Máquina"
        
        if (isEditMode) {
            loadMaquina()
        }
        
        findViewById<Button>(R.id.btn_save_maquina)?.setOnClickListener {
            saveMaquina()
        }
        
        findViewById<Button>(R.id.btn_cancel_maquina)?.setOnClickListener {
            finish()
        }
    }
    
    private fun loadMaquina() {
        lifecycleScope.launch {
            try {
                val maquina = database.maquinaDao().getById(maquinaId)
                maquina?.let {
                    findViewById<EditText>(R.id.et_codigo_maquina)?.setText(it.codigo)
                    findViewById<EditText>(R.id.et_nombre_maquina)?.setText(it.nombre)
                    findViewById<EditText>(R.id.et_modelo_maquina)?.setText(it.modelo)
                    findViewById<EditText>(R.id.et_fabricante_maquina)?.setText(it.fabricante)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    private fun saveMaquina() {
        val codigo = findViewById<EditText>(R.id.et_codigo_maquina)?.text.toString()
        val nombre = findViewById<EditText>(R.id.et_nombre_maquina)?.text.toString()
        
        if (codigo.isEmpty() || nombre.isEmpty()) {
            Toast.makeText(this, "Complete los campos obligatorios", Toast.LENGTH_SHORT).show()
            return
        }
        
        val maquina = Maquina(
            id = if (isEditMode) maquinaId else 0,
            codigo = codigo,
            nombre = nombre,
            modelo = findViewById<EditText>(R.id.et_modelo_maquina)?.text.toString(),
            fabricante = findViewById<EditText>(R.id.et_fabricante_maquina)?.text.toString()
        )
        
        lifecycleScope.launch {
            try {
                database.maquinaDao().insert(maquina)
                Toast.makeText(this@MaquinaFormActivity, "Guardado", Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@MaquinaFormActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
