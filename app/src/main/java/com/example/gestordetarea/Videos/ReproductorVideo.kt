package com.example.gestordetarea.Videos

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import com.example.gestordetarea.R
import com.example.gestordetarea.Utils.FullScreenVideoView

class ReproductorVideo : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reproductor_video)

        // Forzar orientación horizontal
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        val videoView = findViewById<FullScreenVideoView>(R.id.videoView)

        // Obtener el URI del video desde la intención
        val videoUri = intent.getStringExtra("videoUri")?.let { Uri.parse(it) }

        // Configurar el VideoView
        videoUri?.let {
            videoView.setVideoURI(it)
            val mediaController = MediaController(this)
            mediaController.setAnchorView(videoView)
            videoView.setMediaController(mediaController)
            videoView.start()
        }
    }
}
