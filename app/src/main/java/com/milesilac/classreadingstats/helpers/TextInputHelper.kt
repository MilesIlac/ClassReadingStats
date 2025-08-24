package com.milesilac.classreadingstats.helpers

import java.util.Locale.getDefault

fun String.isPositiveInteger() = this.all { it.isDigit() }

fun String.removeExtraZeroes() = runCatching { this.toInt().toString() }.getOrElse { this }

fun String.isProperPositiveDecimal() = this.all { it.isDigit() || it == '.' } && this.count { it == '.' } <= 1

fun String.removeExtraZeroesForDecimal() = when {
    this.isPositiveInteger() -> this.removeExtraZeroes()
    else -> {
        val (numbers, decimals) = this.split(".", limit = 2)
        "${numbers.removeExtraZeroes()}.${decimals}"
    }
}

fun String.isAllLetters() = this.all { it.isLetter() || it.isWhitespace() }

fun String.capitalizeMaybe() = replaceFirstChar { if (it.isLowerCase()) it.titlecase(getDefault()) else it.toString() }