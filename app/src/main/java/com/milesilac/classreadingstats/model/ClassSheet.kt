package com.milesilac.classreadingstats.model

data class ClassSheet(
    var classSection: ClassSection,
    var students: List<StudentList> = listOf(
        StudentList.Header(sex = StudentSexOrient.MALE),
        StudentList.Header(sex = StudentSexOrient.FEMALE)
    ),
)
