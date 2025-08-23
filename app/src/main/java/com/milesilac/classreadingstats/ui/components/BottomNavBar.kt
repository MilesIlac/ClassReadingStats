package com.milesilac.classreadingstats.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Edit
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
import com.milesilac.classreadingstats.ui.theme.ProjectColors

@Composable
fun BottomNavBar(
    onHomeButtonClick: () -> Unit = {},
    onGroupButtonClick: () -> Unit = {},
) {
    NavigationBar(
        modifier = Modifier,
        containerColor = ProjectColors.OffWhite4,
        contentColor = Color.White,
        tonalElevation = NavigationBarDefaults.Elevation,
        windowInsets = NavigationBarDefaults.windowInsets
    ) {
        NavigationBarItem(
            selected = false,
            onClick = {
                onHomeButtonClick()
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
                    imageVector = Icons.TwoTone.Edit,
                    contentDescription = "Manage",
                    tint = Color.Black
                )
            },
            label = {
                Text(
                    text = "Manage",
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        )
    }
}

@Preview
@Composable
fun BottomNavBarPreview() {
    BottomNavBar()
}