package com.milesilac.classreadingstats.model

import java.util.UUID

sealed class StudentList(open val listId: UUID) {
    data class Header(
        override var listId: UUID = UUID.randomUUID(),
        var sex: StudentSexOrient
    ) : StudentList(listId = listId)
    data class StudentDetails(
        override var listId: UUID = UUID.randomUUID(),
        var student: Student
    ) : StudentList(listId = listId)
}