package com.milesilac.classreadingstats.model

import kotlinx.serialization.Serializable

@Serializable
data class Student(
    var orderId: Double = 0.0,
    var name: String,
    var section: String,
    var sex: StudentSexOrient,
    var preTest: ReadingTest,
    var postTest: ReadingTest? = null
)

// in the scenario of reading values from excel file,
// possibly calculate isGradingPassage by checking if all succeeding cells are empty

enum class StudentSexOrient {
    MALE, FEMALE, ERROR
}

fun String.sexConvertCharToEnum(): StudentSexOrient {
    val value = this.uppercase()
    return when (value) {
        "M" -> StudentSexOrient.MALE
        "F" -> StudentSexOrient.FEMALE
        else -> StudentSexOrient.ERROR
    }
}

fun StudentSexOrient.sexConvertEnumToChar(): String {
    val value = this
    return when (value) {
        StudentSexOrient.MALE -> "M"
        StudentSexOrient.FEMALE -> "F"
        StudentSexOrient.ERROR -> ""
    }
}

fun String.sexConvertWordsToEnum(): StudentSexOrient {
    val value = this.uppercase()
    return when (value) {
        "MALE" -> StudentSexOrient.MALE
        "FEMALE" -> StudentSexOrient.FEMALE
        else -> StudentSexOrient.ERROR
    }
}

fun StudentSexOrient.sexConvertEnumToWords(): String {
    val value = this
    return when (value) {
        StudentSexOrient.MALE -> "Male"
        StudentSexOrient.FEMALE -> "Female"
        StudentSexOrient.ERROR -> ""
    }
}

fun String.sexConvertCharToWords(): String {
    val value = this.uppercase()
    return when (value) {
        "M" -> "Male"
        "F" -> "Female"
        else -> ""
    }
}

fun String.sexConvertWordsToChar(): String {
    val value = this.uppercase()
    return when (value) {
        "MALE" -> "M"
        "FEMALE" -> "F"
        else -> ""
    }
}
