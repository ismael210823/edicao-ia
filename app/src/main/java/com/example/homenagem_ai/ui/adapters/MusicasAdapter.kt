package com.example.homenagem_ai.ui.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homenagem_ai.R
import com.example.homenagem_ai.databinding.ItemMusicaBinding

class MusicasAdapter(
    private val context: Context,
    private val musicas: List<Uri>
) : RecyclerView.Adapter<MusicasAdapter.MusicaViewHolder>() {

    inner class MusicaViewHolder(val binding: ItemMusicaBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicaViewHolder {
        val binding = ItemMusicaBinding.inflate(LayoutInflater.from(context), parent, false)
        return MusicaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MusicaViewHolder, position: Int) {
        val uri = musicas[position]
        holder.binding.imageViewMusica.setImageResource(R.drawable.ic_audio_block)
        holder.binding.textViewMusica.text = "Música ${position + 1}"
    }

    override fun getItemCount(): Int = musicas.size
}
