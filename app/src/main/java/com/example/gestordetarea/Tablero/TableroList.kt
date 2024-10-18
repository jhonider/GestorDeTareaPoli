package com.example.gestordetarea.Tablero

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestordetarea.R
import com.example.gestordetarea.Tareas.ListaTareasActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TableroList : AppCompatActivity(),
TableroAdapter.OnTableroClickListener,
TableroAdapter.OnTableroLongClickListener,
TableroAdapter.OnTareaClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fabAgregarTablero: FloatingActionButton
    private lateinit var tableroAdapter: TableroAdapter
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var etSearch: EditText // Añadido para la barra de búsqueda
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

    private val editarTableroLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val nombreTablero = data?.getStringExtra("NOMBRE_TABLERO") ?: ""
                val descripcionTablero = data?.getStringExtra("DESCRIPCION_TABLERO") ?: ""
                val colorTablero = data?.getIntExtra("COLOR_TABLERO", Color.WHITE) ?: Color.WHITE
                val position = data?.getIntExtra("POSITION", -1) ?: -1

                if (position >= 0 && nombreTablero.isNotEmpty() && descripcionTablero.isNotEmpty()) {
                    val tableroEditado = Tablero(nombreTablero, descripcionTablero, colorTablero)
                    tableros[position] = tableroEditado
                    listaFiltrada[position] = tableroEditado
                    tableroAdapter.notifyItemChanged(position)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tablero_list)

        // Configurar el Toolbar
//        val toolbar: Toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayShowTitleEnabled(false)
//
//        // Configurar el DrawerLayout y el menú hamburguesa
//        drawerLayout = findViewById(R.id.drawer_layout)
//        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
//        drawerLayout.addDrawerListener(toggle)
//        toggle.syncState()

        // Inicializar RecyclerView y botón flotante
        recyclerView = findViewById(R.id.rvListaTableros)
        fabAgregarTablero = findViewById(R.id.fabAgregarTablero)
        etSearch = findViewById(R.id.etSearch) // Inicializa el EditText para búsqueda

        // Inicializar la lista de tableros con algunos valores por defecto
        tableros.add(Tablero("Tablero 1", "Descripción del Tablero 1", Color.parseColor("#FF5733")))
        tableros.add(Tablero("Tablero 2", "Descripción del Tablero 2", Color.parseColor("#33FF57")))

        listaFiltrada.addAll(tableros)

        // Configurar RecyclerView
        tableroAdapter = TableroAdapter(listaFiltrada, this, this, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = tableroAdapter

        // Configurar el EditText para la búsqueda
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filter(s.toString())
            }
        })

        // Botón para añadir un nuevo tablero
        fabAgregarTablero.setOnClickListener {
            val intent = Intent(this, NuevoTableroActivity::class.java)
            nuevoTableroLauncher.launch(intent)
        }
    }

    private fun filter(query: String) {
        listaFiltrada.clear()
        if (query.isEmpty()) {
            listaFiltrada.addAll(tableros)
        } else {
            val filteredList = tableros.filter {
                it.nombre.contains(query, ignoreCase = true) ||
                        it.descripcion.contains(query, ignoreCase = true)
            }
            listaFiltrada.addAll(filteredList)
        }
        tableroAdapter.notifyDataSetChanged()
    }

    override fun onTableroClick(tablero: Tablero, position: Int) {
        val intent = Intent(this, EditarTableroActivity::class.java)
        intent.putExtra("NOMBRE_TABLERO", tablero.nombre)
        intent.putExtra("DESCRIPCION_TABLERO", tablero.descripcion)
        intent.putExtra("COLOR_TABLERO", tablero.color)
        intent.putExtra("POSITION", position)
        editarTableroLauncher.launch(intent)
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

    override fun onTareaClick(tablero: Tablero, position: Int) {
        val intent = Intent(this, ListaTareasActivity::class.java)
        intent.putExtra("TABLERO_ID", position)
        intent.putExtra("NOMBRE_TABLERO", tablero.nombre)
        startActivity(intent)
    }
}