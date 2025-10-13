package com.example.moldescmms.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.entities.AsignacionProduccion
import com.example.moldescmms.data.repositories.AsignacionProduccionRepository
import kotlinx.coroutines.launch

class AsignacionProduccionFormActivity : AppCompatActivity() {
    private lateinit var repository: AsignacionProduccionRepository
    private var asignacionId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asignacion_produccion_form)

        supportActionBar?.title = "Asignación de Producción"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val db = AppDatabase.getDatabase(applicationContext)
        repository = AsignacionProduccionRepository(db.asignacionProduccionDao())

        asignacionId = intent.getLongExtra("asignacion_id", 0)

        if (asignacionId > 0) {
            cargarAsignacion()
        }

        findViewById<Button>(R.id.btn_save_asignacion).setOnClickListener {
            guardarAsignacion()
        }
    }

    private fun cargarAsignacion() {
        lifecycleScope.launch {
            repository.getAsignacionById(asignacionId).collect { asignacion ->
                asignacion?.let {
                    findViewById<EditText>(R.id.et_asignacion_numero).setText(it.numeroAsignacion)
                    findViewById<EditText>(R.id.et_asignacion_cantidad_objetivo).setText(it.cantidadObjetivo.toString())
                    findViewById<EditText>(R.id.et_asignacion_turno).setText(it.turno)
                    findViewById<EditText>(R.id.et_asignacion_supervisor).setText(it.supervisor)
                }
            }
        }
    }

    private fun guardarAsignacion() {
        val numero = findViewById<EditText>(R.id.et_asignacion_numero).text.toString()
        val cantidadStr = findViewById<EditText>(R.id.et_asignacion_cantidad_objetivo).text.toString()
        val turno = findViewById<EditText>(R.id.et_asignacion_turno).text.toString()
        val supervisor = findViewById<EditText>(R.id.et_asignacion_supervisor).text.toString()

        if (numero.isBlank() || cantidadStr.isBlank()) {
            Toast.makeText(this, "Número y cantidad son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        val cantidad = cantidadStr.toIntOrNull() ?: 0

        lifecycleScope.launch {
            val asignacion = AsignacionProduccion(
                id = asignacionId,
                numeroAsignacion = numero,
                operadorId = 1, // Temporal, deberá seleccionarse
                maquinaId = 1, // Temporal, deberá seleccionarse
                moldeId = 1, // Temporal, deberá seleccionarse
                cantidadObjetivo = cantidad,
                turno = turno,
                supervisor = supervisor,
                estado = "Programada"
            )

            repository.insert(asignacion)
            Toast.makeText(this@AsignacionProduccionFormActivity, "Asignación guardada", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
