package com.example.gestordetarea

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditarTableroActivity : AppCompatActivity() {

    private lateinit var etNombreTablero: EditText
    private lateinit var etDescripcionTablero: EditText
    private lateinit var btnGuardarCambios: Button
    private var position: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_tablero)

        // Inicializar las vistas
        etNombreTablero = findViewById(R.id.etNombreTablero)
        etDescripcionTablero = findViewById(R.id.etDescripcionTablero)
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios)

        // Recuperar los datos pasados desde MainActivity
        val intent = intent
        val nombreTablero = intent.getStringExtra("NOMBRE_TABLERO")
        val descripcionTablero = intent.getStringExtra("DESCRIPCION_TABLERO")
        position = intent.getIntExtra("POSITION", -1)

        // Poner los datos en los campos de texto para que el usuario pueda editarlos
        etNombreTablero.setText(nombreTablero)
        etDescripcionTablero.setText(descripcionTablero)

        // Configurar el botón "Guardar Cambios"
        btnGuardarCambios.setOnClickListener {
            val nombreEditado = etNombreTablero.text.toString()
            val descripcionEditada = etDescripcionTablero.text.toString()

            if (nombreEditado.isNotEmpty() && descripcionEditada.isNotEmpty()) {
                // Crear un Intent para enviar los resultados de vuelta a MainActivity
                val resultIntent = Intent()
                resultIntent.putExtra("NOMBRE_TABLERO", nombreEditado)
                resultIntent.putExtra("DESCRIPCION_TABLERO", descripcionEditada)
                resultIntent.putExtra("POSITION", position)
                setResult(Activity.RESULT_OK, resultIntent) // Devolver el resultado a MainActivity
                finish() // Cerrar la actividad
            }
        }
    }
}
