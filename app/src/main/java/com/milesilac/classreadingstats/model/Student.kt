package com.milesilac.classreadingstats.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.enums.enumEntries

@Serializable
data class Student(
    var orderId: Double = 0.0,
    var name: String,
    var section: ClassSection,
    var sex: StudentSexOrient,
    var preTest: ReadingTest,
    var postTest: ReadingTest? = null
) {
    fun toJsonString() = Json.encodeToString(this)
}

fun emptyStudent(
    name: String = "",
    section: ClassSection = initClassSection(),
    sex: StudentSexOrient = StudentSexOrient.ERROR,
    preTest: ReadingTest = emptyReadingTest()
) = Student(
    name = name,
    section = section,
    sex = sex,
    preTest = preTest
)

// in the scenario of reading values from excel file,
// possibly calculate isGradingPassage by checking if all succeeding cells are empty

enum class StudentSexOrient(
    val sex: String,
    val wordedLabel: String
) {
    MALE("M", "Male"),
    FEMALE("F", "Female"),
    ERROR("", "")
}

fun String.sexConvertCharToEnum(): StudentSexOrient {
    val value = this.uppercase()
    return enumEntries<StudentSexOrient>().find {
        it.sex == value
    } ?: StudentSexOrient.ERROR
}

fun String.sexConvertWordsToEnum(): StudentSexOrient {
    val value = this.uppercase()
    return enumEntries<StudentSexOrient>().find {
        it.wordedLabel.uppercase() == value
    } ?: StudentSexOrient.ERROR
}
