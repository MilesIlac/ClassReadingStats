package com.milesilac.classreadingstats.repository

import com.milesilac.classreadingstats.model.ClassSheet
import com.milesilac.classreadingstats.model.Student
import kotlinx.coroutines.flow.Flow

interface StudentsRepository {
    fun getClassSheets(): Flow<List<ClassSheet>>

    suspend fun saveClassSheet(classSheet: ClassSheet)

    fun getStudent(studentId: Long): Flow<Student>

    suspend fun updateStudent(student: Student)

    suspend fun deleteSections(sectionsWithCount: List<Pair<Long, Boolean>>)

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