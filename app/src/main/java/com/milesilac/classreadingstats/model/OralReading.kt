package com.milesilac.classreadingstats.model

import kotlinx.serialization.Serializable

@Serializable
data class OralReading(
    var totalNumberOfWordsInSelection: Int,
    var numberOfMiscues: Int,
    var percentage: Float,
    var level: LearnerLevel
)

//remember to code input for total number of items

fun calculateOralReadingPercentage(
    numberOfMiscues: Int,
    totalNumberOfWordsInSelection: Int
): Float {
    return (((totalNumberOfWordsInSelection - numberOfMiscues) / totalNumberOfWordsInSelection) * 100).toFloat()
}
