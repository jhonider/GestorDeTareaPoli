package com.example.gestordetarea.Utils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.gestordetarea.R

class CustomHeader @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val menuIcon: ImageView
    private val appTitle: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_header, this, true)

        // Inicializamos las vistas
        menuIcon = findViewById(R.id.menuIcon)
        appTitle = findViewById(R.id.appTitle)

        // Puedes configurar eventos o comportamientos adicionales aquí
    }

    fun setAppTitle(text: String) {
        appTitle.text = text
    }

    fun setMenuIconClickListener(listener: OnClickListener) {
        menuIcon.setOnClickListener(listener)
    }
}
