package com.milesilac.classreadingstats.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.milesilac.classreadingstats.model.StudentSexOrient
import com.milesilac.classreadingstats.persistence.model.SectionEntity
import com.milesilac.classreadingstats.persistence.model.StudentEntity
import com.milesilac.classreadingstats.persistence.model.StudentRelationship
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDAO {

    @Query("SELECT * FROM sections")
    fun getAllSections(): Flow<List<SectionEntity>>

    @Query("SELECT * FROM students ORDER BY student_name ASC")
    fun getAllStudents(): Flow<List<StudentEntity>>

    @Transaction
    @Query("SELECT * FROM students WHERE student_room_id = :studentId")
    fun getStudent(studentId: Long): Flow<StudentRelationship>

    @Upsert
    suspend fun updateSection(section: SectionEntity): Long

    @Upsert
    suspend fun updateStudents(students: List<StudentEntity>)

    @Query("SELECT * FROM students WHERE section_room_id = :sectionId AND sex = :sex")
    suspend fun getSameListStudents(sectionId: Long, sex: StudentSexOrient): List<StudentEntity>

    @Query("DELETE FROM sections WHERE section_room_id = :sectionId")
    suspend fun deleteSection(sectionId: Long)

    @Query("DELETE FROM students WHERE section_room_id = :sectionId")
    suspend fun deleteSectionStudents(sectionId: Long)

    @Query("DELETE FROM students WHERE student_room_id IN (:studentIds)")
    suspend fun deleteStudentsByRoomId(studentIds: List<Long>)

//    @Query("UPDATE students SET gst_score = :gstScore WHERE student_room_id = :studentId")
//    suspend fun updateGSTScore(studentId: Int, gstScore: Double)
//
//    @Query("UPDATE students SET pre_or_total_words = :preORTotalNumberOfWords WHERE student_room_id = :studentId")
//    suspend fun updatePreORTotalNumberOfWords(studentId: Int, preORTotalNumberOfWords: Double)
//
//    @Query("UPDATE students SET pre_or_miscues = :preORNumberOfMiscues WHERE student_room_id = :studentId")
//    suspend fun updatePreORNumberOfMiscues(studentId: Int, preORNumberOfMiscues: Double)
//
//    @Query("UPDATE students SET pre_rc_percent = :preRCInputPercentage WHERE student_room_id = :studentId")
//    suspend fun updatePreRCInputPercentage(studentId: Int, preRCInputPercentage: Double)
}