package com.milesilac.classreadingstats.model

data class StudentToDeleteBundle(
    val classSection: ClassSection,
    val sexOrient: StudentSexOrient,
    val studentPersistenceIds: List<Long>
)

fun List<Student>.prepareStudentsToDelete(): List<StudentToDeleteBundle> {
    val newList = mutableListOf<StudentToDeleteBundle>()
    this.groupBy { it.section }.forEach { (section, students) ->
        students.mapPartitionForStudentDelete().forEach { mapped ->
            newList.add(
                StudentToDeleteBundle(
                    classSection = section,
                    sexOrient = mapped.first,
                    studentPersistenceIds = mapped.second
                )
            )
        }
    }
    return newList
}

fun List<Student>.mapPartitionForStudentDelete(): List<Pair<StudentSexOrient, List<Long>>> {
    val a = mutableListOf<Long>()
    val b = mutableListOf<Long>()
    for (student in this) {
        when (student.sex) {
            StudentSexOrient.MALE -> a.add(student.persistenceId)
            StudentSexOrient.FEMALE -> b.add(student.persistenceId)
            else -> {}
        }
    }
    return listOf(
        Pair(StudentSexOrient.MALE, a),
        Pair(StudentSexOrient.FEMALE, b)
    )
}