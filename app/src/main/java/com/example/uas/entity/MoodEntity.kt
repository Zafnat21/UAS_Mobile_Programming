package com.example.uas.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mood_table")
data class MoodEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val mood: String,
    val iconResId: Int,      // untuk menyimpan resource id gambar emote
    val colorResId: Int      // untuk menyimpan resource id warna background
)
