package com.example.gestordetarea

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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
        val registerButton: TextView = findViewById(R.id.registerButton)
        registerButton.setOnClickListener {
            // Crea un Intent para abrir la nueva actividad
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        val loginButton: TextView = findViewById(R.id.loginButton)
        loginButton.setOnClickListener {
            // Crea un Intent para abrir la nueva actividad
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}