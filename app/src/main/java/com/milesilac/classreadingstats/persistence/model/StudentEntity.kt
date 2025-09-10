package com.milesilac.classreadingstats.persistence.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.milesilac.classreadingstats.model.ClassSection
import com.milesilac.classreadingstats.model.GroupScreeningTest
import com.milesilac.classreadingstats.model.OralReading
import com.milesilac.classreadingstats.model.ReadingComprehension
import com.milesilac.classreadingstats.model.ReadingTest
import com.milesilac.classreadingstats.model.Student
import com.milesilac.classreadingstats.model.StudentList
import com.milesilac.classreadingstats.model.StudentSexOrient

@Entity(tableName = "students")
data class StudentEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "student_room_id") val studentRoomId: Long = 0,
    @ColumnInfo(name = "section_room_id") val sectionRoomId: Long,
    @ColumnInfo(name = "order_id") val orderId: Double,
    @ColumnInfo(name = "student_name") val studentName: String,
    val sex: StudentSexOrient,
    @ColumnInfo(name = "pre_gst_score") val preGSTScore: Double,
    @ColumnInfo(name = "pre_or_total_words") val preORTotalNumberOfWords: Double,
    @ColumnInfo(name = "pre_or_miscues") val preORNumberOfMiscues: Double,
    @ColumnInfo(name = "pre_rc_percent") val preRCInputPercentage: Double,
    @ColumnInfo(name = "post_gst_score") val postGSTScore: Double?,
    @ColumnInfo(name = "post_or_total_words") val postORTotalNumberOfWords: Double?,
    @ColumnInfo(name = "post_or_miscues") val postORNumberOfMiscues: Double?,
    @ColumnInfo(name = "post_rc_percent") val postRCInputPercentage: Double?,
)

fun List<StudentEntity>.mapPartitionForStudentList(
    classSection: ClassSection,
): Pair<List<StudentList>, List<StudentList>> {
    val a = mutableListOf<StudentList>(StudentList.Header(sex = StudentSexOrient.MALE))
    val b = mutableListOf<StudentList>(StudentList.Header(sex = StudentSexOrient.FEMALE))
    for (student in this) {
        val mapped = StudentList.StudentDetails(
            student = student.mapStudentEntity(classSection = classSection)
        )
        when (mapped.student.sex) {
            StudentSexOrient.MALE -> a.add(mapped)
            StudentSexOrient.FEMALE -> b.add(mapped)
            else -> {}
        }
    }
    return Pair(a, b)
}

fun StudentEntity.mapStudentEntity(classSection: ClassSection) = Student(
    persistenceId = this.studentRoomId,
    orderId = this.orderId,
    name = this.studentName,
    section = classSection,
    sex = this.sex,
    preTest = ReadingTest(
        groupScreeningTest = GroupScreeningTest(
            score = this.preGSTScore
        ),
        oralReading = OralReading(
            totalNumberOfWordsInSelection = this.preORTotalNumberOfWords,
            numberOfMiscues = this.preORNumberOfMiscues
        ),
        readingComprehension = ReadingComprehension(
            inputPercentage = this.preRCInputPercentage
        )
    ),
    postTest = when {
        this.postGSTScore != null -> {
            ReadingTest(
                groupScreeningTest = GroupScreeningTest(
                    score = this.postGSTScore
                ),
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = this.postORTotalNumberOfWords ?: -1.0,
                    numberOfMiscues = this.postORNumberOfMiscues ?: -1.0
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = this.postRCInputPercentage ?: -1.0
                )
            )
        }
        else -> null
    }
)

fun List<StudentList>.mapStudentListToEntity(sectionId: Long): List<StudentEntity> {
    val studentEntities = mutableListOf<StudentEntity>()
    for (student in this) {
        if (student is StudentList.StudentDetails) {
            studentEntities.add(student.student.mapStudent(sectionId = sectionId))
        }
    }
    return studentEntities
}

fun Student.mapStudent(sectionId: Long) = StudentEntity(
    studentRoomId = this.persistenceId,
    sectionRoomId = sectionId,
    orderId = this.orderId,
    studentName = this.name,
    sex = this.sex,
    preGSTScore = this.preTest.groupScreeningTest.score,
    preORTotalNumberOfWords = this.preTest.oralReading?.totalNumberOfWordsInSelection ?: -1.0,
    preORNumberOfMiscues = this.preTest.oralReading?.numberOfMiscues ?: -1.0,
    preRCInputPercentage = this.preTest.readingComprehension?.inputPercentage ?: -1.0,
    postGSTScore = this.postTest?.groupScreeningTest?.score,
    postORTotalNumberOfWords = this.postTest?.oralReading?.totalNumberOfWordsInSelection,
    postORNumberOfMiscues = this.postTest?.oralReading?.numberOfMiscues,
    postRCInputPercentage = this.postTest?.readingComprehension?.inputPercentage
)