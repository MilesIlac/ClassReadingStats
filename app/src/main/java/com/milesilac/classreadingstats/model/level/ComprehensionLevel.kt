package com.milesilac.classreadingstats.model.level

import kotlin.enums.enumEntries

enum class ComprehensionLevel(val level: String) {
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    PASSED("PASSED"),
    ERROR("")
}

fun String.toComprehensionLevel(): ComprehensionLevel {
    val value = this.uppercase()
    return enumEntries<ComprehensionLevel>().find {
        it.level.uppercase() == value
    } ?: ComprehensionLevel.ERROR
}

fun calculateComprehensionLevel(
    score: Int,
    gradeLevel: GradeLevel = GradeLevel.EIGHT,
): ComprehensionLevel {
    val comprehensionLevelString = when (score) {
        in 0..15 -> (gradeLevel.grade - 3).toString()
        in 16..27 -> (gradeLevel.grade - 2).toString()
        in 28..40 -> "PASSED"
        else -> ""
    }
    return comprehensionLevelString.toComprehensionLevel()
}