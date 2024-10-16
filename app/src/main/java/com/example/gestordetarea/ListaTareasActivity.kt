package com.example.gestordetarea

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

@Suppress("DEPRECATION")
class ListaTareasActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tareaAdapter: TareaAdapter
    private var listaTareas = mutableListOf<Tarea>()

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_tareas)

        // Inicializa la lista de tareas con datos simulados
        listaTareas.add(Tarea(id = 1, nombre = "Tarea 1", descripcion = "Descripción de la tarea 1", fechaInicio = "2024-04-10", fechaFin = "2024-04-12"))
        listaTareas.add(Tarea(id = 2, nombre = "Tarea 2", descripcion = "Descripción de la tarea 2", fechaInicio = "2024-04-11", fechaFin = "2024-04-13"))

        // Configurar el RecyclerView
        recyclerView = findViewById(R.id.rvListaTareas)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Configurar el adaptador del RecyclerView
        tareaAdapter = TareaAdapter(listaTareas)
        recyclerView.adapter = tareaAdapter

        // Configurar el botón para abrir NuevaTareaActivity
        val btnCrear = findViewById<Button>(R.id.btnCrear)
        btnCrear.setOnClickListener {
            val intent = Intent(this, NuevaTareaActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_NUEVA_TAREA)
        }

        // Configurar el botón Cancelar
        val btnCancelar = findViewById<Button>(R.id.btnCancelar)
        btnCancelar.setOnClickListener {
            finish() // Cierra la actividad y regresa a la anterior
        }

        // Configurar el listener para manejar clics en las tareas
        tareaAdapter.setOnTareaClickListener { tarea ->
            val intent = Intent(this, NuevaTareaActivity::class.java).apply {
                putExtra("ID_TAREA", tarea.id)
                putExtra("NOMBRE_TAREA", tarea.nombre)
                putExtra("DESCRIPCION_TAREA", tarea.descripcion)
                putExtra("FECHA_INICIO", tarea.fechaInicio)
                putExtra("FECHA_FIN", tarea.fechaFin)
            }
            startActivityForResult(intent, REQUEST_CODE_NUEVA_TAREA)
        }
    }

    // Manejar el resultado de NuevaTareaActivity
    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_NUEVA_TAREA && resultCode == RESULT_OK) {
            data?.let {
                val idTarea = it.getIntExtra("ID_TAREA", -1)
                val nombreTarea = it.getStringExtra("NOMBRE_TAREA") ?: "Tarea sin nombre"
                val descripcionTarea = it.getStringExtra("DESCRIPCION_TAREA") ?: "Sin descripción"
                val fechaInicio = it.getStringExtra("FECHA_INICIO") ?: "Sin fecha de inicio"
                val fechaFin = it.getStringExtra("FECHA_FIN") ?: "Sin fecha de fin"

                // Verifica si se está modificando una tarea existente
                if (idTarea != -1) {
                    listaTareas.find { it.id == idTarea }?.let { tareaExistente ->
                        tareaExistente.nombre = nombreTarea
                        tareaExistente.descripcion = descripcionTarea
                        tareaExistente.fechaInicio = fechaInicio
                        tareaExistente.fechaFin = fechaFin
                        tareaAdapter.notifyDataSetChanged() // Notificar que la lista ha cambiado
                        Log.d("ListaTareasActivity", "Tarea modificada: $tareaExistente")
                    }
                } else {
                    // Crear un nuevo objeto Tarea y agregarlo a la lista
                    val nuevaTarea = Tarea(
                        id = listaTareas.size + 1, // Asegúrate de que el ID sea único
                        nombre = nombreTarea,
                        descripcion = descripcionTarea,
                        fechaInicio = fechaInicio,
                        fechaFin = fechaFin
                    )
                    listaTareas.add(nuevaTarea)
                    tareaAdapter.notifyItemInserted(listaTareas.size - 1) // Notificar al adaptador que se ha agregado un nuevo elemento
                    Log.d("ListaTareasActivity", "Tarea agregada: $nuevaTarea")
                }
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_NUEVA_TAREA = 1 // Código de solicitud
    }
}
