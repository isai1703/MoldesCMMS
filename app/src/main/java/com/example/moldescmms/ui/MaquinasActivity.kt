package com.example.moldescmms.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moldescmms.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MaquinasActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maquinas)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Máquinas"
        
        findViewById<FloatingActionButton>(R.id.fab_add_maquina)?.setOnClickListener {
            startActivity(Intent(this, MaquinaFormActivity::class.java))
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
