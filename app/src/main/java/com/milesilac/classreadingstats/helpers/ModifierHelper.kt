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