package com.example.uas

import android.app.Application
import com.example.uas.dao.AppDatabase
import com.example.uas.data.NoteRepository
import com.example.uas.repository.MoodRepository

class NoteApplication : Application() {

    // Inisialisasi database Room
    val database by lazy { AppDatabase.getDatabase(this) }

    // Repository untuk Note (catatan)
    val noteRepository by lazy { NoteRepository(database.noteDao()) }

    // Repository untuk Mood (riwayat mood)
    val moodRepository by lazy { MoodRepository(database.moodDao()) }
}
