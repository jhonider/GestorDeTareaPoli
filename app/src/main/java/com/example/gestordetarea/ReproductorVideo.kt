package com.example.gestordetarea

import android.annotation.SuppressLint
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

        val videoView = findViewById<VideoView>(R.id.videoView)
        val videoUri = intent.getStringExtra("videoUri")

        videoView.setVideoURI(Uri.parse(videoUri))
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)
        videoView.start()
    }
}
