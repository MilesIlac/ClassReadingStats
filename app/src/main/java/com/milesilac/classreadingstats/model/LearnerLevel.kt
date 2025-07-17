package com.milesilac.classreadingstats.model

enum class LearnerLevel {
    FRUSTRATION,
    INDEPENDENT,
    INSTRUCTIONAL,
    ERROR
}

fun String.toLearnerLevel(): LearnerLevel {
    val value = this.uppercase()
    return when (value) {
        "FRUSTRATION" -> LearnerLevel.FRUSTRATION
        "INDEPENDENT" -> LearnerLevel.INDEPENDENT
        "INSTRUCTIONAL" -> LearnerLevel.INSTRUCTIONAL
        else -> LearnerLevel.ERROR
    }
}

fun LearnerLevel.toLearnerLevelString(): String {
    val level = this
    return when (level) {
        LearnerLevel.FRUSTRATION -> "Frustration"
        LearnerLevel.INDEPENDENT -> "Independent"
        LearnerLevel.INSTRUCTIONAL -> "Instructional"
        else -> ""
    }
}

fun calculateLearnerOralReading(
    percentage: Float
): LearnerLevel {
    return when (percentage) {
        in 97.0..100.0 -> LearnerLevel.INDEPENDENT
        in 90.0..96.9 -> LearnerLevel.INSTRUCTIONAL
        in 0.0..89.9 -> LearnerLevel.FRUSTRATION
        else -> LearnerLevel.ERROR
    }
}

fun calculateLearnerReadingComprehension(
    percentage: Float
): LearnerLevel {
    return when (percentage) {
        in 80.0..100.0 -> LearnerLevel.INDEPENDENT
        in 59.0..79.9 -> LearnerLevel.INSTRUCTIONAL
        in 0.0..58.9 -> LearnerLevel.FRUSTRATION
        else -> LearnerLevel.ERROR
    }
}