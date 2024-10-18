package com.example.gestordetarea.Tablero

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestordetarea.Tareas.ListaTareasActivity
import com.example.gestordetarea.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListaTablerosActivity : AppCompatActivity(),
    TableroAdapter.OnTableroClickListener,
    TableroAdapter.OnTableroLongClickListener,
    TableroAdapter.OnTareaClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fabAgregarTablero: FloatingActionButton
    private lateinit var tableroAdapter: TableroAdapter
    private lateinit var etBuscar: EditText
    private var tableros = mutableListOf<Tablero>()
    private var listaFiltrada = mutableListOf<Tablero>()

    private val nuevoTableroLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val nombreTablero = data?.getStringExtra("NOMBRE_TABLERO") ?: ""
                val descripcionTablero = data?.getStringExtra("DESCRIPCION_TABLERO") ?: ""
                val colorTablero = data?.getIntExtra("COLOR_TABLERO", Color.WHITE) ?: Color.WHITE

                if (nombreTablero.isNotEmpty() && descripcionTablero.isNotEmpty()) {
                    val nuevoTablero = Tablero(nombreTablero, descripcionTablero, colorTablero)
                    tableros.add(nuevoTablero)
                    listaFiltrada.add(nuevoTablero)
                    tableroAdapter.notifyItemInserted(listaFiltrada.size - 1)
                }
            }
        }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rvListaTableros)
        fabAgregarTablero = findViewById(R.id.fabAgregarTablero)
        etBuscar = findViewById(R.id.etSearch) // Asegúrate que el ID sea correcto

        // Inicializar la lista de tableros
        tableros.add(Tablero("Tablero 1", "Descripción del Tablero 1", Color.parseColor("#FF5733")))
        tableros.add(Tablero("Tablero 2", "Descripción del Tablero 2", Color.parseColor("#33FF57")))

        listaFiltrada.addAll(tableros)

        // Configurar el RecyclerView
        tableroAdapter = TableroAdapter(listaFiltrada, this, this, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = tableroAdapter

        // Botón para añadir un nuevo tablero
        fabAgregarTablero.setOnClickListener {
            val intent = Intent(this, NuevoTableroActivity::class.java)
            nuevoTableroLauncher.launch(intent)
        }

        // Configurar el EditText para buscar tableros
        etBuscar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filtrarTableros(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun filtrarTableros(query: String) {
        Log.d("ListaTablerosActivity", "Filtrando tableros con la consulta: $query")
        listaFiltrada.clear()
        if (query.isEmpty()) {
            listaFiltrada.addAll(tableros)
        } else {
            for (tablero in tableros) {
                if (tablero.nombre.contains(query, ignoreCase = true)) {
                    listaFiltrada.add(tablero)
                }
            }
        }
        tableroAdapter.notifyDataSetChanged()
    }

    override fun onTableroClick(tablero: Tablero, position: Int) {
        val intent = Intent(this, EditarTableroActivity::class.java)
        intent.putExtra("TABLERO_ID", position)
        intent.putExtra("NOMBRE_TABLERO", tablero.nombre)
        startActivity(intent)
    }

    override fun onTareaClick(tablero: Tablero, position: Int) {
        val intent = Intent(this, ListaTareasActivity::class.java)
        intent.putExtra("TABLERO_ID", position)
        intent.putExtra("NOMBRE_TABLERO", tablero.nombre)
        startActivity(intent)
    }

    override fun onTableroLongClick(tablero: Tablero, position: Int) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar Tablero")
            .setMessage("¿Estás seguro de que deseas eliminar el tablero '${tablero.nombre}'?")
            .setPositiveButton("Eliminar") { _, _ ->
                tableros.removeAt(position)
                listaFiltrada.removeAt(position)
                tableroAdapter.notifyItemRemoved(position)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}
