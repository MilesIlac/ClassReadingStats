package com.milesilac.classreadingstats.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.milesilac.classreadingstats.persistence.model.SectionEntity
import com.milesilac.classreadingstats.persistence.model.StudentEntity
import com.milesilac.classreadingstats.persistence.model.StudentRelationship
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDAO {

    @Query("SELECT * FROM sections")
    fun getAllSections(): Flow<List<SectionEntity>>

    @Query("SELECT * FROM students")
    fun getAllStudents(): Flow<List<StudentEntity>>

    @Transaction
    @Query("SELECT * FROM students WHERE student_room_id = :studentId")
    fun getStudent(studentId: Long): Flow<StudentRelationship>

    @Upsert
    suspend fun saveSection(section: SectionEntity): Long

    @Upsert
    suspend fun updateStudents(students: List<StudentEntity>)

    @Query("DELETE FROM students WHERE student_room_id IN (:studentIds)")
    suspend fun deleteStudents(studentIds: List<Int>)

    @Query("UPDATE students SET pre_gst_score = :preGSTScore WHERE student_room_id = :studentId")
    suspend fun updatePreGSTScore(studentId: Int, preGSTScore: Double)

    @Query("UPDATE students SET pre_or_total_words = :preORTotalNumberOfWords WHERE student_room_id = :studentId")
    suspend fun updatePreORTotalNumberOfWords(studentId: Int, preORTotalNumberOfWords: Double)

    @Query("UPDATE students SET pre_or_miscues = :preORNumberOfMiscues WHERE student_room_id = :studentId")
    suspend fun updatePreORNumberOfMiscues(studentId: Int, preORNumberOfMiscues: Double)

    @Query("UPDATE students SET pre_rc_percent = :preRCInputPercentage WHERE student_room_id = :studentId")
    suspend fun updatePreRCInputPercentage(studentId: Int, preRCInputPercentage: Double)
}