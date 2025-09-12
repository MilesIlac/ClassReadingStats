package com.milesilac.classreadingstats.model

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
sealed class StudentList() {
    @Serializable
    data class Header(
        var listItemId: String = UUID.randomUUID().toString(),
        var sex: StudentSexOrient
    ) : StudentList()
    @Serializable
    data class StudentDetails(
        var listItemId: String = UUID.randomUUID().toString(),
        var student: Student
    ) : StudentList()
}