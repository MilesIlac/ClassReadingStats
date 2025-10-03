package com.milesilac.classreadingstats.repository

import com.milesilac.classreadingstats.model.ClassSheet
import com.milesilac.classreadingstats.model.Student
import com.milesilac.classreadingstats.model.StudentToDeleteBundle
import kotlinx.coroutines.flow.Flow

interface StudentsRepository {
    fun getClassSheets(): Flow<List<ClassSheet>>

    suspend fun saveClassSheets(classSheets: List<ClassSheet>)

    fun getStudent(studentId: Long): Flow<Student>

    fun getLookupPairs(): Flow<List<StudentWithScannedGrade>>

    suspend fun lookupStudents(
        sectionPersistenceId: Long,
        studentNamePairs: Set<Pair<String,String>>
    )

    suspend fun updateStudent(student: Student, hasSortOperation: Boolean)

    suspend fun deleteSections(sectionsWithCount: List<Pair<Long, Boolean>>)

    suspend fun deleteStudentsByRoomId(studentBundlesToDelete: List<StudentToDeleteBundle>)

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