package com.example.gestordetarea

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.example.gestordetarea.R

class ReproductorVideo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reproductor_video)

        // Forzar la orientación a horizontal
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        val videoView = findViewById<VideoView>(R.id.videoView)
        val videoUri = intent.getStringExtra("videoUri")

        videoView.setVideoURI(Uri.parse(videoUri))
        // ajusta el video al centro de la pantalla
        videoView.setOnPreparedListener { mediaPlayer: MediaPlayer ->
            val videoWidth = mediaPlayer.videoWidth
            val videoHeight = mediaPlayer.videoHeight

            val videoViewWidth = videoView.width
            val videoViewHeight = videoView.height

            val xScale = videoViewWidth.toFloat() / videoWidth
            val yScale = videoViewHeight.toFloat() / videoHeight

            val scale = Math.min(xScale, yScale)

            val layoutParams = videoView.layoutParams
            layoutParams.width = (videoWidth * scale).toInt()
            layoutParams.height = (videoHeight * scale).toInt()
            videoView.layoutParams = layoutParams

            videoView.start()
        }

        val mediaController = android.widget.MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)
    }
}
