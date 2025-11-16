package com.example.homenagem_ai.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homenagem_ai.R
import com.example.homenagem_ai.model.MidiaType
import com.example.homenagem_ai.model.TimelineSection
import com.example.homenagem_ai.viewmodel.SharedUploadViewModel

class SectionAdapter(
    private val context: Context,
    private val sections: List<TimelineSection>
) : RecyclerView.Adapter<SectionAdapter.SectionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_section, parent, false)
        return SectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        holder.bind(sections[position])
    }

    override fun getItemCount(): Int = sections.size

    inner class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvSectionTitle)
        private val recycler: RecyclerView = itemView.findViewById(R.id.recyclerSection)

        fun bind(section: TimelineSection) {
            tvTitle.text = section.title
            recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            val autores = if (section.type == MidiaType.AUDIO) {
                (context as? FragmentActivity)?.let {
                    ViewModelProvider(it)[SharedUploadViewModel::class.java].autoresAudios
                } ?: emptyMap()
            } else emptyMap()

            recycler.adapter = MidiaAdapter(context, section.items, section.type, autores)

        }
    }
}
