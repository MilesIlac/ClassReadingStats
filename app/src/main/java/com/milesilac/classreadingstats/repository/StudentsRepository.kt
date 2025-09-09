package com.milesilac.classreadingstats.repository

import com.milesilac.classreadingstats.model.ClassSheet
import kotlinx.coroutines.flow.Flow

interface StudentsRepository {
    fun getClassSheets(): Flow<List<ClassSheet>>

    companion object {
        @Volatile
        private var newInstance: StudentsRepository? = null

        fun getInstance(): StudentsRepository {
            // Double-checked locking
            return newInstance ?: synchronized(this) {
                val instance = StudentsRepositoryImpl()
                newInstance = instance
                instance
            }
        }
    }
}