package com.example.uas 

data class MeditationItem(
    val title: String,
    val subtitle: String,
    val type: String,
    val backgroundImage: Int? = null,
    val isFullWidth: Boolean = false,
    val audioResource: Int // Tambahkan properti untuk menyimpan resource audio
)