package com.example.homenagem_ai.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homenagem_ai.R
import com.example.homenagem_ai.databinding.FragmentUploadAudiosBinding
import com.example.homenagem_ai.ui.adapters.AudiosAdapter
import com.example.homenagem_ai.viewmodel.SharedUploadViewModel

class UploadAudiosFragment : Fragment() {

    private lateinit var binding: FragmentUploadAudiosBinding
    private val viewModel: SharedUploadViewModel by activityViewModels()
    private lateinit var adapter: AudiosAdapter

    private val selecionarAudiosLauncher = registerForActivityResult(
        ActivityResultContracts.OpenMultipleDocuments()
    ) { uris ->
        uris?.forEach { uri ->
            if (!viewModel.audios.contains(uri)) {
                requireContext().contentResolver.takePersistableUriPermission(
                    uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                viewModel.audios.add(uri)
            }
        }
        adapter.notifyDataSetChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUploadAudiosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = AudiosAdapter(requireContext(), viewModel.audios, viewModel.autoresAudios)
        binding.recyclerAudios.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerAudios.adapter = adapter

        binding.btnSelecionarAudios.setOnClickListener {
            selecionarAudiosLauncher.launch(arrayOf("audio/*"))
        }

        binding.btnContinuarAudios.setOnClickListener {
            if (viewModel.audios.isEmpty()) {
                Toast.makeText(requireContext(), "Selecione pelo menos um áudio", Toast.LENGTH_SHORT).show()
            } else {
                findNavController().navigate(R.id.action_uploadAudiosFragment_to_uploadVideosFragment)
            }
        }
    }
}
