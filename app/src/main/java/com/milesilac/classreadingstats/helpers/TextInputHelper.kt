package com.milesilac.classreadingstats.helpers

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