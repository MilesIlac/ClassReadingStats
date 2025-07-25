package com.milesilac.classreadingstats.model

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Student(
    var listId: String = UUID.randomUUID().toString(),
    var orderId: Int = 0,
    var name: String,
    var section: String,
    var sex: String,
    var groupScreeningTest: GroupScreeningTest,
    var isGradingPassage: Boolean = true,
    var oralReading: OralReading? = null,
    var readingComprehension: ReadingComprehension? = null
)

fun Student.shouldGradePassage() = this.groupScreeningTest.comprehensionLevel != ComprehensionLevel.PASSED

// in the scenario of reading values from excel file,
// possibly calculate isGradingPassage by checking if all succeeding cells are empty

enum class StudentSexOrient {
    MALE, FEMALE, ERROR
}

fun String.toStudentSexOrient(): StudentSexOrient {
    val value = this.uppercase()
    return when (value) {
        "M" -> StudentSexOrient.MALE
        "F" -> StudentSexOrient.FEMALE
        else -> StudentSexOrient.ERROR
    }
}

fun StudentSexOrient.toStudentSexOrientString(): String {
    val value = this
    return when (value) {
        StudentSexOrient.MALE -> "M"
        StudentSexOrient.FEMALE -> "F"
        StudentSexOrient.ERROR -> ""
    }
}
