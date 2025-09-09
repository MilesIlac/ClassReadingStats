package com.milesilac.classreadingstats.model

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
        return sheet.maleStudents.any { it is StudentList.StudentDetails }
                || sheet.femaleStudents.any { it is StudentList.StudentDetails }
    }
    return false
}
