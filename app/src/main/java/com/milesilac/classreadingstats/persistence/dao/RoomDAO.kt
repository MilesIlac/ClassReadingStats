package com.milesilac.classreadingstats.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.milesilac.classreadingstats.persistence.model.ClassSheetRelationship
import com.milesilac.classreadingstats.persistence.model.StudentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDAO {

    @Query("SELECT * FROM sections")
    fun getClassSheets(): Flow<List<ClassSheetRelationship>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: StudentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudents(students: List<StudentEntity>)

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