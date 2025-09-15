package com.milesilac.classreadingstats.model

import kotlinx.serialization.Serializable

@Serializable
data class ClassSheet(
    var classSection: ClassSection,
    var maleStudents: List<StudentList> = listOf(
        StudentList.Header(sex = StudentSexOrient.MALE),
    ),
    var femaleStudents: List<StudentList> = listOf(
        StudentList.Header(sex = StudentSexOrient.FEMALE)
    ),
)

fun initClassSheet() = ClassSheet(
    classSection = initClassSection()
)

fun List<ClassSheet>.hasStudents(): Boolean {
    for (sheet in this) {
        return (sheet.maleStudents + sheet.femaleStudents).any { it is StudentList.StudentDetails }
    }
    return false
}

fun ClassSheet.getAllStudentDetailsList() = (this.maleStudents.asSequence() + this.femaleStudents.asSequence())
    .filter { it is StudentList.StudentDetails }
    .toList() //usually for just checking list size, etc.

//@Suppress("NOTHING_TO_INLINE") //inlining prevents Compose Preview problems
fun ClassSheet.getStudentsPerSection() = (this.maleStudents.asSequence() + this.femaleStudents.asSequence())
    .mapNotNull { if (it is StudentList.StudentDetails) it.student else null }
    .toList() //StudentList details transformed to Student
