package com.example.moldescmms.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moldescmms.R
import com.example.moldescmms.data.entities.RequerimientoInsumo
import java.text.SimpleDateFormat
import java.util.*

class RequerimientoInsumoAdapter(
    private val onItemClick: (RequerimientoInsumo) -> Unit
) : ListAdapter<RequerimientoInsumo, RequerimientoInsumoAdapter.RequerimientoViewHolder>(RequerimientoDiffCallback()) {
    
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequerimientoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_requerimiento_insumo, parent, false)
        return RequerimientoViewHolder(view, onItemClick, dateFormat)
    }
    
    override fun onBindViewHolder(holder: RequerimientoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    class RequerimientoViewHolder(
        itemView: View,
        private val onItemClick: (RequerimientoInsumo) -> Unit,
        private val dateFormat: SimpleDateFormat
    ) : RecyclerView.ViewHolder(itemView) {
        
        private val tvArticulo: TextView = itemView.findViewById(R.id.tv_req_articulo)
        private val tvTipo: TextView = itemView.findViewById(R.id.tv_req_tipo)
        private val tvCantidad: TextView = itemView.findViewById(R.id.tv_req_cantidad)
        private val tvArea: TextView = itemView.findViewById(R.id.tv_req_area)
        private val tvEstado: TextView = itemView.findViewById(R.id.tv_req_estado)
        private val tvPrioridad: TextView = itemView.findViewById(R.id.tv_req_prioridad)
        private val tvFecha: TextView = itemView.findViewById(R.id.tv_req_fecha)
        
        fun bind(req: RequerimientoInsumo) {
            tvArticulo.text = req.articulo
            tvTipo.text = req.tipoInsumo
            tvCantidad.text = "${req.cantidad} ${req.unidadMedida}"
            tvArea.text = req.areaSolicitante
            tvEstado.text = req.estado
            tvPrioridad.text = req.prioridad
            tvFecha.text = "Req: ${dateFormat.format(Date(req.fechaRequerida))}"
            
            when (req.estado) {
                "Pendiente" -> tvEstado.setTextColor(Color.parseColor("#FF9800"))
                "Aprobado" -> tvEstado.setTextColor(Color.parseColor("#2196F3"))
                "Comprado" -> tvEstado.setTextColor(Color.parseColor("#9C27B0"))
                "Entregado" -> tvEstado.setTextColor(Color.parseColor("#4CAF50"))
                "Rechazado" -> tvEstado.setTextColor(Color.parseColor("#F44336"))
            }
            
            when (req.prioridad) {
                "Urgente" -> tvPrioridad.setTextColor(Color.parseColor("#F44336"))
                "Alta" -> tvPrioridad.setTextColor(Color.parseColor("#FF9800"))
                "Media" -> tvPrioridad.setTextColor(Color.parseColor("#2196F3"))
                "Baja" -> tvPrioridad.setTextColor(Color.parseColor("#4CAF50"))
            }
            
            itemView.setOnClickListener { onItemClick(req) }
        }
    }
    
    private class RequerimientoDiffCallback : DiffUtil.ItemCallback<RequerimientoInsumo>() {
        override fun areItemsTheSame(oldItem: RequerimientoInsumo, newItem: RequerimientoInsumo): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: RequerimientoInsumo, newItem: RequerimientoInsumo): Boolean {
            return oldItem == newItem
        }
    }
}
