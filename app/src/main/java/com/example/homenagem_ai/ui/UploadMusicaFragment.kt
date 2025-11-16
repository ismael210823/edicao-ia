package com.example.homenagem_ai.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.homenagem_ai.R
import com.example.homenagem_ai.databinding.FragmentUploadMusicaBinding
import com.example.homenagem_ai.ui.adapters.MusicasAdapter
import com.example.homenagem_ai.viewmodel.SharedUploadViewModel

class UploadMusicaFragment : Fragment() {

    private val viewModel: SharedUploadViewModel by activityViewModels()
    private lateinit var binding: FragmentUploadMusicaBinding
    private lateinit var adapter: MusicasAdapter

    private val selecionarMusicasLauncher = registerForActivityResult(
        ActivityResultContracts.OpenMultipleDocuments()
    ) { uris ->
        uris?.forEach { uri ->
            if (!viewModel.musicas.contains(uri)) {
                requireContext().contentResolver.takePersistableUriPermission(
                    uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                viewModel.musicas.add(uri)
            }
        }
        adapter.notifyDataSetChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUploadMusicaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = MusicasAdapter(requireContext(), viewModel.musicas)
        binding.recyclerMusicas.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerMusicas.adapter = adapter

        binding.btnSelecionarMusica.setOnClickListener {
            selecionarMusicasLauncher.launch(arrayOf("audio/*"))
        }

        binding.btnFinalizar.setOnClickListener {
            findNavController().navigate(R.id.action_uploadMusicaFragment_to_timelineFragment)
        }
    }
}
