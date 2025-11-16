package com.example.homenagem_ai.ui

import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.media3.exoplayer.ExoPlayer
import com.example.homenagem_ai.databinding.FragmentResultadoBinding
import com.example.homenagem_ai.R
import androidx.media3.common.MediaItem

import androidx.navigation.fragment.findNavController

class ResultadoFragment : Fragment() {

    private var _binding: FragmentResultadoBinding? = null
    private val binding get() = _binding!!

    private var player: ExoPlayer? = null

    private data class CategoriaViews(
        val title: String,
        val containerStars: LinearLayout,
        val positive: LinearLayout,
        val negative: LinearLayout,
        val posButtons: List<Button>,
        val negButtons: List<Button>
    )

    private val categorias = mutableMapOf<String, CategoriaViews>()
    private val ratings = mutableMapOf<String, Int>()
    private val feedbackSelected = mutableMapOf<String, MutableList<String>>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View {
        _binding = FragmentResultadoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(v: View, s: Bundle?) {

        setupPlayer()
        setupCategorias()

        binding.btnEnviarFeedback.setOnClickListener { enviarFeedback() }
        binding.btnEditar.setOnClickListener {
            findNavController().navigate(R.id.action_resultadoFragment_to_timelineFragment)
        }
    }

    private fun setupPlayer() {
        player = ExoPlayer.Builder(requireContext()).build()
        binding.playerContainer.tag = player

        val mediaItem = MediaItem.fromUri(Uri.parse("asset:///sample_video.mp4"))
        player?.setMediaItem(mediaItem)
        player?.prepare()
        player?.play()
    }

    private fun setupCategorias() {

        fun load(
            catId: Int,
            title: String,
            posTexts: List<String>,
            negTexts: List<String>
        ): CategoriaViews {

            val cat = binding.root.findViewById<View>(catId)

            val starsContainer = cat.findViewById<LinearLayout>(R.id.starsContainer)
            val posContainer = cat.findViewById<LinearLayout>(R.id.containerPositivo)
            val negContainer = cat.findViewById<LinearLayout>(R.id.containerNegativo)
            val titleView = cat.findViewById<TextView>(R.id.tvTituloCategoria)

            titleView.text = title

            // LISTA MUTÁVEL PARA REGISTRAR O QUE FOI SELECIONADO
            feedbackSelected[title] = mutableListOf()

            fun setupToggleButton(button: Button, text: String) {
                button.text = text
                button.isSelected = false

                button.setOnClickListener {
                    button.isSelected = !button.isSelected

                    if (button.isSelected) {
                        feedbackSelected[title]?.add(text)
                    } else {
                        feedbackSelected[title]?.remove(text)
                    }
                }
            }

            val posButtons = posTexts.mapIndexed { i, txt ->
                val id = listOf(R.id.btnPositivo1, R.id.btnPositivo2)[i]
                val btn = posContainer.findViewById<Button>(id)
                setupToggleButton(btn, txt)
                btn
            }

            val negButtons = negTexts.mapIndexed { i, txt ->
                val id = listOf(R.id.btnNegativo1, R.id.btnNegativo2)[i]
                val btn = negContainer.findViewById<Button>(id)
                setupToggleButton(btn, txt)
                btn
            }

            setupStars(title, starsContainer, posContainer, negContainer)

            return CategoriaViews(
                title,
                starsContainer,
                posContainer,
                negContainer,
                posButtons,
                negButtons
            )
        }

        categorias["audio"] = load(
            R.id.catAudio,
            "Montagem dos Áudios",
            listOf("Nitidez do áudio", "Cortes naturais"),
            listOf("Ruído ou eco", "Cortes bruscos")
        )

        categorias["fotos"] = load(
            R.id.catFotos,
            "Sincronização das Fotos",
            listOf("Foto no momento certo", "Narrativa visual boa"),
            listOf("Foto trocada", "Transições rápidas")
        )

        categorias["musica"] = load(
            R.id.catMusica,
            "Música de Fundo",
            listOf("Boa escolha", "Volume ideal"),
            listOf("Volume alto", "Não combinou")
        )

        categorias["fluidez"] = load(
            R.id.catFluidez,
            "Fluidez da Edição",
            listOf("Transições suaves", "Movimento agradável"),
            listOf("Transições bruscas", "Movimentos rápidos")
        )

        categorias["emocao"] = load(
            R.id.catEmocao,
            "Emoção Transmitida",
            listOf("História coerente", "Emoção transmitida"),
            listOf("Faltou emoção", "Narrativa confusa")
        )
    }


    private fun setupStars(
        key: String,
        container: LinearLayout,
        positive: LinearLayout,
        negative: LinearLayout
    ) {
        ratings[key] = 0

        for (i in 1..5) {
            val star = ImageView(requireContext())
            star.layoutParams = LinearLayout.LayoutParams(56, 56).apply {
                setMargins(4, 0, 4, 0)
            }
            star.setImageResource(R.drawable.ic_star_empty)

            star.setOnClickListener {
                ratings[key] = i
                updateStars(container, i)

                positive.visibility = if (i >= 4) View.VISIBLE else View.GONE
                negative.visibility = if (i <= 3) View.VISIBLE else View.GONE
            }

            container.addView(star)
        }
    }

    private fun updateStars(container: LinearLayout, rating: Int) {
        for (i in 0 until container.childCount) {
            val star = container.getChildAt(i) as ImageView
            star.setImageResource(
                if (i < rating) R.drawable.ic_star_full else R.drawable.ic_star_empty
            )
        }
    }

    private fun enviarFeedback() {
        val comentario = binding.etFeedback.text.toString()

        val json = mutableMapOf(
            "ratings" to ratings,
            "selected_feedback" to feedbackSelected,
            "comentario" to comentario
        )

        // Aqui futuramente:
        // send POST /feedback

        Toast.makeText(requireContext(), "Feedback enviado!", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        player?.release()
        _binding = null
        super.onDestroyView()
    }
}
