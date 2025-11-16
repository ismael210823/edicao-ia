package com.example.homenagem_ai.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.homenagem_ai.R
import com.example.homenagem_ai.databinding.FragmentProcessamentoBinding

class ProcessamentoFragment : Fragment() {

    private var _binding: FragmentProcessamentoBinding? = null
    private val binding get() = _binding!!

    private val handler = Handler(Looper.getMainLooper())
    private var progress = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProcessamentoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        simulateProgress()
    }

    private fun simulateProgress() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (progress < 100) {
                    progress += 1
                    binding.progressBar.progress = progress
                    handler.postDelayed(this, 50)
                } else {
                    binding.tvMensagem.text = "Vídeo gerado com sucesso!"
                    // Aqui você pode navegar para a próxima tela, como a de preview ou download
                    findNavController().navigate(R.id.action_processamentoFragment_to_resultadoFragment)

                }
            }
        }, 50)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        handler.removeCallbacksAndMessages(null)
    }
}
