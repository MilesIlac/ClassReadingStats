package com.milesilac.classreadingstats.model

import kotlinx.serialization.Serializable

@Serializable
data class Student(
    var orderId: Double = 0.0,
    var name: String,
    var section: String,
    var sex: String,
    var preTest: ReadingTest,
    var postTest: ReadingTest? = null
)

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
