package com.milesilac.classreadingstats.model

enum class ComprehensionLevel {
    FOUR, FIVE, SIX, SEVEN, EIGHT, PASSED, ERROR
}

fun String.toComprehensionLevel(): ComprehensionLevel {
    val value = this.uppercase()
    return when (value) {
        "4" -> ComprehensionLevel.FOUR
        "5" -> ComprehensionLevel.FIVE
        "6" -> ComprehensionLevel.SIX
        "7" -> ComprehensionLevel.SEVEN
        "8" -> ComprehensionLevel.EIGHT
        "PASSED" -> ComprehensionLevel.PASSED
        else -> ComprehensionLevel.ERROR
    }
}

fun ComprehensionLevel.toComprehensionLevelString(): String {
    val level = this
    return when (level) {
        ComprehensionLevel.FOUR -> "4"
        ComprehensionLevel.FIVE -> "5"
        ComprehensionLevel.SIX -> "6"
        ComprehensionLevel.SEVEN -> "7"
        ComprehensionLevel.EIGHT -> "8"
        ComprehensionLevel.PASSED -> "PASSED"
        else -> ""
    }
}

fun calculateComprehensionLevel(
    score: Int,
    gradeLevel: GradeLevel = GradeLevel.EIGHT,
): ComprehensionLevel {
    val comprehensionLevelString = when (score) {
        in 0..15 -> (gradeLevel.toGradeLevelInt() - 3).toString()
        in 16..27 -> (gradeLevel.toGradeLevelInt() - 2).toString()
        in 28..40 -> "PASSED"
        else -> ""
    }
    return comprehensionLevelString.toComprehensionLevel()
}