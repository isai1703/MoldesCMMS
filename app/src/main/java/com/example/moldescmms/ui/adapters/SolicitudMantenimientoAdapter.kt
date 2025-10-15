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

class SolicitudMantenimientoAdapter(
    private val onItemClick: (SolicitudMantenimiento) -> Unit
) : ListAdapter<SolicitudMantenimiento, SolicitudMantenimientoAdapter.SolicitudViewHolder>(SolicitudDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolicitudViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_solicitud_mantenimiento, parent, false)
        return SolicitudViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: SolicitudViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SolicitudViewHolder(
        itemView: View,
        private val onItemClick: (SolicitudMantenimiento) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val tvNumero: TextView = itemView.findViewById(R.id.tv_solicitud_numero)
        private val tvMolde: TextView = itemView.findViewById(R.id.tv_solicitud_molde)
        private val tvEstado: TextView = itemView.findViewById(R.id.tv_solicitud_estado)
        private val tvFecha: TextView = itemView.findViewById(R.id.tv_solicitud_fecha)
        private val tvPrioridad: TextView = itemView.findViewById(R.id.tv_solicitud_prioridad)

        fun bind(solicitud: SolicitudMantenimiento) {
            tvNumero.text = "Solicitud #${solicitud.id}"
            tvMolde.text = "Molde: ${solicitud.moldeId ?: "N/A"}"
            tvEstado.text = solicitud.estado
            tvPrioridad.text = solicitud.prioridad
            
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            tvFecha.text = dateFormat.format(Date(solicitud.fechaSolicitud))
            
            // Cambiar color según prioridad
            when (solicitud.prioridad) {
                "Urgente" -> tvPrioridad.setTextColor(itemView.context.getColor(android.R.color.holo_red_dark))
                "Alta" -> tvPrioridad.setTextColor(itemView.context.getColor(android.R.color.holo_orange_dark))
                else -> tvPrioridad.setTextColor(itemView.context.getColor(android.R.color.darker_gray))
            }
            
            itemView.setOnClickListener { onItemClick(solicitud) }
        }
    }

    class SolicitudDiffCallback : DiffUtil.ItemCallback<SolicitudMantenimiento>() {
        override fun areItemsTheSame(oldItem: SolicitudMantenimiento, newItem: SolicitudMantenimiento): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SolicitudMantenimiento, newItem: SolicitudMantenimiento): Boolean {
            return oldItem == newItem
        }
    }
}
