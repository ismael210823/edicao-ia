package com.example.homenagem_ai.ui.adapters

import android.content.Context
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homenagem_ai.R

class AudiosAdapter(
    private val context: Context,
    private val audioUris: List<Uri>,
    private val autores: MutableMap<Uri, String>
) : RecyclerView.Adapter<AudiosAdapter.AudioViewHolder>() {

    inner class AudioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNomeAudio: TextView = itemView.findViewById(R.id.tvNomeAudio)
        val etNomePessoa: EditText = itemView.findViewById(R.id.etNomePessoa)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_audio, parent, false)
        return AudioViewHolder(view)
    }

    override fun onBindViewHolder(holder: AudioViewHolder, position: Int) {
        val uri = audioUris[position]
        val nomeArquivo = uri.lastPathSegment ?: "Áudio ${position + 1}"
        holder.tvNomeAudio.text = nomeArquivo

        holder.etNomePessoa.setText(autores[uri] ?: "")

        holder.etNomePessoa.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                autores[uri] = s.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    override fun getItemCount(): Int = audioUris.size
}
