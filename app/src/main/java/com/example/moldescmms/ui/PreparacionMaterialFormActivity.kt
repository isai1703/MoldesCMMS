package com.example.moldescmms.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.entities.PreparacionMaterial
import com.example.moldescmms.data.repositories.PreparacionMaterialRepository
import kotlinx.coroutines.launch

class PreparacionMaterialFormActivity : AppCompatActivity() {
    private lateinit var repository: PreparacionMaterialRepository
    private var preparacionId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preparacion_material_form)

        supportActionBar?.title = "Preparación de Material"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val db = AppDatabase.getDatabase(applicationContext)
        repository = PreparacionMaterialRepository(db.preparacionMaterialDao())

        preparacionId = intent.getLongExtra("preparacion_id", 0)

        if (preparacionId > 0) {
            cargarPreparacion()
        }

        findViewById<Button>(R.id.btn_save_preparacion).setOnClickListener {
            guardarPreparacion()
        }
    }

    private fun cargarPreparacion() {
        lifecycleScope.launch {
            repository.getPreparacionById(preparacionId).collect { preparacion ->
                preparacion?.let {
                    findViewById<EditText>(R.id.et_preparacion_numero).setText(it.numeroPreparacion)
                    findViewById<EditText>(R.id.et_preparacion_material).setText(it.materiaPrima)
                    findViewById<EditText>(R.id.et_preparacion_cantidad).setText(it.cantidadPreparada.toString())
                    findViewById<EditText>(R.id.et_preparacion_pigmento).setText(it.pigmento)
                    findViewById<EditText>(R.id.et_preparacion_turno).setText(it.turno)
                }
            }
        }
    }

    private fun guardarPreparacion() {
        val numero = findViewById<EditText>(R.id.et_preparacion_numero).text.toString()
        val material = findViewById<EditText>(R.id.et_preparacion_material).text.toString()
        val cantidadStr = findViewById<EditText>(R.id.et_preparacion_cantidad).text.toString()
        val pigmento = findViewById<EditText>(R.id.et_preparacion_pigmento).text.toString()
        val turno = findViewById<EditText>(R.id.et_preparacion_turno).text.toString()

        if (numero.isBlank() || material.isBlank() || cantidadStr.isBlank()) {
            Toast.makeText(this, "Campos obligatorios faltantes", Toast.LENGTH_SHORT).show()
            return
        }

        val cantidad = cantidadStr.toDoubleOrNull() ?: 0.0

        lifecycleScope.launch {
            val preparacion = PreparacionMaterial(
                id = preparacionId,
                numeroPreparacion = numero,
                materiaPrima = material,
                cantidadPreparada = cantidad,
                pigmento = pigmento,
                turno = turno,
                estado = "Preparando"
            )

            repository.insert(preparacion)
            Toast.makeText(this@PreparacionMaterialFormActivity, "Preparación guardada", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
