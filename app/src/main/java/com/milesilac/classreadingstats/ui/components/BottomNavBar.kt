package com.milesilac.classreadingstats.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BottomNavBar(
    isAllSelected: Boolean = true,
    onSelectAllButtonClick: () -> Unit = {},
    onGroupButtonClick: () -> Unit = {},
    onDeleteButtonClick: () -> Unit = {}
) {
    NavigationBar(
        modifier = Modifier,
        containerColor = Color.White,
        contentColor = Color.White,
        tonalElevation = NavigationBarDefaults.Elevation,
        windowInsets = NavigationBarDefaults.windowInsets
    ) {
        NavigationBarItem(
            selected = false,
            onClick = {
                onDeleteButtonClick()
            },
            icon = {
                Icon(
                    imageVector = Icons.TwoTone.Home,
                    contentDescription = "Home",
                    tint = Color.Black
                )
            },
            label = {
                Text(
                    text = "Home",
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = {
                onGroupButtonClick()
            },
            icon = {
                Icon(
                    imageVector = Icons.TwoTone.Add,
                    contentDescription = "Add",
                    tint = Color.Black
                )
            },
            label = {
                Text(
                    text = "Add",
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        )
    }
}

@Preview
@Composable
fun PreviewBottomNavBar() {
    BottomNavBar()
}