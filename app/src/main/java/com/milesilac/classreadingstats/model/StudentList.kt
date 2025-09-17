package com.milesilac.classreadingstats.model

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
sealed class StudentList {
    @Serializable
    data class Header(
        var sex: StudentSexOrient
    ) : StudentList()
    @Serializable
    data class StudentDetails(
        var listItemId: String = UUID.randomUUID().toString(),
        var student: Student
    ) : StudentList()
}

fun List<StudentList>.mapToStudents() = this
    .mapNotNull { if (it is StudentList.StudentDetails) it.student else null } //StudentList details transformed to Students

fun List<StudentList>.mapToStudentPersistenceIds() = this
    .mapNotNull { if (it is StudentList.StudentDetails) it.student.persistenceId else null } //StudentList details transformed to StudentPersistenceIds

fun List<StudentList>.addStudentWithOrderId(student: Student): List<StudentList> {
    val newList = mutableListOf<StudentList>(this[0])
    this.asSequence()
        .filter { it is StudentList.StudentDetails }
        .plus(StudentList.StudentDetails(student = student))
        .sortedBy { (it as StudentList.StudentDetails).student.name }
        .forEachIndexed { index, studentList ->
            (studentList as StudentList.StudentDetails).student.orderId = (index + 1).toDouble()
            newList.add(studentList)
        }
    return newList
}