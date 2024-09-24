package com.example.gestordetarea

import Video
import VideoAdapter
import VideoDatabase
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListaVideos : AppCompatActivity() {
    private lateinit var videoAdapter: VideoAdapter
    private lateinit var videoDatabase: VideoDatabase

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_videos)

        videoDatabase = VideoDatabase(this)

        // Insertar videos en la base de datos (solo si no están ya insertados)
        insertInitialVideos()

        val videoList = videoDatabase.getAllVideos()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        videoAdapter = VideoAdapter(videoList) { video ->
            // Abre la actividad para reproducir el video seleccionado
            val intent = Intent(this, ReproductorVideo::class.java)
            intent.putExtra("videoUri", video.uri)
            startActivity(intent)
        }
        recyclerView.adapter = videoAdapter
    }

    // Método para insertar videos iniciales en la base de datos
    private fun insertInitialVideos() {
        videoDatabase = VideoDatabase(this)
        val videoList = videoDatabase.getAllVideos()

        // Solo insertar si la lista está vacía
        if (videoList.isEmpty()) {
            val videosToInsert = listOf(
                Video(0, "Video 1", "android.resource://$packageName/${R.raw.video1}"),
                Video(0, "Video 2", "android.resource://$packageName/${R.raw.video2}"),
                Video(0, "Video 3", "android.resource://$packageName/${R.raw.video3}")
            )

            videosToInsert.forEach { video ->
                videoDatabase.insertVideo(video)
            }
        }
    }
}


