package com.example.moldescmms.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moldescmms.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MoldesActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moldes)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Moldes"
        
        findViewById<FloatingActionButton>(R.id.fab_add_molde)?.setOnClickListener {
            startActivity(Intent(this, MoldeFormActivity::class.java))
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
