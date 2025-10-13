package com.example.moldescmms.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moldescmms.R
import com.example.moldescmms.data.entities.AsignacionProduccion
import java.text.SimpleDateFormat
import java.util.*

class AsignacionProduccionAdapter(
    private val onItemClick: (AsignacionProduccion) -> Unit
) : ListAdapter<AsignacionProduccion, AsignacionProduccionAdapter.AsignacionViewHolder>(AsignacionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsignacionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_asignacion_produccion, parent, false)
        return AsignacionViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: AsignacionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class AsignacionViewHolder(
        itemView: View,
        private val onItemClick: (AsignacionProduccion) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val tvNumero: TextView = itemView.findViewById(R.id.tv_asignacion_numero)
        private val tvProduccion: TextView = itemView.findViewById(R.id.tv_asignacion_produccion)
        private val tvEstado: TextView = itemView.findViewById(R.id.tv_asignacion_estado)
        private val tvEficiencia: TextView = itemView.findViewById(R.id.tv_asignacion_eficiencia)

        fun bind(asignacion: AsignacionProduccion) {
            tvNumero.text = asignacion.numeroAsignacion
            tvProduccion.text = "${asignacion.cantidadProducida} / ${asignacion.cantidadObjetivo}"
            tvEstado.text = asignacion.estado
            tvEficiencia.text = "Eficiencia: ${String.format("%.1f", asignacion.porcentajeEficiencia)}%"
            
            itemView.setOnClickListener { onItemClick(asignacion) }
        }
    }

    class AsignacionDiffCallback : DiffUtil.ItemCallback<AsignacionProduccion>() {
        override fun areItemsTheSame(oldItem: AsignacionProduccion, newItem: AsignacionProduccion): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AsignacionProduccion, newItem: AsignacionProduccion): Boolean {
            return oldItem == newItem
        }
    }
}
