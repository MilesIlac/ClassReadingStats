package com.milesilac.classreadingstats.model

enum class GradeLevel(val grade: Int) {
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    ERROR(0)
}

fun Int.toGradeLevel(): GradeLevel {
    val value = this
    return when (value) {
        7 -> GradeLevel.SEVEN
        8 -> GradeLevel.EIGHT
        9 -> GradeLevel.NINE
        10 -> GradeLevel.TEN
        else -> GradeLevel.ERROR
    }
}

