package com.example.homenagem_ai.ui.adapters

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homenagem_ai.databinding.ItemVideoBinding
import kotlinx.coroutines.*

class VideosAdapter(
    private val context: Context,
    private val videos: List<Uri>
) : RecyclerView.Adapter<VideosAdapter.VideoViewHolder>() {

    private val thumbnailCache = mutableMapOf<Uri, Bitmap>()

    inner class VideoViewHolder(val binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = ItemVideoBinding.inflate(LayoutInflater.from(context), parent, false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val uri = videos[position]
        val imageView = holder.binding.thumbnail

        if (thumbnailCache.containsKey(uri)) {
            imageView.setImageBitmap(thumbnailCache[uri])
        } else {
            imageView.setImageResource(android.R.drawable.ic_menu_report_image) // placeholder
            CoroutineScope(Dispatchers.IO).launch {
                val retriever = MediaMetadataRetriever()
                try {
                    val pfd = context.contentResolver.openFileDescriptor(uri, "r")
                    val bitmap = pfd?.use {
                        retriever.setDataSource(it.fileDescriptor)
                        retriever.getFrameAtTime(1000000, MediaMetadataRetriever.OPTION_CLOSEST)
                    }

                    bitmap?.let {
                        thumbnailCache[uri] = it
                        withContext(Dispatchers.Main) {
                            imageView.setImageBitmap(it)
                        }
                    }

                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        imageView.setImageResource(android.R.drawable.ic_media_play)
                    }
                } finally {
                    retriever.release()
                }
            }
        }
    }

    override fun getItemCount(): Int = videos.size
}
