package com.example.uas.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas.entity.NoteEntity
import com.example.uas.data.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    val notes: LiveData<List<NoteEntity>> = repository.notes

    fun getNoteById(id: Int): LiveData<NoteEntity> {
        return repository.getNoteById(id)
    }

    fun insertNote(note: NoteEntity) = viewModelScope.launch {
        repository.insert(note)
    }

    fun updateNote(note: NoteEntity) = viewModelScope.launch {
        repository.update(note)
    }

    fun deleteNote(note: NoteEntity) = viewModelScope.launch {
        repository.delete(note)
    }
}
