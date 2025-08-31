package com.milesilac.classreadingstats.helpers

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape

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

fun Modifier.advancedBackgroundColorChooser(
    doBrushCondition: Boolean = false,
    brush: Brush,
    brushShape: Shape = RectangleShape,
    brushAlpha: Float = 1F,
    color: Color,
    colorShape: Shape = RectangleShape,
): Modifier {
    return when {
        doBrushCondition -> {
            this.then(
                Modifier.background(
                    brush = brush,
                    shape = brushShape,
                    alpha = brushAlpha
                )
            )
        }
        else -> {
            this.then(
                Modifier.background(
                    color = color,
                    shape = colorShape
                )
            )
        }
    }
}