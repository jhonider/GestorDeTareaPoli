package com.example.gestordetarea

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class NuevoTableroActivity : AppCompatActivity() {

    private lateinit var etNombreTablero: EditText
    private lateinit var etDescripcionTablero: EditText
    private lateinit var btnSeleccionarColor: ImageButton
    private lateinit var btnCrearTablero: Button
    private lateinit var btnCancelar: Button
    private lateinit var viewColorSeleccionado: View

    private var colorSeleccionado: Int = Color.TRANSPARENT // Color por defecto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_tablero)

        // Inicializar vistas
        etNombreTablero = findViewById(R.id.etNombreTablero)
        etDescripcionTablero = findViewById(R.id.etDescripcionTablero)
        btnSeleccionarColor = findViewById(R.id.btnSeleccionarColor)
        btnCrearTablero = findViewById(R.id.btnCrearTablero)
        btnCancelar = findViewById(R.id.btnCancelar)
        viewColorSeleccionado = findViewById(R.id.viewColorSeleccionado)

        // Botón para seleccionar color
        btnSeleccionarColor.setOnClickListener {
            mostrarSelectorDeColor()
        }

        // Botón para crear tablero
        btnCrearTablero.setOnClickListener {
            val nombre = etNombreTablero.text.toString()
            val descripcion = etDescripcionTablero.text.toString()

            if (nombre.isNotEmpty() && descripcion.isNotEmpty()) {
                // Pasa el color seleccionado junto con el nombre y descripción
                val data = Intent().apply {
                    putExtra("NOMBRE_TABLERO", nombre)
                    putExtra("DESCRIPCION_TABLERO", descripcion)
                    putExtra("COLOR_TABLERO", colorSeleccionado)
                }
                setResult(Activity.RESULT_OK, data)
                finish() // Finalizar actividad
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Botón para cancelar
        btnCancelar.setOnClickListener {
            finish() // Finalizar la actividad
        }
    }

    private fun mostrarSelectorDeColor() {
        // Opciones de colores simples
        val colores = arrayOf("Rojo", "Verde", "Azul", "Amarillo", "Naranja")
        val coloresHex = arrayOf(
            ContextCompat.getColor(this, android.R.color.holo_red_dark),
            ContextCompat.getColor(this, android.R.color.holo_green_dark),
            ContextCompat.getColor(this, android.R.color.holo_blue_dark),
            ContextCompat.getColor(this, android.R.color.holo_orange_light),
            ContextCompat.getColor(this, android.R.color.holo_orange_dark)
        )

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Seleccionar Color")
        builder.setItems(colores) { _, which ->
            colorSeleccionado = coloresHex[which]
            viewColorSeleccionado.setBackgroundColor(colorSeleccionado) // Cambia el color de vista seleccionada
        }
        builder.show()
    }
}
