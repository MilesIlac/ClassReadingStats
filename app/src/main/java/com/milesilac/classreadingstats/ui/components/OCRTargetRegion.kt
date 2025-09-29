package com.milesilac.classreadingstats.ui.components

import android.graphics.RectF
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.milesilac.classreadingstats.helpers.dpToFloat
import com.milesilac.classreadingstats.helpers.pxToDp

@Composable
fun OCRTargetRegion(
    modifier: Modifier = Modifier,
    onBoxChange: (RectF) -> Unit = {}
) {
    val padding = 20.dp
    val paddingFloat = padding.dpToFloat
    Box(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        BoxWithConstraints(
            modifier = modifier
                .fillMaxSize()
//                .border(2.dp, Color.Red), //for debugging padding
        ) {

            val maxWidthPx = maxWidth.dpToFloat
            val maxHeightPx = maxHeight.dpToFloat
            var boxOffset by remember { mutableStateOf(Offset(0F, maxHeightPx/2)) }
            var boxWidthPx by remember { mutableFloatStateOf((maxWidthPx - boxOffset.x)) }
            var boxHeightPx by remember { mutableFloatStateOf(50F) }
            var handleWidthHeightPx by remember { mutableFloatStateOf(50F) }

            LaunchedEffect(Unit) {
                onBoxChange(
                    RectF(
                        boxOffset.x + paddingFloat,
                        boxOffset.y + paddingFloat,
                        (boxOffset.x + boxWidthPx + paddingFloat),
                        (boxOffset.y + boxHeightPx + paddingFloat)
                    ) //paddingFloat ensures correctness
                )
            }

            // Draggable main box
            Box(
                modifier = Modifier
                    .offset { IntOffset(boxOffset.x.toInt(), boxOffset.y.toInt()) }
                    .width(boxWidthPx.pxToDp)
                    .height(boxHeightPx.pxToDp)
                    .border(2.dp, Color.Green)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            val newOffsetX = (boxOffset.x + dragAmount.x)
                                .coerceIn(0f, (maxWidthPx - boxWidthPx))
                            val newOffsetY = (boxOffset.y + dragAmount.y)
                                .coerceIn(0f, (maxHeightPx - boxHeightPx))
                            boxOffset = Offset(newOffsetX, newOffsetY)

                            onBoxChange(
                                RectF(
                                    boxOffset.x + paddingFloat,
                                    boxOffset.y + paddingFloat,
                                    (boxOffset.x + boxWidthPx + paddingFloat),
                                    (boxOffset.y + boxHeightPx + paddingFloat)
                                ) //paddingFloat ensures correctness
                            )
                        }
                    }
            )

            // Resizable handle (bottom-right corner)
            val handleOffsetX = (boxOffset.x + (boxWidthPx - ((handleWidthHeightPx / 2) + 2.dp.value)))
            val handleOffsetY = (boxOffset.y + (boxHeightPx - ((handleWidthHeightPx / 2) + 2.dp.value)))
            Box(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            handleOffsetX.toInt(),
                            handleOffsetY.toInt(),
                        )
                    }
                    .width(handleWidthHeightPx.pxToDp)
                    .height(handleWidthHeightPx.pxToDp)
                    .background(Color.Green, shape = CircleShape)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            val newWidth = (boxWidthPx + dragAmount.x)
                                .coerceIn(300f, maxWidthPx - boxOffset.x)
                            boxWidthPx = newWidth
                            val newHeight = (boxHeightPx + dragAmount.y)
                                .coerceIn(50f, maxHeightPx - boxOffset.y)
                            boxHeightPx = newHeight

                            onBoxChange(
                                RectF(
                                    boxOffset.x + paddingFloat,
                                    boxOffset.y + paddingFloat,
                                    (boxOffset.x + boxWidthPx + paddingFloat),
                                    (boxOffset.y + boxHeightPx + paddingFloat)
                                ) //paddingFloat ensures correctness
                            )
                        }
                    }
            )
        }
    }
}

@Preview
@Composable
fun OCRTargetRegionPreview() {
    OCRTargetRegion()
}