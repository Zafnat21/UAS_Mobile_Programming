package com.example.uas.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.uas.entity.MoodEntity
import com.example.uas.entity.NoteEntity
import com.example.uas.entity.UserEntity

@Database(entities = [NoteEntity::class, MoodEntity::class, UserEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun moodDao(): MoodDao
    abstract fun userDao(): UserDao  // ✅ Tambahkan ini

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "db"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
