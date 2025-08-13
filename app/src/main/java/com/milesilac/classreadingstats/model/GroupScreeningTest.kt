package com.milesilac.classreadingstats.model

import kotlinx.serialization.Serializable

@Serializable
class GroupScreeningTest(
    var score: Double,
) {
    val comprehensionLevel: ComprehensionLevel get() = calculateComprehensionLevel(
        score = score.toInt()
    )
}
