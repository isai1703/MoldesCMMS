package com.example.moldescmms.utils

import android.content.Context
import android.os.Environment
import com.example.moldescmms.data.entities.Mantenimiento
import com.example.moldescmms.data.entities.Molde
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.TextAlignment
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class PdfGenerator(private val context: Context) {
    
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    private val dateOnlyFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    
    fun generarReporteMantenimiento(
        mantenimiento: Mantenimiento,
        molde: Molde,
        nombreArchivo: String = "mantenimiento_${mantenimiento.id}_${System.currentTimeMillis()}.pdf"
    ): File {
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val pdfFile = File(downloadsDir, nombreArchivo)
        
        val writer = PdfWriter(pdfFile)
        val pdfDoc = PdfDocument(writer)
        val document = Document(pdfDoc)
        
        // Título
        document.add(
            Paragraph("REPORTE DE MANTENIMIENTO")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(18f)
                .setBold()
        )
        
        document.add(Paragraph("\n"))
        
        // Información del molde
        document.add(Paragraph("DATOS DEL MOLDE").setBold().setFontSize(14f))
        document.add(Paragraph("Código: ${molde.codigo}"))
        document.add(Paragraph("Nombre: ${molde.nombre}"))
        document.add(Paragraph("Ubicación: ${molde.ubicacion}"))
        document.add(Paragraph("\n"))
        
        // Información del mantenimiento
        document.add(Paragraph("DATOS DEL MANTENIMIENTO").setBold().setFontSize(14f))
        document.add(Paragraph("Folio: ${mantenimiento.id}"))
        document.add(Paragraph("Tipo: ${mantenimiento.tipo}"))
        document.add(Paragraph("Subtipo: ${mantenimiento.subtipo}"))
        document.add(Paragraph("Estado: ${mantenimiento.estado}"))
        document.add(Paragraph("Prioridad: ${mantenimiento.prioridad}"))
        document.add(Paragraph("\n"))
        
        // Fechas
        document.add(Paragraph("FECHAS").setBold().setFontSize(14f))
        document.add(Paragraph("Programado: ${dateFormat.format(Date(mantenimiento.fechaProgramada))}"))
        mantenimiento.fechaInicio?.let {
            document.add(Paragraph("Inicio: ${dateFormat.format(Date(it))}"))
        }
        mantenimiento.fechaFinalizacion?.let {
            document.add(Paragraph("Finalización: ${dateFormat.format(Date(it))}"))
        }
        document.add(Paragraph("\n"))
        
        // Descripción y trabajos
        document.add(Paragraph("DESCRIPCIÓN").setBold().setFontSize(14f))
        document.add(Paragraph(mantenimiento.descripcion.ifEmpty { "N/A" }))
        document.add(Paragraph("\n"))
        
        document.add(Paragraph("TRABAJOS REALIZADOS").setBold().setFontSize(14f))
        document.add(Paragraph(mantenimiento.trabajosRealizados.ifEmpty { "N/A" }))
        document.add(Paragraph("\n"))
        
        // Recursos utilizados
        if (mantenimiento.refaccionesUsadas.isNotEmpty()) {
            document.add(Paragraph("REFACCIONES UTILIZADAS").setBold().setFontSize(14f))
            document.add(Paragraph(mantenimiento.refaccionesUsadas))
            document.add(Paragraph("\n"))
        }
        
        if (mantenimiento.herramientasUsadas.isNotEmpty()) {
            document.add(Paragraph("HERRAMIENTAS UTILIZADAS").setBold().setFontSize(14f))
            document.add(Paragraph(mantenimiento.herramientasUsadas))
            document.add(Paragraph("\n"))
        }
        
        // Observaciones
        if (mantenimiento.observaciones.isNotEmpty()) {
            document.add(Paragraph("OBSERVACIONES").setBold().setFontSize(14f))
            document.add(Paragraph(mantenimiento.observaciones))
            document.add(Paragraph("\n"))
        }
        
        // Firmas
        document.add(Paragraph("\n\n"))
        document.add(Paragraph("FIRMAS").setBold().setFontSize(14f))
        
        val tablaFirmas = Table(2)
        tablaFirmas.addCell("Realizado por:\n\n\n_____________________\n${mantenimiento.realizadoPor}")
        tablaFirmas.addCell("Supervisado por:\n\n\n_____________________\n${mantenimiento.supervisadoPor}")
        
        document.add(tablaFirmas)
        
        // Pie de página
        document.add(Paragraph("\n"))
        document.add(
            Paragraph("Generado: ${dateFormat.format(Date())}")
                .setTextAlignment(TextAlignment.RIGHT)
                .setFontSize(8f)
        )
        
        document.close()
        
        return pdfFile
    }
    
    fun generarReporteMolde(molde: Molde): File {
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val pdfFile = File(downloadsDir, "molde_${molde.codigo}_${System.currentTimeMillis()}.pdf")
        
        val writer = PdfWriter(pdfFile)
        val pdfDoc = PdfDocument(writer)
        val document = Document(pdfDoc)
        
        document.add(
            Paragraph("FICHA TÉCNICA DE MOLDE")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(18f)
                .setBold()
        )
        
        document.add(Paragraph("\n"))
        
        // Información básica
        document.add(Paragraph("INFORMACIÓN GENERAL").setBold())
        document.add(Paragraph("Código: ${molde.codigo}"))
        document.add(Paragraph("Nombre: ${molde.nombre}"))
        document.add(Paragraph("Descripción: ${molde.descripcion}"))
        document.add(Paragraph("Ubicación: ${molde.ubicacion}"))
        document.add(Paragraph("\n"))
        
        // Especificaciones técnicas
        document.add(Paragraph("ESPECIFICACIONES TÉCNICAS").setBold())
        document.add(Paragraph("Número de cavidades: ${molde.numeroCavidades}"))
        document.add(Paragraph("Peso: ${molde.peso} kg"))
        document.add(Paragraph("Dimensiones: ${molde.dimensiones}"))
        document.add(Paragraph("\n"))
        
        // Sistema de colada
        document.add(Paragraph("SISTEMA DE COLADA").setBold())
        document.add(Paragraph("Tipo: ${molde.tipoColada}"))
        document.add(Paragraph("Canales: ${molde.numeroCanalesColada}"))
        document.add(Paragraph("\n"))
        
        // Sistema de expulsión
        document.add(Paragraph("SISTEMA DE EXPULSIÓN").setBold())
        document.add(Paragraph("Tipo: ${molde.tipoExpulsor}"))
        document.add(Paragraph("Cantidad: ${molde.numeroExpulsores}"))
        document.add(Paragraph("\n"))
        
        // Sistema de refrigeración
        document.add(Paragraph("SISTEMA DE REFRIGERACIÓN").setBold())
        document.add(Paragraph("Circuitos: ${molde.numeroCircuitosRefrigerante}"))
        document.add(Paragraph("Conexiones rápidas: ${molde.numeroConexionesRapidas}"))
        document.add(Paragraph("Tipo de conexión: ${molde.tipoConexionRefrigerante}"))
        document.add(Paragraph("\n"))
        
        // Sistema de aire
        document.add(Paragraph("SISTEMA DE AIRE").setBold())
        document.add(Paragraph("Circuitos: ${molde.numeroCircuitosAire}"))
        document.add(Paragraph("Conexiones: ${molde.numeroConexionesAire}"))
        document.add(Paragraph("\n"))
        
        // Elementos especiales
        document.add(Paragraph("ELEMENTOS ESPECIALES").setBold())
        if (molde.tieneMuelasGeneradorasRosca) {
            document.add(Paragraph("✓ Muelas generadoras de rosca: ${molde.numeroMuelasRosca} (${molde.tipoRosca})"))
        }
        if (molde.tieneInsertos) {
            document.add(Paragraph("✓ Insertos: ${molde.numeroInsertos} (${molde.tipoInsertos})"))
        }
        if (molde.tieneCorrederas) {
            document.add(Paragraph("✓ Correderas: ${molde.numeroCorrederas}"))
        }
        if (molde.tieneLevantadores) {
            document.add(Paragraph("✓ Levantadores: ${molde.numeroLevantadores}"))
        }
        
        document.close()
        
        return pdfFile
    }
}
