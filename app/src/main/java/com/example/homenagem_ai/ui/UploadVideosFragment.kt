package com.example.homenagem_ai.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.homenagem_ai.R
import com.example.homenagem_ai.ui.adapters.VideosAdapter
import com.example.homenagem_ai.databinding.FragmentUploadVideosBinding
import com.example.homenagem_ai.viewmodel.SharedUploadViewModel

class UploadVideosFragment : Fragment() {

    private val viewModel: SharedUploadViewModel by activityViewModels()
    private lateinit var binding: FragmentUploadVideosBinding
    private lateinit var adapter: VideosAdapter

    private val selecionarVideosLauncher = registerForActivityResult(
        androidx.activity.result.contract.ActivityResultContracts.OpenMultipleDocuments()
    ) { uris ->
        uris?.forEach { uri ->
            if (!viewModel.videos.contains(uri)) {
                requireContext().contentResolver.takePersistableUriPermission(
                    uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                viewModel.videos.add(uri)
            }
        }
        adapter.notifyDataSetChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUploadVideosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = VideosAdapter(requireContext(), viewModel.videos)
        binding.recyclerVideos.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerVideos.adapter = adapter

        binding.btnSelecionarVideos.setOnClickListener {
            selecionarVideosLauncher.launch(arrayOf("video/*"))
        }

        binding.btnContinuar.setOnClickListener {
            findNavController().navigate(R.id.action_uploadVideosFragment_to_uploadMusicaFragment)
        }
    }
}
