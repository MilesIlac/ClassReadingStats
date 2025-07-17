package com.milesilac.classreadingstats.model

enum class GradeLevel {
    SEVEN, EIGHT, NINE, TEN, ERROR
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

fun GradeLevel.toGradeLevelInt(): Int {
    val level = this
    return when (level) {
        GradeLevel.SEVEN -> 7
        GradeLevel.EIGHT -> 8
        GradeLevel.NINE -> 9
        GradeLevel.TEN -> 10
        else -> 0
    }
}

