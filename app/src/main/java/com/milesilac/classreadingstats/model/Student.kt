package com.milesilac.classreadingstats.model

data class Student(
    var sheetRowNumber: Int = 0,
    var name: String,
    var section: String,
    var sex: String,
    var groupScreeningTest: GroupScreeningTest,
    var isGradingPassage: Boolean = false,
    var oralReading: OralReading? = null,
    var readingComprehension: ReadingComprehension? = null
)

fun Student.shouldGradePassage() = this.groupScreeningTest.comprehensionLevel != ComprehensionLevel.PASSED

// in the scenario of reading values from excel file,
// possibly calculate isGradingPassage by checking if all succeeding cells are empty
