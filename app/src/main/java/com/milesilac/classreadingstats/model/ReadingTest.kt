package com.milesilac.classreadingstats.model

import kotlinx.serialization.Serializable

@Serializable
data class ReadingTest(
    var groupScreeningTest: GroupScreeningTest,
    var isGradingPassage: Boolean = true,
    var oralReading: OralReading? = null,
    var readingComprehension: ReadingComprehension? = null
)

fun ReadingTest.shouldGradePassage() = this.groupScreeningTest.comprehensionLevel != ComprehensionLevel.PASSED