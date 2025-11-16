package com.example.homenagem_ai.model

import android.net.Uri

data class TimelineSection(
    val title: String,
    val items: List<Uri>,
    val type: MidiaType
)

enum class MidiaType {
    FOTO,
    VIDEO,
    AUDIO,
    MUSICA
}
