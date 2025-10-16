package com.example.moldescmms.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moldescmms.R
import com.example.moldescmms.data.entities.SolicitudMantenimiento
import java.text.SimpleDateFormat
import java.util.*

class SolicitudPendienteAdapter(
    private val onItemClick: (SolicitudMantenimiento) -> Unit
) : ListAdapter<SolicitudMantenimiento, SolicitudPendienteAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_solicitud_pendiente, parent, false)
        return ViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        itemView: View,
        private val onItemClick: (SolicitudMantenimiento) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        
        fun bind(solicitud: SolicitudMantenimiento) {
            itemView.findViewById<TextView>(R.id.tv_solicitud_numero).text = "Solicitud #${solicitud.id}"
            itemView.findViewById<TextView>(R.id.tv_solicitud_tipo).text = solicitud.tipo
            itemView.findViewById<TextView>(R.id.tv_solicitud_departamento).text = solicitud.departamentoOrigen
            itemView.findViewById<TextView>(R.id.tv_solicitud_prioridad).text = solicitud.prioridad
            
            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            itemView.findViewById<TextView>(R.id.tv_solicitud_fecha).text = 
                dateFormat.format(Date(solicitud.fechaSolicitud))
            
            // Color según prioridad
            val tvPrioridad = itemView.findViewById<TextView>(R.id.tv_solicitud_prioridad)
            when (solicitud.prioridad) {
                "Urgente" -> tvPrioridad.setTextColor(itemView.context.getColor(android.R.color.holo_red_dark))
                "Alta" -> tvPrioridad.setTextColor(itemView.context.getColor(android.R.color.holo_orange_dark))
                else -> tvPrioridad.setTextColor(itemView.context.getColor(android.R.color.holo_blue_dark))
            }
            
            itemView.setOnClickListener { onItemClick(solicitud) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<SolicitudMantenimiento>() {
        override fun areItemsTheSame(oldItem: SolicitudMantenimiento, newItem: SolicitudMantenimiento) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: SolicitudMantenimiento, newItem: SolicitudMantenimiento) =
            oldItem == newItem
    }
}
