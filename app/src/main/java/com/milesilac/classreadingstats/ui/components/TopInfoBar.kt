package com.milesilac.classreadingstats.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.milesilac.classreadingstats.model.toGradeLevelInt
import com.milesilac.classreadingstats.ui.dummyStudentListsEightAmethyst

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopInfoBar(
    section: String,
    onGroupButtonClick: () -> Unit = {},
    onDeleteButtonClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = section
            )
        },
        modifier = Modifier,
        actions = {
            IconButton(
                modifier = Modifier,
                onClick = {},
            ) {
                Icon(
                    imageVector = Icons.TwoTone.MoreVert,
                    contentDescription = "More",
                    tint = Color.Black
                )
            }
        },
    )
}

@Preview
@Composable
fun PreviewTopInfoBar() {
    val gradeLevel = dummyStudentListsEightAmethyst.classSection.gradeLevel.toGradeLevelInt()
    val sectionName = dummyStudentListsEightAmethyst.classSection.sectionName
    val section = "${gradeLevel}-${sectionName.uppercase()}"
    TopInfoBar(section = section)
}