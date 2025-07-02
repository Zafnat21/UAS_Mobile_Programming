package com.example.uas.data

import androidx.lifecycle.LiveData
import com.example.uas.dao.NoteDao
import com.example.uas.entity.NoteEntity

class NoteRepository(private val noteDao: NoteDao) {

    val notes: LiveData<List<NoteEntity>> = noteDao.getAllNotes()

    fun getNoteById(id: Int): LiveData<NoteEntity> {
        return noteDao.getNoteById(id)
    }

    suspend fun insert(note: NoteEntity) {
        noteDao.insert(note)
    }

    suspend fun update(note: NoteEntity) {
        noteDao.update(note)
    }

    suspend fun delete(note: NoteEntity) {
        noteDao.delete(note)
    }
}
