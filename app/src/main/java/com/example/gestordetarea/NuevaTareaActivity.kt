package com.example.gestordetarea

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class NuevaTareaActivity : AppCompatActivity() {

    private lateinit var etNombreTarea: EditText
    private lateinit var etDescripcionTarea: EditText
    private lateinit var etFechaInicio: EditText
    private lateinit var etFechaFin: EditText
    private lateinit var btnCrearTarea: Button
    private lateinit var btnCancelarTarea: Button

    private var fechaInicio: String = ""
    private var fechaFin: String = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_tarea)

        // Inicializar vistas
        etNombreTarea = findViewById(R.id.etNombreTarea)
        etDescripcionTarea = findViewById(R.id.etDescripcionTarea)
        etFechaInicio = findViewById(R.id.etFechaInicio)
        etFechaFin = findViewById(R.id.etFechaFin)
        btnCrearTarea = findViewById(R.id.btnCrearTarea)
        btnCancelarTarea = findViewById(R.id.btnCancelarTarea)

        // Configurar el clic para abrir el calendario de fecha de inicio
        etFechaInicio.setOnClickListener {
            showDatePickerDialog { selectedDate ->
                fechaInicio = selectedDate
                etFechaInicio.setText(fechaInicio)
            }
        }

        // Configurar el clic para abrir el calendario de fecha de fin
        etFechaFin.setOnClickListener {
            showDatePickerDialog { selectedDate ->
                fechaFin = selectedDate
                etFechaFin.setText(fechaFin)
            }
        }

        // Obtener datos de la tarea si se están editando
        intent?.let {
            if (it.hasExtra("ID_TAREA")) {
                etNombreTarea.setText(it.getStringExtra("NOMBRE_TAREA"))
                etDescripcionTarea.setText(it.getStringExtra("DESCRIPCION_TAREA"))
                etFechaInicio.setText(it.getStringExtra("FECHA_INICIO"))
                etFechaFin.setText(it.getStringExtra("FECHA_FIN"))
                title = "Modificar Tarea" // Cambiar el título si se está editando
            }
        }

        // Botón para crear o modificar la tarea
        btnCrearTarea.setOnClickListener {
            Log.d("NuevaTareaActivity", "Creando o modificando tarea...")
            val nombre = etNombreTarea.text.toString()
            val descripcion = etDescripcionTarea.text.toString()

            Log.d("NuevaTareaActivity", "Nombre: $nombre, Descripción: $descripcion, Fecha Inicio: $fechaInicio, Fecha Fin: $fechaFin")

            // Validar que los campos no estén vacíos
            if (nombre.isNotEmpty() && descripcion.isNotEmpty() && fechaInicio.isNotEmpty() && fechaFin.isNotEmpty()) {
                val intent = Intent()
                intent.putExtra("ID_TAREA", intent.getIntExtra("ID_TAREA", -1)) // Pasar el ID de la tarea
                intent.putExtra("NOMBRE_TAREA", nombre)
                intent.putExtra("DESCRIPCION_TAREA", descripcion)
                intent.putExtra("FECHA_INICIO", fechaInicio)
                intent.putExtra("FECHA_FIN", fechaFin)
                setResult(RESULT_OK, intent) // Devuelve el resultado OK
                finish() // Cierra la actividad
            } else {
                Log.e("NuevaTareaActivity", "Campos vacíos!")
            }
        }

        // Botón para cancelar la tarea
        btnCancelarTarea.setOnClickListener {
            setResult(RESULT_CANCELED) // Devuelve el resultado cancelado
            finish() // Cierra la actividad
        }
    }

    // Muestra un diálogo para seleccionar la fecha
    private fun showDatePickerDialog(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Configura el diálogo de selección de fecha
        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            onDateSelected(selectedDate) // Llama al callback con la fecha seleccionada
        }, year, month, day)

        datePickerDialog.show() // Muestra el diálogo
    }
}
