package com.example.uas

import android.app.Application
import com.example.uas.data.AppDatabase
import com.example.uas.data.NoteRepository

class NoteApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { NoteRepository(database.noteDao()) }
}
