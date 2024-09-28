import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gestordetarea.R

class VideoAdapter(private val videos: List<Video>, private val onVideoClick: (Video) -> Unit) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

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

        // Aquí puedes configurar el ícono si cada video tiene uno personalizado
        //holder.videoIcon.setImageResource(R.drawable.ic_video_placeholder)

        // Configura el icono de reproducción
        holder.playIcon.setOnClickListener {
            onVideoClick(video)
        }
    }

    override fun getItemCount(): Int = videos.size
}
