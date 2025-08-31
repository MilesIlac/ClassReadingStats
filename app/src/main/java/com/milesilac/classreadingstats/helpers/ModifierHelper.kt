package com.milesilac.classreadingstats.helpers

import androidx.compose.ui.Modifier

fun Modifier.checkIfAddOtherModifier(
    shouldAddOtherModifier: Boolean = false,
    otherModifier: Modifier = Modifier
): Modifier {
    return when {
        shouldAddOtherModifier -> {
            this.then(
                otherModifier
            )
        }
        else -> {
            this
        }
    }
}

fun Modifier.chooseOneModifier(
    chooseFirst: Boolean = false,
    firstModifier: Modifier = Modifier,
    secondModifier: Modifier = Modifier,
): Modifier {
    return this.then(
        when {
            chooseFirst -> firstModifier
            else -> secondModifier
        }
    )
}