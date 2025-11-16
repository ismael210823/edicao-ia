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
import com.example.homenagem_ai.databinding.FragmentUploadFotosBinding
import com.example.homenagem_ai.ui.adapters.FotosAdapter
import com.example.homenagem_ai.viewmodel.SharedUploadViewModel


class UploadFotosFragment : Fragment() {
    private val viewModel: SharedUploadViewModel by activityViewModels()
    private lateinit var binding: FragmentUploadFotosBinding

    private val REQUEST_FOTOS = 1001
    private lateinit var adapter: FotosAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUploadFotosBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = FotosAdapter(viewModel.fotos)
        binding.recyclerFotos.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerFotos.adapter = adapter  // ← ESSENCIAL


        binding.btnSelecionarFotos.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                addCategory(Intent.CATEGORY_OPENABLE)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
            }
            startActivityForResult(intent, REQUEST_FOTOS)
        }

        binding.btnContinuar.setOnClickListener {
            if (viewModel.fotos.isEmpty()) {
                Toast.makeText(context, "Selecione ao menos uma foto", Toast.LENGTH_SHORT).show()
            } else {
                findNavController().navigate(R.id.action_uploadFotosFragment_to_uploadAudiosFragment)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_FOTOS && resultCode == Activity.RESULT_OK && data != null) {
            val novasFotos = extractUris(data)
            novasFotos.forEach { novaUri ->
                if (!viewModel.fotos.contains(novaUri)) {
                    viewModel.fotos.add(novaUri)
                }
            }

            adapter.notifyDataSetChanged()  // usa a mesma lista da viewModel
        }
    }


    private fun extractUris(data: Intent): List<Uri> {
        val uris = mutableListOf<Uri>()
        val flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

        data.clipData?.let { clip ->
            for (i in 0 until clip.itemCount) {
                val uri = clip.getItemAt(i).uri
                requireContext().contentResolver.takePersistableUriPermission(uri, flags)
                uris.add(uri)
            }
        } ?: data.data?.let { uri ->
            requireContext().contentResolver.takePersistableUriPermission(uri, flags)
            uris.add(uri)
        }

        return uris
    }



}
