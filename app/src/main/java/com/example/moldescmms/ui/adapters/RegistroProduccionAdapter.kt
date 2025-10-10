package com.example.moldescmms.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moldescmms.R
import com.example.moldescmms.data.entities.RegistroProduccion
import java.text.SimpleDateFormat
import java.util.*

class RegistroProduccionAdapter(
    private val onItemClick: (RegistroProduccion) -> Unit
) : ListAdapter<RegistroProduccion, RegistroProduccionAdapter.RegistroViewHolder>(RegistroDiffCallback()) {
    
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistroViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_registro_produccion, parent, false)
        return RegistroViewHolder(view, onItemClick, dateFormat)
    }
    
    override fun onBindViewHolder(holder: RegistroViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    class RegistroViewHolder(
        itemView: View,
        private val onItemClick: (RegistroProduccion) -> Unit,
        private val dateFormat: SimpleDateFormat
    ) : RecyclerView.ViewHolder(itemView) {
        
        private val tvFecha: TextView = itemView.findViewById(R.id.tv_registro_fecha)
        private val tvTurno: TextView = itemView.findViewById(R.id.tv_registro_turno)
        private val tvPiezas: TextView = itemView.findViewById(R.id.tv_registro_piezas)
        private val tvDefectos: TextView = itemView.findViewById(R.id.tv_registro_defectos)
        private val tvEficiencia: TextView = itemView.findViewById(R.id.tv_registro_eficiencia)
        
        fun bind(registro: RegistroProduccion) {
            tvFecha.text = dateFormat.format(Date(registro.fechaInicio))
            tvTurno.text = registro.turno
            tvPiezas.text = "Producidas: ${registro.piezasProducidas}"
            tvDefectos.text = "Defectuosas: ${registro.piezasDefectuosas}"
            
            val eficiencia = if (registro.piezasProducidas > 0) {
                val porcentajeDefectos = (registro.piezasDefectuosas.toFloat() / registro.piezasProducidas * 100)
                100 - porcentajeDefectos
            } else 0f
            
            tvEficiencia.text = "Eficiencia: ${"%.1f".format(eficiencia)}%"
            
            itemView.setOnClickListener { onItemClick(registro) }
        }
    }
    
    private class RegistroDiffCallback : DiffUtil.ItemCallback<RegistroProduccion>() {
        override fun areItemsTheSame(oldItem: RegistroProduccion, newItem: RegistroProduccion): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: RegistroProduccion, newItem: RegistroProduccion): Boolean {
            return oldItem == newItem
        }
    }
}
