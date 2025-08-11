package com.milesilac.classreadingstats.model

import kotlinx.serialization.Serializable

@Serializable
data class OralReading(
    var totalNumberOfWordsInSelection: Double,
    var numberOfMiscues: Double,
    var percentage: Double,
    var level: LearnerLevel
)

//remember to code input for total number of items

fun calculateOralReadingPercentage(
    numberOfMiscues: Double,
    totalNumberOfWordsInSelection: Double
): Double {
    return when {
        numberOfMiscues == -1.0 -> -0.01
        totalNumberOfWordsInSelection == -1.0 -> -0.01
        else -> ((totalNumberOfWordsInSelection - numberOfMiscues) / totalNumberOfWordsInSelection) * 100
    }
}
