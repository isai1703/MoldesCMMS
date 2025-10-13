package com.example.moldescmms.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moldescmms.R
import com.example.moldescmms.data.entities.Proveedor

class ProveedorAdapter(
    private val onItemClick: (Proveedor) -> Unit
) : ListAdapter<Proveedor, ProveedorAdapter.ProveedorViewHolder>(ProveedorDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProveedorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_proveedor, parent, false)
        return ProveedorViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: ProveedorViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ProveedorViewHolder(
        itemView: View,
        private val onItemClick: (Proveedor) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val tvNombre: TextView = itemView.findViewById(R.id.tv_proveedor_nombre)
        private val tvCategoria: TextView = itemView.findViewById(R.id.tv_proveedor_categoria)
        private val tvContacto: TextView = itemView.findViewById(R.id.tv_proveedor_contacto)

        fun bind(proveedor: Proveedor) {
            tvNombre.text = proveedor.nombre
            tvCategoria.text = proveedor.categoria
            tvContacto.text = "${proveedor.telefono} - ${proveedor.email}"
            
            itemView.setOnClickListener { onItemClick(proveedor) }
        }
    }

    class ProveedorDiffCallback : DiffUtil.ItemCallback<Proveedor>() {
        override fun areItemsTheSame(oldItem: Proveedor, newItem: Proveedor): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Proveedor, newItem: Proveedor): Boolean {
            return oldItem == newItem
        }
    }
}
