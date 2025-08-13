package com.milesilac.classreadingstats.model

import kotlinx.serialization.Serializable

@Serializable
data class ReadingTest(
    var groupScreeningTest: GroupScreeningTest,
    var oralReading: OralReading? = null,
    var readingComprehension: ReadingComprehension? = null
) {
    fun shouldGradePassage() = groupScreeningTest.comprehensionLevel != ComprehensionLevel.PASSED
}

fun emptyReadingTest() = ReadingTest(
    groupScreeningTest = GroupScreeningTest(
        score = 0.0
    )
)