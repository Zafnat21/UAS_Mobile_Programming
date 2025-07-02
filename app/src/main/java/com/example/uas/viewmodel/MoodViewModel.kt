package com.example.uas.viewmodel

import androidx.lifecycle.*
import com.example.uas.entity.MoodEntity
import com.example.uas.repository.MoodRepository
import kotlinx.coroutines.launch

class MoodViewModel(private val repository: MoodRepository) : ViewModel() {

    val allMoods: LiveData<List<MoodEntity>> = repository.allMoods

    fun insert(mood: MoodEntity) = viewModelScope.launch {
        repository.insert(mood)
    }

    fun delete(mood: MoodEntity) = viewModelScope.launch {
        repository.delete(mood)
    }
}
