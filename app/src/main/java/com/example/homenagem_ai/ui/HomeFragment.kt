package com.example.homenagem_ai.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.homenagem_ai.databinding.FragmentHomeBinding
import com.example.homenagem_ai.viewmodel.HomeViewModel
import androidx.navigation.fragment.findNavController


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAniversario.setOnClickListener {
            navigateToForm("Aniversário")
        }

        binding.btnCasamento.setOnClickListener {
            navigateToForm("Casamento")
        }

        binding.btnDespedida.setOnClickListener {
            navigateToForm("Despedida")
        }

        binding.btnOutro.setOnClickListener {
            binding.btnAniversario.visibility = View.GONE
            binding.btnCasamento.visibility = View.GONE
            binding.btnDespedida.visibility = View.GONE
            binding.btnOutro.visibility = View.GONE

            binding.editTextOutro.visibility = View.VISIBLE
            binding.btnOutroContinuar.visibility = View.VISIBLE
        }


        binding.btnOutroContinuar.setOnClickListener {
            val outroTipo = binding.editTextOutro.text.toString()
            if (outroTipo.isBlank()) {
                binding.editTextOutro.error = "Digite o tipo de homenagem"
                return@setOnClickListener
            }
            val action = HomeFragmentDirections.actionHomeFragmentToFormFragment(tipoHomenagem = outroTipo)
            findNavController().navigate(action)
        }

    }

    private fun navigateToForm(tipo: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToFormFragment(tipoHomenagem = tipo)
        findNavController().navigate(action)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
