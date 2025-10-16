package com.example.moldescmms.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moldescmms.R

class AuxiliarDetalleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auxiliar_detalle)
        supportActionBar?.title = "Detalle Auxiliar"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
