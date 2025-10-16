package com.example.moldescmms.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moldescmms.R

class AuxiliarFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auxiliar_form)
        supportActionBar?.title = "Nuevo Auxiliar"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
