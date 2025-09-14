package com.milesilac.classreadingstats.model

import kotlinx.serialization.Serializable
import kotlin.enums.enumEntries

@Serializable
data class Student(
    var persistenceId: Long = 0,
    var orderId: Double = 0.0,
    var name: String,
    var section: ClassSection,
    var sex: StudentSexOrient,
    var hasPostTest: Boolean,
    var gst: GroupScreeningTest,
    var preTest: ReadingTest,
    var postTest: ReadingTest? = null
)

fun emptyStudent(
    name: String = "",
    section: ClassSection = initClassSection(),
    sex: StudentSexOrient = StudentSexOrient.ERROR,
    hasPostTest: Boolean = false,
    gst: GroupScreeningTest = initGST(),
    preTest: ReadingTest = emptyReadingTest()
) = Student(
    name = name,
    section = section,
    sex = sex,
    hasPostTest = hasPostTest,
    gst = gst,
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
