package com.milesilac.classreadingstats.model

data class OralReading(
    var numberOfMiscues: Int,
    var percentage: Float,
    var level: LearnerLevel
)

//remember to code input for total number of items

fun calculateOralReadingPercentage(
    numberOfMiscues: Int,
    totalOfItems: Int
): Float {
    return ((numberOfMiscues / totalOfItems) * 100).toFloat()
}
