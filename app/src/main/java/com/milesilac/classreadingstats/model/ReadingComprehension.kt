package com.milesilac.classreadingstats.model

import kotlinx.serialization.Serializable

@Serializable
data class ReadingComprehension(
    var inputPercentage: Float,
    var level: LearnerLevel
)