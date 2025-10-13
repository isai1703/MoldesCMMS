package com.example.moldescmms.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moldescmms.R
import com.example.moldescmms.data.entities.PreparacionMaterial
import java.text.SimpleDateFormat
import java.util.*

class PreparacionMaterialAdapter(
    private val onItemClick: (PreparacionMaterial) -> Unit
) : ListAdapter<PreparacionMaterial, PreparacionMaterialAdapter.PreparacionViewHolder>(PreparacionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreparacionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_preparacion_material, parent, false)
        return PreparacionViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: PreparacionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PreparacionViewHolder(
        itemView: View,
        private val onItemClick: (PreparacionMaterial) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val tvNumero: TextView = itemView.findViewById(R.id.tv_preparacion_numero)
        private val tvMaterial: TextView = itemView.findViewById(R.id.tv_preparacion_material)
        private val tvEstado: TextView = itemView.findViewById(R.id.tv_preparacion_estado)
        private val tvFecha: TextView = itemView.findViewById(R.id.tv_preparacion_fecha)

        fun bind(preparacion: PreparacionMaterial) {
            tvNumero.text = preparacion.numeroPreparacion
            tvMaterial.text = "${preparacion.materiaPrima} - ${preparacion.cantidadPreparada} ${preparacion.unidadMedida}"
            tvEstado.text = preparacion.estado
            
            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            tvFecha.text = dateFormat.format(Date(preparacion.fechaPreparacion))
            
            itemView.setOnClickListener { onItemClick(preparacion) }
        }
    }

    class PreparacionDiffCallback : DiffUtil.ItemCallback<PreparacionMaterial>() {
        override fun areItemsTheSame(oldItem: PreparacionMaterial, newItem: PreparacionMaterial): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PreparacionMaterial, newItem: PreparacionMaterial): Boolean {
            return oldItem == newItem
        }
    }
}
