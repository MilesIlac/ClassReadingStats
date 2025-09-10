package com.milesilac.classreadingstats.model

import kotlin.enums.enumEntries

enum class LearnerLevel(val level: String) {
    FRUSTRATION("Frustration"),
    INDEPENDENT("Independent"),
    INSTRUCTIONAL("Instructional"),
    ERROR("")
}

fun String.toLearnerLevel(): LearnerLevel {
    val value = this.uppercase()
    return enumEntries<LearnerLevel>().find {
        it.level.uppercase() == value
    } ?: LearnerLevel.ERROR
}

fun calculateLearnerOralReading(
    percentage: Double
): LearnerLevel {
    return when (percentage) {
        in 97.0..100.0 -> LearnerLevel.INDEPENDENT
        in 90.0..96.9 -> LearnerLevel.INSTRUCTIONAL
        in 0.0..89.9 -> LearnerLevel.FRUSTRATION
        else -> LearnerLevel.ERROR
    }
}

fun calculateLearnerReadingComprehension(
    percentage: Double
): LearnerLevel {
    return when (percentage) {
        in 80.0..100.0 -> LearnerLevel.INDEPENDENT
        in 59.0..79.9 -> LearnerLevel.INSTRUCTIONAL
        in 0.0..58.9 -> LearnerLevel.FRUSTRATION
        else -> LearnerLevel.ERROR
    }
}

fun calculateLearnerOverallReadingProfile(
    orLevel: LearnerLevel,
    rcLevel: LearnerLevel
): LearnerLevel {
    return when {
        orLevel == LearnerLevel.INDEPENDENT && rcLevel == LearnerLevel.INDEPENDENT -> LearnerLevel.INDEPENDENT
        orLevel == LearnerLevel.FRUSTRATION || rcLevel == LearnerLevel.FRUSTRATION -> LearnerLevel.FRUSTRATION
        orLevel == LearnerLevel.INSTRUCTIONAL || rcLevel == LearnerLevel.INSTRUCTIONAL -> LearnerLevel.INSTRUCTIONAL
        else -> LearnerLevel.ERROR
    }
}