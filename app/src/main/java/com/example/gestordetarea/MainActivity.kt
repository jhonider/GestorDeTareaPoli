package com.example.gestordetarea

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gestordetarea.Buscador.BuscardorWeb
import com.example.gestordetarea.Videos.ListaVideos

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val buttonBuscador : Button = findViewById(R.id.button_buscador_web)
        val buttonListVideos : Button = findViewById(R.id.button_lista_videos)

        buttonBuscador.setOnClickListener {
            val intent = Intent(this, BuscardorWeb::class.java)
            startActivity(intent)
        }

        buttonListVideos.setOnClickListener {
            val intent = Intent(this, ListaVideos::class.java)
            startActivity(intent)
        }
    }
}