package com.milesilac.classreadingstats.model

import kotlinx.serialization.Serializable

@Serializable
class OralReading(
    var totalNumberOfWordsInSelection: Double,
    var numberOfMiscues: Double,
) {
    val percentage: Double get() = calculateOralReadingPercentage(
        numberOfMiscues = numberOfMiscues,
        totalNumberOfWordsInSelection = totalNumberOfWordsInSelection
    )
    val level: LearnerLevel get() = calculateLearnerOralReading(percentage = percentage)
}

fun calculateOralReadingPercentage(
    totalNumberOfWordsInSelection: Double,
    numberOfMiscues: Double,
): Double {
    return when {
        totalNumberOfWordsInSelection == -1.0 -> -0.01
        numberOfMiscues == -1.0 -> -0.01
        else -> ((totalNumberOfWordsInSelection - numberOfMiscues) / totalNumberOfWordsInSelection) * 100
    }
}
