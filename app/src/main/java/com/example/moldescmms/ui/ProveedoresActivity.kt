package com.example.moldescmms.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.repositories.ProveedorRepository
import com.example.moldescmms.ui.adapters.ProveedorAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class ProveedoresActivity : AppCompatActivity() {
    private lateinit var repository: ProveedorRepository
    private lateinit var adapter: ProveedorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proveedores)

        supportActionBar?.title = "Proveedores"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val db = AppDatabase.getDatabase(applicationContext)
        repository = ProveedorRepository(db.proveedorDao())

        setupRecyclerView()
        
        findViewById<FloatingActionButton>(R.id.fab_add_proveedor).setOnClickListener {
            startActivity(Intent(this, ProveedorFormActivity::class.java))
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_proveedores)
        adapter = ProveedorAdapter { proveedor ->
            val intent = Intent(this, ProveedorFormActivity::class.java)
            intent.putExtra("proveedor_id", proveedor.id)
            startActivity(intent)
        }
        
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            repository.allProveedores.collect { proveedores ->
                adapter.submitList(proveedores)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
