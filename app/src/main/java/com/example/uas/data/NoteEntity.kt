package com.example.uas.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val content: String
)
