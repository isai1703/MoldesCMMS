package com.example.moldescmms.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.entities.Proveedor
import com.example.moldescmms.data.repositories.ProveedorRepository
import kotlinx.coroutines.launch

class ProveedorFormActivity : AppCompatActivity() {
    private lateinit var repository: ProveedorRepository
    private var proveedorId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proveedor_form)

        supportActionBar?.title = "Proveedor"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val db = AppDatabase.getDatabase(applicationContext)
        repository = ProveedorRepository(db.proveedorDao())

        proveedorId = intent.getLongExtra("proveedor_id", 0)

        if (proveedorId > 0) {
            cargarProveedor()
        }

        findViewById<Button>(R.id.btn_save_proveedor).setOnClickListener {
            guardarProveedor()
        }
    }

    private fun cargarProveedor() {
        lifecycleScope.launch {
            repository.getProveedorById(proveedorId).collect { proveedor ->
                proveedor?.let {
                    findViewById<EditText>(R.id.et_proveedor_codigo).setText(it.codigo)
                    findViewById<EditText>(R.id.et_proveedor_nombre).setText(it.nombre)
                    findViewById<EditText>(R.id.et_proveedor_contacto).setText(it.contactoPrincipal)
                    findViewById<EditText>(R.id.et_proveedor_telefono).setText(it.telefono)
                    findViewById<EditText>(R.id.et_proveedor_email).setText(it.email)
                    findViewById<EditText>(R.id.et_proveedor_categoria).setText(it.categoria)
                }
            }
        }
    }

    private fun guardarProveedor() {
        val codigo = findViewById<EditText>(R.id.et_proveedor_codigo).text.toString()
        val nombre = findViewById<EditText>(R.id.et_proveedor_nombre).text.toString()
        val contacto = findViewById<EditText>(R.id.et_proveedor_contacto).text.toString()
        val telefono = findViewById<EditText>(R.id.et_proveedor_telefono).text.toString()
        val email = findViewById<EditText>(R.id.et_proveedor_email).text.toString()
        val categoria = findViewById<EditText>(R.id.et_proveedor_categoria).text.toString()

        if (codigo.isBlank() || nombre.isBlank()) {
            Toast.makeText(this, "Código y nombre son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            val proveedor = Proveedor(
                id = proveedorId,
                codigo = codigo,
                nombre = nombre,
                contactoPrincipal = contacto,
                telefono = telefono,
                email = email,
                categoria = categoria
            )

            repository.insert(proveedor)
            Toast.makeText(this@ProveedorFormActivity, "Proveedor guardado", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
