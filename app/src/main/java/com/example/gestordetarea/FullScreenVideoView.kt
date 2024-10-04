package com.example.gestordetarea

import android.content.Context
import android.util.AttributeSet
import android.widget.VideoView

class FullScreenVideoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : VideoView(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = getDefaultSize(0, widthMeasureSpec)
        val height = getDefaultSize(0, heightMeasureSpec)
        setMeasuredDimension(width, height)  // Ajusta el VideoView a las dimensiones de la pantalla
    }
}