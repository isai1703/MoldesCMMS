package com.example.moldescmms.utils

import android.content.Context
import android.os.Environment
import com.example.moldescmms.data.entities.Mantenimiento
import com.example.moldescmms.data.entities.Molde
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ReportGenerator(private val context: Context) {
    
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    
    fun generarReporteMantenimientoTexto(
        mantenimiento: Mantenimiento,
        molde: Molde
    ): File {
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val fileName = "mantenimiento_${mantenimiento.id}_${System.currentTimeMillis()}.txt"
        val file = File(downloadsDir, fileName)
        
        val contenido = StringBuilder()
        contenido.appendLine("=" .repeat(50))
        contenido.appendLine("REPORTE DE MANTENIMIENTO")
        contenido.appendLine("=" .repeat(50))
        contenido.appendLine()
        
        contenido.appendLine("DATOS DEL MOLDE")
        contenido.appendLine("-" .repeat(50))
        contenido.appendLine("Código: ${molde.codigo}")
        contenido.appendLine("Nombre: ${molde.nombre}")
        contenido.appendLine("Ubicación: ${molde.ubicacion}")
        contenido.appendLine()
        
        contenido.appendLine("DATOS DEL MANTENIMIENTO")
        contenido.appendLine("-" .repeat(50))
        contenido.appendLine("Folio: ${mantenimiento.id}")
        contenido.appendLine("Tipo: ${mantenimiento.tipo}")
        contenido.appendLine("Subtipo: ${mantenimiento.subtipo}")
        contenido.appendLine("Estado: ${mantenimiento.estado}")
        contenido.appendLine("Prioridad: ${mantenimiento.prioridad}")
        contenido.appendLine()
        
        contenido.appendLine("FECHAS")
        contenido.appendLine("-" .repeat(50))
        contenido.appendLine("Programado: ${dateFormat.format(Date(mantenimiento.fechaProgramada))}")
        mantenimiento.fechaInicio?.let {
            contenido.appendLine("Inicio: ${dateFormat.format(Date(it))}")
        }
        mantenimiento.fechaFinalizacion?.let {
            contenido.appendLine("Finalización: ${dateFormat.format(Date(it))}")
        }
        contenido.appendLine()
        
        if (mantenimiento.descripcion.isNotEmpty()) {
            contenido.appendLine("DESCRIPCIÓN")
            contenido.appendLine("-" .repeat(50))
            contenido.appendLine(mantenimiento.descripcion)
            contenido.appendLine()
        }
        
        if (mantenimiento.trabajosRealizados.isNotEmpty()) {
            contenido.appendLine("TRABAJOS REALIZADOS")
            contenido.appendLine("-" .repeat(50))
            contenido.appendLine(mantenimiento.trabajosRealizados)
            contenido.appendLine()
        }
        
        if (mantenimiento.refaccionesUsadas.isNotEmpty()) {
            contenido.appendLine("REFACCIONES UTILIZADAS")
            contenido.appendLine("-" .repeat(50))
            contenido.appendLine(mantenimiento.refaccionesUsadas)
            contenido.appendLine()
        }
        
        if (mantenimiento.herramientasUsadas.isNotEmpty()) {
            contenido.appendLine("HERRAMIENTAS UTILIZADAS")
            contenido.appendLine("-" .repeat(50))
            contenido.appendLine(mantenimiento.herramientasUsadas)
            contenido.appendLine()
        }
        
        if (mantenimiento.observaciones.isNotEmpty()) {
            contenido.appendLine("OBSERVACIONES")
            contenido.appendLine("-" .repeat(50))
            contenido.appendLine(mantenimiento.observaciones)
            contenido.appendLine()
        }
        
        contenido.appendLine("FIRMAS")
        contenido.appendLine("-" .repeat(50))
        contenido.appendLine("Realizado por: ${mantenimiento.realizadoPor}")
        contenido.appendLine("Supervisado por: ${mantenimiento.supervisadoPor}")
        contenido.appendLine()
        
        contenido.appendLine("=" .repeat(50))
        contenido.appendLine("Generado: ${dateFormat.format(Date())}")
        contenido.appendLine("=" .repeat(50))
        
        file.writeText(contenido.toString())
        return file
    }
    
    fun generarFichaTecnicaMolde(molde: Molde): File {
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val fileName = "molde_${molde.codigo}_${System.currentTimeMillis()}.txt"
        val file = File(downloadsDir, fileName)
        
        val contenido = StringBuilder()
        contenido.appendLine("=" .repeat(50))
        contenido.appendLine("FICHA TÉCNICA DE MOLDE")
        contenido.appendLine("=" .repeat(50))
        contenido.appendLine()
        
        contenido.appendLine("INFORMACIÓN GENERAL")
        contenido.appendLine("-" .repeat(50))
        contenido.appendLine("Código: ${molde.codigo}")
        contenido.appendLine("Nombre: ${molde.nombre}")
        contenido.appendLine("Descripción: ${molde.descripcion}")
        contenido.appendLine("Ubicación: ${molde.ubicacion}")
        contenido.appendLine("Estado: ${molde.estado}")
        contenido.appendLine()
        
        contenido.appendLine("ESPECIFICACIONES TÉCNICAS")
        contenido.appendLine("-" .repeat(50))
        contenido.appendLine("Número de cavidades: ${molde.numeroCavidades}")
        contenido.appendLine("Peso: ${molde.peso} kg")
        contenido.appendLine("Dimensiones: ${molde.dimensiones}")
        contenido.appendLine()
        
        contenido.appendLine("SISTEMA DE COLADA")
        contenido.appendLine("-" .repeat(50))
        contenido.appendLine("Tipo: ${molde.tipoColada}")
        contenido.appendLine("Canales: ${molde.numeroCanalesColada}")
        contenido.appendLine()
        
        contenido.appendLine("SISTEMA DE EXPULSIÓN")
        contenido.appendLine("-" .repeat(50))
        contenido.appendLine("Tipo: ${molde.tipoExpulsor}")
        contenido.appendLine("Cantidad: ${molde.numeroExpulsores}")
        contenido.appendLine()
        
        contenido.appendLine("SISTEMA DE REFRIGERACIÓN")
        contenido.appendLine("-" .repeat(50))
        contenido.appendLine("Circuitos: ${molde.numeroCircuitosRefrigerante}")
        contenido.appendLine("Conexiones rápidas: ${molde.numeroConexionesRapidas}")
        contenido.appendLine("Tipo de conexión: ${molde.tipoConexionRefrigerante}")
        contenido.appendLine("Descripción: ${molde.descripcionCircuitoRefrigerante}")
        contenido.appendLine()
        
        contenido.appendLine("SISTEMA DE AIRE")
        contenido.appendLine("-" .repeat(50))
        contenido.appendLine("Circuitos: ${molde.numeroCircuitosAire}")
        contenido.appendLine("Conexiones: ${molde.numeroConexionesAire}")
        contenido.appendLine("Tipo de conexión: ${molde.tipoConexionAire}")
        contenido.appendLine()
        
        contenido.appendLine("ELEMENTOS ESPECIALES")
        contenido.appendLine("-" .repeat(50))
        if (molde.tieneMuelasGeneradorasRosca) {
            contenido.appendLine("✓ Muelas generadoras de rosca: ${molde.numeroMuelasRosca} (${molde.tipoRosca})")
        }
        if (molde.tieneInsertos) {
            contenido.appendLine("✓ Insertos: ${molde.numeroInsertos} (${molde.tipoInsertos})")
        }
        if (molde.tieneCorrederas) {
            contenido.appendLine("✓ Correderas: ${molde.numeroCorrederas}")
        }
        if (molde.tieneLevantadores) {
            contenido.appendLine("✓ Levantadores: ${molde.numeroLevantadores}")
        }
        contenido.appendLine()
        
        if (molde.observaciones.isNotEmpty()) {
            contenido.appendLine("OBSERVACIONES")
            contenido.appendLine("-" .repeat(50))
            contenido.appendLine(molde.observaciones)
            contenido.appendLine()
        }
        
        contenido.appendLine("=" .repeat(50))
        contenido.appendLine("Generado: ${dateFormat.format(Date())}")
        contenido.appendLine("=" .repeat(50))
        
        file.writeText(contenido.toString())
        return file
    }
}
