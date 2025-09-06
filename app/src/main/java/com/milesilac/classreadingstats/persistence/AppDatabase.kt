package com.milesilac.classreadingstats.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.milesilac.classreadingstats.persistence.dao.RoomDAO
import com.milesilac.classreadingstats.persistence.model.SectionEntity
import com.milesilac.classreadingstats.persistence.model.StudentEntity

@Database(entities = [SectionEntity::class, StudentEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun roomDao(): RoomDAO

    companion object {
        @Volatile
        private var newInstance: AppDatabase? = null

        fun getInstance(appContext: Context): AppDatabase {
            // Double-checked locking
            return newInstance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    appContext,
                    AppDatabase::class.java,
                    "students_db"
                ).build()
                newInstance = instance
                instance
            }
        }
    }
}