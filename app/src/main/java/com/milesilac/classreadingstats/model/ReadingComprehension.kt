package com.milesilac.classreadingstats.model

import kotlinx.serialization.Serializable

@Serializable
data class ReadingComprehension(
    var inputPercentage: Double,
) {
    val level: LearnerLevel get() = calculateLearnerReadingComprehension(percentage = inputPercentage)
}