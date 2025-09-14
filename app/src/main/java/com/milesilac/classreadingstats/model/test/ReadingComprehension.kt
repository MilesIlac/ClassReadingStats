package com.milesilac.classreadingstats.model.test

import com.milesilac.classreadingstats.model.level.LearnerLevel
import com.milesilac.classreadingstats.model.level.calculateLearnerReadingComprehension
import kotlinx.serialization.Serializable

@Serializable
data class ReadingComprehension(
    var inputPercentage: Double,
) {
    val level: LearnerLevel get() = calculateLearnerReadingComprehension(percentage = inputPercentage)
}

fun initReadingComprehension() = ReadingComprehension(
    inputPercentage = -1.0,
)