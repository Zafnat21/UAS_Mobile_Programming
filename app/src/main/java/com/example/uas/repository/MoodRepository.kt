package com.example.uas.repository

import androidx.lifecycle.LiveData
import com.example.uas.dao.MoodDao
import com.example.uas.entity.MoodEntity

class MoodRepository(private val moodDao: MoodDao) {

    val allMoods: LiveData<List<MoodEntity>> = moodDao.getAllMoods()

    suspend fun insert(mood: MoodEntity) {
        moodDao.insert(mood)
    }

    suspend fun getMoodByDate(date: String): MoodEntity? {
        return moodDao.getMoodByDate(date)
    }

    suspend fun delete(mood: MoodEntity) {
        moodDao.delete(mood)
    }
}
