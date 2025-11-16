package com.example.homenagem_ai.ui.adapters

import android.app.Activity
import android.content.Context
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.homenagem_ai.R
import com.example.homenagem_ai.databinding.ItemMidiaBlockBinding
import com.example.homenagem_ai.model.MidiaType
import com.example.homenagem_ai.viewmodel.SharedUploadViewModel

class MidiaAdapter(
    private val context: Context,
    private val uris: List<Uri>,
    private val type: MidiaType,
    private val autores: Map<Uri, String> = emptyMap() // novo parâmetro
) : RecyclerView.Adapter<MidiaAdapter.MidiaViewHolder>() {


    inner class MidiaViewHolder(val binding: ItemMidiaBlockBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MidiaViewHolder {
        val binding = ItemMidiaBlockBinding.inflate(LayoutInflater.from(context), parent, false)
        return MidiaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MidiaViewHolder, position: Int) {
        val uri = uris[position]
        val iv = holder.binding.imageView
        val tv = holder.binding.textView

        when (type) {
            MidiaType.FOTO -> {
                iv.setImageURI(uri)
                tv.text = "Foto ${position + 1}"
            }
            MidiaType.VIDEO -> {
                try {
                    val retriever = MediaMetadataRetriever()
                    retriever.setDataSource(context, uri)
                    val frame = retriever.getFrameAtTime(1000000)
                    iv.setImageBitmap(frame)
                    retriever.release()
                } catch (_: Exception) {
                    iv.setImageResource(android.R.drawable.ic_media_play)
                }
                tv.text = "Vídeo ${position + 1}"
            }
            MidiaType.AUDIO -> {
                iv.setImageResource(R.drawable.ic_audio_block)

                val isMusica = uris.size == 1 && type == MidiaType.AUDIO && autores.isEmpty()
                val nome = autores[uri]?.takeIf { it.isNotBlank() }
                tv.text = when {
                    isMusica -> "Música de Fundo"
                    nome != null -> nome
                    else -> "Áudio ${position + 1}"
                }
            }
            MidiaType.MUSICA -> {
                iv.setImageResource(R.drawable.ic_music_block)
                tv.text = "Música ${position + 1}"

            }




        }
    }

    override fun getItemCount(): Int = uris.size
}
