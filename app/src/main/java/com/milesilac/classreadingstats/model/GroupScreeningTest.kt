package com.milesilac.classreadingstats.model

import kotlinx.serialization.Serializable

@Serializable
data class GroupScreeningTest(
    var score: Double,
    var comprehensionLevel: ComprehensionLevel
)
