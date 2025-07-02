package com.example.uas.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.uas.entity.MoodEntity

@Dao
interface MoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mood: MoodEntity)

    @Query("SELECT * FROM mood_table ORDER BY date DESC")
    fun getAllMoods(): LiveData<List<MoodEntity>>

    @Query("SELECT * FROM mood_table WHERE date = :date LIMIT 1")
    suspend fun getMoodByDate(date: String): MoodEntity?

    @Delete
    suspend fun delete(mood: MoodEntity)
}
