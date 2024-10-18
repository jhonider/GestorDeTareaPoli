package com.example.gestordetarea.Tareas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gestordetarea.R

class TareaAdapter(private val listaTareas: MutableList<Tarea>) : RecyclerView.Adapter<TareaAdapter.TareaViewHolder>() {

    private var onTareaClickListener: ((Tarea) -> Unit)? = null

    inner class TareaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNombreTarea: TextView = itemView.findViewById(R.id.tvNombreTarea)
        private val tvDescripcionTarea: TextView = itemView.findViewById(R.id.tvDescripcionTarea)

        fun bind(tarea: Tarea) {
            tvNombreTarea.text = tarea.nombre
            tvDescripcionTarea.text = tarea.descripcion

            itemView.setOnClickListener {
                onTareaClickListener?.invoke(tarea)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TareaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tarea, parent, false)
        return TareaViewHolder(view)
    }

    override fun onBindViewHolder(holder: TareaViewHolder, position: Int) {
        holder.bind(listaTareas[position])
    }

    override fun getItemCount(): Int = listaTareas.size

    fun setOnTareaClickListener(listener: (Tarea) -> Unit) {
        onTareaClickListener = listener
    }
}
