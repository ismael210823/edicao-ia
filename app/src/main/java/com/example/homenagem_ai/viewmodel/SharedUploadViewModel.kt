package com.example.homenagem_ai.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel

class SharedUploadViewModel : ViewModel() {
    val fotos = mutableListOf<Uri>()
    val audios = mutableListOf<Uri>()
    val autoresAudios = mutableMapOf<Uri, String>()
    val videos = mutableListOf<Uri>()
    var musicas = mutableListOf<Uri>()
    var descricao: String = ""

}
