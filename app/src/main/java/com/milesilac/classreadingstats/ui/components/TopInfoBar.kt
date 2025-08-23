package com.milesilac.classreadingstats.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.milesilac.classreadingstats.ui.dummyStudentListsEightAmethyst

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopInfoBar(
    section: String,
    onExportClick: () -> Unit = {},
) {
    TopAppBar(
        title = {
            Text(
                text = section
            )
        },
        modifier = Modifier,
        actions = {
            Text(
                text = "EXPORT",
                modifier = Modifier
                    .clickable {
                        onExportClick()
                    }
                    .padding(12.dp)
            )
//            IconButton(
//                modifier = Modifier,
//                onClick = {
//                    onExportClick()
//                },
//            ) {
//                Icon(
//                    imageVector = Icons.TwoTone.MoreVert,
//                    contentDescription = "More",
//                    tint = Color.Black
//                )
//            }
        },
    )
}

@Preview
@Composable
fun TopInfoBarPreview() {
    val gradeLevel = dummyStudentListsEightAmethyst.classSection.gradeLevel.grade
    val sectionName = dummyStudentListsEightAmethyst.classSection.sectionName
    val section = "${gradeLevel}-${sectionName.uppercase()}"
    TopInfoBar(section = section)
}