package com.example.homenagem_ai.ui.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homenagem_ai.databinding.ItemFotoBinding

class FotosAdapter(private val items: List<Uri>) :
    RecyclerView.Adapter<FotosAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemFotoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.binding.ivFoto.context)
            .load(items[position])
            .centerCrop()
            .into(holder.binding.ivFoto)
    }

    override fun getItemCount() = items.size
}

