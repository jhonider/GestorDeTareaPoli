import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.media.ThumbnailUtils
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gestordetarea.R

class VideoAdapter(private var videos: List<Video>, private val onVideoClick: (Video) -> Unit) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    // Método para actualizar los datos
    fun updateData(newList: List<Video>) {
        videos = newList
        notifyDataSetChanged()  // Notificar al RecyclerView que los datos han cambiado
    }


    class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val videoIcon: ImageView = view.findViewById(R.id.videoIcon)
        val videoTitle: TextView = view.findViewById(R.id.videoTitle)
        val playIcon: ImageView = view.findViewById(R.id.playIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.video_item, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = videos[position]
        holder.videoTitle.text = video.title

        val videoThumbnail: Bitmap? = ThumbnailUtils.createVideoThumbnail(
            video.uri,  // Ruta del video
            MediaStore.Video.Thumbnails.MINI_KIND  // Especificamos que es un video
        )

        // Asignar la miniatura al ImageView
        videoThumbnail?.let {
            holder.videoIcon.setImageBitmap(it)
        } ?: holder.videoIcon.setImageResource(R.drawable.ic_video)

        // Configura el icono de reproducción
        holder.playIcon.setOnClickListener {
            onVideoClick(video)
        }
    }

    override fun getItemCount(): Int = videos.size
}
