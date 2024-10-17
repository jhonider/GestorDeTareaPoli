package com.example.gestordetarea.Tablero

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gestordetarea.R

class TableroAdapter(
    private val tableros: List<Tablero>,
    private val clickListener: OnTableroClickListener,
    private val longClickListener: OnTableroLongClickListener,
    private val onTareaClickListener: OnTareaClickListener // Listener para el botón "TAREAS"
) : RecyclerView.Adapter<TableroAdapter.TableroViewHolder>() {

    interface OnTableroClickListener {
        fun onTableroClick(tablero: Tablero, position: Int)
    }

    interface OnTableroLongClickListener {
        fun onTableroLongClick(tablero: Tablero, position: Int)
    }

    interface OnTareaClickListener {
        fun onTareaClick(tablero: Tablero, position: Int) // Método para manejar clic en "TAREAS"
    }

    inner class TableroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitulo: TextView = itemView.findViewById(R.id.tvTituloTablero)
        private val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescripcionTablero)
        private val layoutTablero: View = itemView.findViewById(R.id.layoutTablero)
        private val btnTareas: Button = itemView.findViewById(R.id.btnTareas) // Botón de tareas

        fun bind(tablero: Tablero) {
            // Asignación de valores a los campos del tablero
            tvTitulo.text = tablero.nombre
            tvDescripcion.text = tablero.descripcion
            layoutTablero.setBackgroundColor(tablero.color)

            // Manejo de click para editar el tablero
            itemView.setOnClickListener {
                clickListener.onTableroClick(tablero, adapterPosition)
            }

            // Configuración del clic largo para eliminar el tablero
            itemView.setOnLongClickListener {
                longClickListener.onTableroLongClick(tablero, adapterPosition)
                true
            }

            // Manejo del click en el botón "TAREAS"
            btnTareas.setOnClickListener {
                onTareaClickListener.onTareaClick(tablero, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tablero, parent, false)
        return TableroViewHolder(view)
    }

    override fun onBindViewHolder(holder: TableroViewHolder, position: Int) {
        holder.bind(tableros[position])
    }

    override fun getItemCount(): Int = tableros.size
}
