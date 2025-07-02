package com.example.uas.appdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.uas.dao.MoodDao
import com.example.uas.entity.MoodEntity
import com.example.uas.dao.NoteDao
import com.example.uas.entity.NoteEntity

@Database(entities = [NoteEntity::class, MoodEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun moodDao(): MoodDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "db"
                )
                    .fallbackToDestructiveMigration() // Hapus data saat migrasi versi (pastikan sesuai kebutuhanmu)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
