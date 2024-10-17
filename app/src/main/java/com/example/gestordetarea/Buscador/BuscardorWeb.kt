package com.example.gestordetarea.Buscador

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gestordetarea.R

class BuscardorWeb : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var searchBar: EditText
    private lateinit var searchButton: Button

    @SuppressLint()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscador_web)

        // Inicializa los componentes
        webView = findViewById(R.id.webView)
        searchBar = findViewById(R.id.searchBar)
        searchButton = findViewById(R.id.searchButton)

        webView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String
            ) {
                Toast.makeText(applicationContext, "Error: $description", Toast.LENGTH_SHORT).show()
            }
        }

        // Evento de click para el botón de búsqueda
        searchButton.setOnClickListener {
            val url = searchBar.text.toString()
            if (url.isNotEmpty()) {
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    webView.loadUrl("https://$url")
                }
            }
        }
    }
}