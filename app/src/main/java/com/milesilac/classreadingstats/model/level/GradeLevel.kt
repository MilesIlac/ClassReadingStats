package com.milesilac.classreadingstats.model.level

import kotlin.enums.enumEntries

enum class GradeLevel(val grade: Int) {
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    ERROR(0)
}

fun Int.toGradeLevel(): GradeLevel {
    val value = this
    return enumEntries<GradeLevel>().find {
        it.grade == value
    } ?: GradeLevel.ERROR
}

