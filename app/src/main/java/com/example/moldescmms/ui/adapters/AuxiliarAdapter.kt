package com.example.moldescmms.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moldescmms.R
import com.example.moldescmms.data.entities.TecnicoTaller

class AuxiliarAdapter(
    private val onItemClick: (TecnicoTaller) -> Unit
) : ListAdapter<TecnicoTaller, AuxiliarAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_auxiliar, parent, false)
        return ViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        itemView: View,
        private val onItemClick: (TecnicoTaller) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(auxiliar: TecnicoTaller) {
            itemView.findViewById<TextView>(R.id.tv_nombre).text = auxiliar.nombre
            itemView.findViewById<TextView>(R.id.tv_turno).text = "Turno: ${auxiliar.turnoPreferente}"
            itemView.findViewById<TextView>(R.id.tv_tareas).text = "Tareas: ${auxiliar.solicitudesEnProceso}"
            itemView.setOnClickListener { onItemClick(auxiliar) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<TecnicoTaller>() {
        override fun areItemsTheSame(old: TecnicoTaller, new: TecnicoTaller) = old.id == new.id
        override fun areContentsTheSame(old: TecnicoTaller, new: TecnicoTaller) = old == new
    }
}
