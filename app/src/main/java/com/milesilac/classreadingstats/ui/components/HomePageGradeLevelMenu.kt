package com.milesilac.classreadingstats.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.milesilac.classreadingstats.helpers.checkIfAddOtherModifier
import com.milesilac.classreadingstats.helpers.pxToDp
import com.milesilac.classreadingstats.model.level.GradeLevel
import com.milesilac.classreadingstats.ui.theme.ProjectColors
import kotlin.enums.enumEntries

@Composable
fun HomePageGradeLevelMenu(
    isExpanded: Boolean = false,
    buttonText: String = "-",
    isButtonEnabled: Boolean = false,
    nonEmptyGrades: Set<GradeLevel> = setOf(),
    currentGradeLevel: GradeLevel = GradeLevel.EIGHT,
    onMenuVisibility: () -> Unit = {},
    onPick: (GradeLevel) -> Unit = {}
) {
    var negativeOffset by rememberSaveable { mutableIntStateOf(0) }
    var buttonWidth by rememberSaveable { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
    ) {
        Popup(
            onDismissRequest = {},
            offset = IntOffset(0, y = -negativeOffset),
            properties = PopupProperties()
        ) {
            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn(
                    animationSpec = spring(stiffness = 600F),
                ) + slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(durationMillis = 300)
                ),
                exit = fadeOut(
                    animationSpec = spring(stiffness = 600F),
                ) + slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(durationMillis = 300)
                )
            ) {
                PopupMenuLayout(
                    matchWidth = buttonWidth,
                    onLayoutPlaced = { negativeOffset = it },
                    nonEmptyGrades = nonEmptyGrades,
                    currentGradeLevel = currentGradeLevel,
                    onPick = onPick
                )
            }
        }
        OutlinedButton(
            onClick = { onMenuVisibility() },
            modifier = Modifier
                .padding(horizontal = 6.dp)
                .onGloballyPositioned {
                    buttonWidth = it.size.width
                },
            enabled = isButtonEnabled,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonColors(
                containerColor = Color.Yellow,
                contentColor = Color.Black,
                disabledContainerColor = Color.Yellow,
                disabledContentColor = Color.Black
            ),
            border = BorderStroke(
                width = 4.dp,
                color = ProjectColors.OffOrange1
            )
        ) {
            Text(
                text = buttonText,
                modifier = Modifier,
            )
        }
    }
}

@Preview
@Composable
fun HomePageGradeLevelMenuPreview() {
    HomePageGradeLevelMenu()
}

@Composable
fun PopupMenuLayout(
    matchWidth: Int = 0,
    onLayoutPlaced: (Int) -> Unit = {},
    nonEmptyGrades: Set<GradeLevel> = setOf(),
    currentGradeLevel: GradeLevel = GradeLevel.EIGHT,
    onPick: (GradeLevel) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 6.dp)
            .background(
                color = Color.Yellow,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                border = BorderStroke(
                    width = 4.dp,
                    color = ProjectColors.OffOrange1
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .clip(shape = RoundedCornerShape(8.dp))
            .onPlaced {
                onLayoutPlaced(it.size.height)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        enumEntries<GradeLevel>().filterNot { it == GradeLevel.ERROR || it !in nonEmptyGrades }.forEach { gradeLevel ->
            Text(
                text = "${gradeLevel.grade}",
                modifier = Modifier
                    .widthIn(min = matchWidth.pxToDp)
                    .checkIfAddOtherModifier(
                        shouldAddOtherModifier = currentGradeLevel != gradeLevel && (gradeLevel in nonEmptyGrades),
                        otherModifier = Modifier.clickable { onPick(gradeLevel) }
                    )
                    .padding(
                        vertical = 8.dp
                    ),
                color = when {
                    currentGradeLevel == gradeLevel -> ProjectColors.OffRed4
                    else -> Color.Black
                },
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun PopupMenuLayoutPreview() {
    PopupMenuLayout()
}