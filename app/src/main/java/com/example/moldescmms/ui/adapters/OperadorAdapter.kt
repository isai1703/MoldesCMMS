package com.example.moldescmms.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moldescmms.R
import com.example.moldescmms.data.entities.Operador

class OperadorAdapter(
    private val onItemClick: (Operador) -> Unit
) : ListAdapter<Operador, OperadorAdapter.OperadorViewHolder>(OperadorDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OperadorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_operador, parent, false)
        return OperadorViewHolder(view, onItemClick)
    }
    
    override fun onBindViewHolder(holder: OperadorViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    class OperadorViewHolder(
        itemView: View,
        private val onItemClick: (Operador) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        
        private val tvNombre: TextView = itemView.findViewById(R.id.tv_operador_nombre)
        private val tvNumeroEmpleado: TextView = itemView.findViewById(R.id.tv_numero_empleado)
        private val tvDepartamento: TextView = itemView.findViewById(R.id.tv_operador_departamento)
        private val tvTurno: TextView = itemView.findViewById(R.id.tv_operador_turno)
        private val tvNivel: TextView = itemView.findViewById(R.id.tv_operador_nivel)
        
        fun bind(operador: Operador) {
            tvNombre.text = operador.nombreCompleto
            tvNumeroEmpleado.text = "No. ${operador.numeroEmpleado}"
            tvDepartamento.text = operador.departamento
            tvTurno.text = operador.turno
            tvNivel.text = operador.nivelExperiencia
            
            itemView.setOnClickListener { onItemClick(operador) }
        }
    }
    
    private class OperadorDiffCallback : DiffUtil.ItemCallback<Operador>() {
        override fun areItemsTheSame(oldItem: Operador, newItem: Operador): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: Operador, newItem: Operador): Boolean {
            return oldItem == newItem
        }
    }
}
