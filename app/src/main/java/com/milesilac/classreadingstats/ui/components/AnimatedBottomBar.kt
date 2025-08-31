package com.milesilac.classreadingstats.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Close
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material.icons.twotone.Edit
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.milesilac.classreadingstats.ui.theme.ProjectColors

@Composable
fun AnimatedBottomBar(
    isDeleteMode: Boolean = false,
    isDeleteBtnEnabled: Boolean = false,
    onManageClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    AnimatedContent(
        targetState = isDeleteMode,
        transitionSpec = {
            fadeIn(
                animationSpec = tween(200)
            ) togetherWith fadeOut(
                animationSpec = tween(180, delayMillis = 100)
            )
        }
    ) { isStateInDeleteMode ->
        when {
            isStateInDeleteMode -> {
                DeleteModeBar(
                    isDeleteBtnEnabled = isDeleteBtnEnabled,
                    onDeleteClick = onDeleteClick,
                    onBackClick = onBackClick
                )
            }
            else -> {
                ManageBar(
                    onManageClick = onManageClick
                )
            }
        }
    }
}

@Composable
fun DeleteModeBar(
    isDeleteBtnEnabled: Boolean = false,
    onDeleteClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        color = ProjectColors.OffWhite4,
        contentColor = Color.Black,
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(
                        horizontal = 12.dp,
                        vertical = 8.dp
                    )
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedButton(
                    onClick = onDeleteClick,
                    modifier = Modifier,
                    enabled = isDeleteBtnEnabled,
                    colors = ButtonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.White
                    )
                ) {
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.TwoTone.Delete,
                            contentDescription = "Delete Students",
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Delete",
                            modifier = Modifier,
                            color = Color.Black,
                        )
                    }
                }
                OutlinedButton(
                    onClick = onBackClick,
                    modifier = Modifier,
                    colors = ButtonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.White
                    )
                ) {
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.TwoTone.Close,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Back",
                            modifier = Modifier,
                            color = Color.Black,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ManageBar(
    onManageClick: () -> Unit = {},
) {
    Surface(
        onClick = onManageClick,
        modifier = Modifier
            .semantics { role = Role.Button }
            .fillMaxWidth()
            .height(80.dp),
        shape = RectangleShape,
        color = ProjectColors.OffGreen3,
        contentColor = ProjectColors.OffWhite4,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.TwoTone.Edit,
                contentDescription = "Manage",
            )
            Text(
                text = "Manage",
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun AnimatedBottomBarPreview() {
    AnimatedBottomBar()
}

@Preview
@Composable
fun DeleteModeBarPreview() {
    DeleteModeBar()
}

@Preview
@Composable
fun ManageBarPreview() {
    ManageBar()
}