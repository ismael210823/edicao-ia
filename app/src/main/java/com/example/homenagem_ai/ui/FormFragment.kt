package com.example.homenagem_ai.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.homenagem_ai.R
import com.example.homenagem_ai.databinding.FragmentFormBinding
import com.example.homenagem_ai.viewmodel.SharedUploadViewModel

class FormFragment : Fragment() {

    private var _binding: FragmentFormBinding? = null
    private val binding get() = _binding!!

    private val args: FormFragmentArgs by navArgs() // navigation-safe args
    private val viewModel: SharedUploadViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tipo = args.tipoHomenagem
        Toast.makeText(requireContext(), "Tipo escolhido: $tipo", Toast.LENGTH_SHORT).show()

        binding.btnAvancar.setOnClickListener {
            val nome = binding.editTextNome.text.toString()
            val descricao = binding.editTextDescricao.text.toString()

            if (nome.isBlank()) {
                binding.editTextNome.error = "Informe o nome do homenageado"
                return@setOnClickListener
            }

            viewModel.descricao = descricao

            val mensagens = mutableListOf<String>()
            for (i in 0 until binding.mensagensContainer.childCount) {
                val view = binding.mensagensContainer.getChildAt(i)
                if (view is EditText) {
                    val texto = view.text.toString()
                    if (texto.isNotBlank()) mensagens.add(texto)
                }
            }

            findNavController().navigate(R.id.action_formFragment_to_uploadFotosFragment)
        }



        binding.btnAddMensagem.setOnClickListener {
            val novaMensagem = EditText(requireContext()).apply {
                hint = "Nova mensagem personalizada"
                setBackgroundResource(R.drawable.bg_edittext)
                setPadding(32, 24, 32, 24)
                setTextColor(resources.getColor(android.R.color.black, null))
                setHintTextColor(resources.getColor(android.R.color.darker_gray, null))
                minLines = 3
                maxLines = 5
                layoutParams = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    bottomMargin = 24
                }
            }
            binding.mensagensContainer.addView(novaMensagem)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
