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
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "student_room_id") val studentRoomId: Int = 0,
    @ColumnInfo(name = "section_room_id") val sectionRoomId: Int,
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
            student = Student(
                orderId = student.orderId,
                name = student.studentName,
                section = classSection,
                sex = student.sex,
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = student.preGSTScore
                    ),
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = student.preORTotalNumberOfWords,
                        numberOfMiscues = student.preORNumberOfMiscues
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = student.preRCInputPercentage
                    )
                ),
                postTest = when {
                    student.postGSTScore != null -> {
                        ReadingTest(
                            groupScreeningTest = GroupScreeningTest(
                                score = student.postGSTScore
                            ),
                            oralReading = OralReading(
                                totalNumberOfWordsInSelection = student.postORTotalNumberOfWords ?: -1.0,
                                numberOfMiscues = student.postORNumberOfMiscues ?: -1.0
                            ),
                            readingComprehension = ReadingComprehension(
                                inputPercentage = student.postRCInputPercentage ?: -1.0
                            )
                        )
                    }
                    else -> null
                }
            )
        )
        when (mapped.student.sex) {
            StudentSexOrient.MALE -> a.add(mapped)
            StudentSexOrient.FEMALE -> b.add(mapped)
            else -> {}
        }
    }
    return Pair(a, b)
}
