package com.example.gestordetarea

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestordetarea.Buscador.BuscardorWeb
import com.example.gestordetarea.Tablero.EditarTableroActivity
import com.example.gestordetarea.Tablero.NuevoTableroActivity
import com.example.gestordetarea.Tablero.Tablero
import com.example.gestordetarea.Tablero.TableroAdapter
import com.example.gestordetarea.Tablero.TableroList
import com.example.gestordetarea.Tareas.ListaTareasActivity
import com.example.gestordetarea.Videos.ListaVideos
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val buttonTablero : Button = findViewById(R.id.button_tablero)
        val buttonBuscador : Button = findViewById(R.id.button_buscador_web)
        val buttonListVideos : Button = findViewById(R.id.button_lista_videos)

        buttonTablero.setOnClickListener {
            val intent = Intent(this, TableroList::class.java)
            startActivity(intent)
        }

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
