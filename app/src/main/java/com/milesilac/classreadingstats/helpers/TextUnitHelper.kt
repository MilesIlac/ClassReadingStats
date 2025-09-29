package com.milesilac.classreadingstats.helpers

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

val TextUnit.nonScaledSp
    @Composable
    get() = (this.value / LocalDensity.current.fontScale).sp

val Int.pxToDp
    @Composable
    get() = with(LocalDensity.current) { this@pxToDp.toDp() }

val Float.pxToDp
    @Composable
    get() = with(LocalDensity.current) { this@pxToDp.toDp() }

val Dp.dpToFloat
    @Composable
    get() = with(LocalDensity.current) { this@dpToFloat.toPx() }