package com.example.gestordetarea.Tareas

data class Tarea(
    var nombre: String,
    var descripcion: String,
    var fechaInicio: String,
    var fechaFin: String,
    val id: Int
)
