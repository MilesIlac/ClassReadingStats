package com.milesilac.classreadingstats.model

sealed class StudentList {
    data class Header(var sex: StudentSexOrient): StudentList()
    data class StudentDetails(var student: Student) : StudentList()
}