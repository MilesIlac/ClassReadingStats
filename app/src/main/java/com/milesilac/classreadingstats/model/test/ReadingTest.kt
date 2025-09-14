package com.milesilac.classreadingstats.model.test

import kotlinx.serialization.Serializable

@Serializable
data class ReadingTest(
    var oralReading: OralReading = initOralReading(),
    var readingComprehension: ReadingComprehension = initReadingComprehension()
)

fun emptyReadingTest() = ReadingTest()