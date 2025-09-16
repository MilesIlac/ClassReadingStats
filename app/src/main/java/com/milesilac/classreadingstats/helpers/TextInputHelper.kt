package com.milesilac.classreadingstats.helpers

import java.util.Locale.getDefault

fun String.repairInput(currentValue: String, errorValue: String): String {
    return when {
        currentValue == errorValue -> {
            var newString = this
            errorValue.forEach { character ->
                val removeIndex = newString.indexOfFirst { it == character }
                newString = runCatching { newString.removeRange(removeIndex , removeIndex + 1) }.getOrElse { newString }
            }
            newString
        }
        else -> this
    }
}

fun String.isPositiveInteger() = this.all { it.isDigit() }

fun String.removeExtraZeroes() = runCatching { this.toInt().toString() }.getOrElse { this }

inline fun String.inputFullCheckForIntString(
    currentValue: String,
    errorValue: String,
    returnValue: (String) -> Unit
) {
    println("classInits textInput currentValue $currentValue")
    println("classInits textInput newValue $this")
    if (currentValue == errorValue) returnValue("")
    val repaired = this.repairInput(currentValue = currentValue, errorValue = errorValue)
    println("classInits textInput repaired $repaired")
    if (repaired.isPositiveInteger()) {
        println("classInits textInput isPositiveInteger")
        println("classInits textInput removeExtraZeroes ${repaired.removeExtraZeroes()}")
        returnValue(repaired.removeExtraZeroes())
    }
}

fun String.isProperPositiveDecimal() = this.all { it.isDigit() || it == '.' } && this.count { it == '.' } <= 1

fun String.removeExtraZeroesForDecimal() = when {
    this.isPositiveInteger() -> this.removeExtraZeroes()
    else -> {
        val (numbers, decimals) = this.split(".", limit = 2)
        "${numbers.removeExtraZeroes()}.${decimals}"
    }
}

inline fun String.inputFullCheckForDecimalString(
    currentValue: String,
    errorValue: String,
    returnValue: (String) -> Unit
) {
    println("classInits textInput currentValue $currentValue")
    println("classInits textInput newValue $this")
    if (currentValue == errorValue) returnValue("")
    val repaired = this.repairInput(currentValue = currentValue, errorValue = errorValue)
    println("classInits textInput repaired $repaired")
    if (repaired.isProperPositiveDecimal()) {
        println("classInits textInput isProperPositiveDecimal")
        println("classInits textInput removeExtraZeroesForDecimal ${repaired.removeExtraZeroesForDecimal()}")
        returnValue(repaired.removeExtraZeroesForDecimal())
    }
}

fun String.isAllLetters() = this.all { it.isLetter() || it.isWhitespace() }

fun String.inputFullCheckForStudentName(): String {
    return when {
        this.trim().all { it.isLetter().not() } -> ""
        else -> this.trim()
    }
}

fun String.capitalizeMaybeWithTrim() = trim().replaceFirstChar { if (it.isLowerCase()) it.titlecase(getDefault()) else it.toString() }