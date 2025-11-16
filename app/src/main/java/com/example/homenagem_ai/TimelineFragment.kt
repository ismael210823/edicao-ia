package com.example.homenagem_ai.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homenagem_ai.R
import com.example.homenagem_ai.databinding.FragmentTimelineBinding
import com.example.homenagem_ai.model.MidiaType
import com.example.homenagem_ai.model.TimelineSection
import com.example.homenagem_ai.ui.adapters.SectionAdapter
import com.example.homenagem_ai.viewmodel.SharedUploadViewModel

class TimelineFragment : Fragment() {

    private var _binding: FragmentTimelineBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SharedUploadViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTimelineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val allSections = mutableListOf(
            TimelineSection("Fotos", viewModel.fotos, MidiaType.FOTO),
            TimelineSection("Vídeos", viewModel.videos, MidiaType.VIDEO),
            TimelineSection("Áudios", viewModel.audios, MidiaType.AUDIO)
        ).filter { it.items.isNotEmpty() }.toMutableList()

        if (viewModel.musicas.isNotEmpty()) {
            val musicasSection = TimelineSection(
                title = "Músicas",
                items = viewModel.musicas,
                type = MidiaType.MUSICA
            )
            allSections.add(musicasSection)
        }

        binding.recyclerSections.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerSections.adapter = SectionAdapter(requireContext(), allSections)
        binding.btnAvancar.setOnClickListener {
            findNavController().navigate(R.id.action_timelineFragment_to_processamentoFragment)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
