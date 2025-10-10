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
import com.example.moldescmms.data.entities.RefaccionMaquina

class RefaccionMaquinaAdapter(
    private val onItemClick: (RefaccionMaquina) -> Unit
) : ListAdapter<RefaccionMaquina, RefaccionMaquinaAdapter.RefaccionViewHolder>(RefaccionDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RefaccionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_refaccion_maquina, parent, false)
        return RefaccionViewHolder(view, onItemClick)
    }
    
    override fun onBindViewHolder(holder: RefaccionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    class RefaccionViewHolder(
        itemView: View,
        private val onItemClick: (RefaccionMaquina) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        
        private val tvNombre: TextView = itemView.findViewById(R.id.tv_refaccion_nombre)
        private val tvCodigo: TextView = itemView.findViewById(R.id.tv_refaccion_codigo)
        private val tvNumeroParte: TextView = itemView.findViewById(R.id.tv_numero_parte)
        private val tvModelo: TextView = itemView.findViewById(R.id.tv_refaccion_modelo)
        private val tvStock: TextView = itemView.findViewById(R.id.tv_refaccion_stock)
        private val tvCategoria: TextView = itemView.findViewById(R.id.tv_refaccion_categoria)
        private val tvPrecio: TextView = itemView.findViewById(R.id.tv_refaccion_precio)
        
        fun bind(refaccion: RefaccionMaquina) {
            tvNombre.text = refaccion.nombre
            tvCodigo.text = refaccion.codigoRefaccion
            tvNumeroParte.text = "P/N: ${refaccion.numeroParte}"
            tvModelo.text = refaccion.modeloMaquina
            tvStock.text = "Stock: ${refaccion.stockActual} / Min: ${refaccion.stockMinimo}"
            tvCategoria.text = refaccion.categoria
            tvPrecio.text = "$${String.format("%.2f", refaccion.precioUnitario)}"
            
            when {
                refaccion.stockActual <= 0 -> {
                    tvStock.setTextColor(Color.parseColor("#F44336"))
                    tvStock.text = "Stock: AGOTADO"
                }
                refaccion.stockActual <= refaccion.stockMinimo -> {
                    tvStock.setTextColor(Color.parseColor("#FF9800"))
                }
                else -> {
                    tvStock.setTextColor(Color.parseColor("#4CAF50"))
                }
            }
            
            itemView.setOnClickListener { onItemClick(refaccion) }
        }
    }
    
    private class RefaccionDiffCallback : DiffUtil.ItemCallback<RefaccionMaquina>() {
        override fun areItemsTheSame(oldItem: RefaccionMaquina, newItem: RefaccionMaquina): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: RefaccionMaquina, newItem: RefaccionMaquina): Boolean {
            return oldItem == newItem
        }
    }
}
