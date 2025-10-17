package com.example.moldescmms.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.entities.TecnicoTaller
import com.example.moldescmms.data.entities.AsignacionSolicitud
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuxiliarDetalleViewModel(application: Application) : AndroidViewModel(application) {
    
    private val db = AppDatabase.getDatabase(application)
    private val auxiliarDao = db.tecnicoTallerDao()
    private val asignacionDao = db.asignacionSolicitudDao()
    
    private val _auxiliarData = MutableStateFlow<TecnicoTaller?>(null)
    val auxiliarData: StateFlow<TecnicoTaller?> = _auxiliarData
    
    private val _tareasAsignadas = MutableStateFlow<List<AsignacionSolicitud>>(emptyList())
    val tareasAsignadas: StateFlow<List<AsignacionSolicitud>> = _tareasAsignadas
    
    fun cargarDetalleAuxiliar(auxiliarId: Long) {
        viewModelScope.launch {
            val auxiliar = auxiliarDao.getById(auxiliarId)
            _auxiliarData.emit(auxiliar)
            
            val tareas = asignacionDao.getByAuxiliarId(auxiliarId)
            _tareasAsignadas.emit(tareas)
        }
    }
}
