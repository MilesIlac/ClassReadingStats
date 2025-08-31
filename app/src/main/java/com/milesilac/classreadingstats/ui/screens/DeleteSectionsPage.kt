package com.milesilac.classreadingstats.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Close
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material.icons.twotone.Restore
import androidx.compose.material.icons.twotone.Save
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.milesilac.classreadingstats.helpers.advancedBackgroundColorChooser
import com.milesilac.classreadingstats.helpers.nonScaledSp
import com.milesilac.classreadingstats.model.ClassSection
import com.milesilac.classreadingstats.model.ClassSheet
import com.milesilac.classreadingstats.model.StudentList
import com.milesilac.classreadingstats.model.toSectionString
import com.milesilac.classreadingstats.ui.dummyStudentListsEightAmethyst
import com.milesilac.classreadingstats.ui.dummyStudentListsEightDiamond
import com.milesilac.classreadingstats.ui.theme.ProjectColors

@Composable
fun DeleteSectionsPage(
    sheets: List<ClassSheet> = listOf(),
    onSaveClick: (List<ClassSheet>) -> Unit = {},
    onBackClick: () -> Unit = {},
    onVisible: () -> Unit = {}
) {
    onVisible()
    var currentSheets by remember { mutableStateOf(sheets) }
    var currentDeletePendingSheets by remember { mutableStateOf(listOf<ClassSheet>()) }
    var selectedSheets by remember { mutableStateOf(setOf<ClassSection>()) }
    var selectedDeletePendingSheets by remember { mutableStateOf(setOf<ClassSection>()) }
    val isDeleteEnabled = selectedSheets.isNotEmpty()
    val isRestoreEnabled = selectedDeletePendingSheets.isNotEmpty()

    Column(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(ProjectColors.OffViolet3, ProjectColors.OffViolet3)
                )
            )
            .systemBarsPadding()
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .background(color = ProjectColors.OffWhite4)
                .weight(1F)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .background(color = ProjectColors.OffViolet3)
                    .padding(
                        horizontal = 12.dp,
                        vertical = 28.dp
                    )
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "DELETE SECTIONS",
                    modifier = Modifier,
                    color = ProjectColors.OffWhite4,
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center
                )
            }
            Column(
                modifier = Modifier
                    .background(color = ProjectColors.OffWhite4)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .background(color = ProjectColors.OffYellow2)
                        .padding(
                            horizontal = 12.dp,
                            vertical = 12.dp
                        )
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Current Sections",
                        modifier = Modifier,
                        color = Color.Black,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }
                if (currentSheets.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .background(color = ProjectColors.OffWhite4)
                            .padding(
                                vertical = 4.dp,
                            )
                            .weight(1F)
                            .fillMaxWidth(),
                    ) {
//                        item { //SAMPLE
//                            Row(
//                                modifier = Modifier
//                                    .advancedBackgroundColorChooser(
//                                        doBrushCondition = false,
//                                        brush = Brush.horizontalGradient(
//                                            colors = listOf(ProjectColors.OffViolet1, ProjectColors.OffViolet1)
//                                        ),
//                                        brushAlpha = 0.9F,
//                                        color = ProjectColors.OffWhite4,
//                                    )
//                                    .padding(
//                                        horizontal = 12.dp,
//                                        vertical = 8.dp
//                                    ),
//                                verticalAlignment = Alignment.CenterVertically
//                            ) {
//                                Text(
//                                    text = "10-AQUAMARINE",
//                                    Modifier
//                                        .weight(1F),
//                                    color = ProjectColors.OffViolet2,
//                                )
//                                Text(
//                                    text = "40 students",
//                                    Modifier
//                                        .weight(1F),
//                                    color = ProjectColors.OffViolet2,
//                                    textAlign = TextAlign.End
//                                )
//                            }
//                        }
                        currentSheets.forEach { sheet ->
                            val itemSelected = sheet.classSection in selectedSheets
                            item {
                                Row(
                                    modifier = Modifier
                                        .advancedBackgroundColorChooser(
                                            doBrushCondition = itemSelected,
                                            brush = Brush.horizontalGradient(
                                                colors = listOf(ProjectColors.OffViolet1, ProjectColors.OffViolet1)
                                            ),
                                            brushAlpha = 0.5F,
                                            color = ProjectColors.OffWhite4,
                                        )
                                        .clickable {
                                            selectedSheets = selectedSheets.toMutableSet().apply {
                                                when {
                                                    sheet.classSection in this -> remove(sheet.classSection)
                                                    else -> add(sheet.classSection)
                                                }
                                            }
                                        }
                                        .padding(
                                            horizontal = 12.dp,
                                            vertical = 8.dp
                                        ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = sheet.classSection.toSectionString(isSpaced = true),
                                        Modifier
                                            .weight(1F),
                                        color = Color.Black,
                                    )
                                    val totalStudents = (sheet.maleStudents.filter { it is StudentList.StudentDetails } +
                                            sheet.femaleStudents.filter { it is StudentList.StudentDetails }).size
                                    Text(
                                        text = "$totalStudents students",
                                        Modifier
                                            .weight(1F),
                                        color = Color.Black,
                                        textAlign = TextAlign.End
                                    )
                                }
                            }
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .background(color = ProjectColors.OffWhite4)
                            .padding(
                                horizontal = 12.dp,
                                vertical = 12.dp,
                            )
                            .weight(1F)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Empty",
                            modifier = Modifier,
                            color = Color.Black,
                            fontSize = 18.sp.nonScaledSp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .background(color = ProjectColors.OffAquaGreen2)
                        .padding(
                            horizontal = 12.dp,
                            vertical = 12.dp
                        )
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Sections to be Deleted",
                        modifier = Modifier,
                        color = ProjectColors.OffWhite4,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }
                if (currentDeletePendingSheets.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .background(color = ProjectColors.OffWhite4)
                            .padding(
                                vertical = 4.dp,
                            )
                            .weight(1F)
                            .fillMaxWidth(),
                    ) {
//                        item { //SAMPLE
//                            Row(
//                                modifier = Modifier
//                                    .advancedBackgroundColorChooser(
//                                        doBrushCondition = false,
//                                        brush = Brush.horizontalGradient(
//                                            colors = listOf(ProjectColors.OffViolet1, ProjectColors.OffViolet1)
//                                        ),
//                                        brushAlpha = 0.9F,
//                                        color = ProjectColors.OffWhite4,
//                                    )
//                                    .padding(
//                                        horizontal = 12.dp,
//                                        vertical = 8.dp
//                                    ),
//                                verticalAlignment = Alignment.CenterVertically
//                            ) {
//                                Text(
//                                    text = "10-AQUAMARINE",
//                                    Modifier
//                                        .weight(1F),
//                                    color = ProjectColors.OffBrown1,
//                                )
//                                Text(
//                                    text = "40 students",
//                                    Modifier
//                                        .weight(1F),
//                                    color = ProjectColors.OffBrown1,
//                                    textAlign = TextAlign.End
//                                )
//                            }
//                        }
                        currentDeletePendingSheets.forEach { sheet ->
                            val itemSelected = sheet.classSection in selectedDeletePendingSheets
                            item {
                                Row(
                                    modifier = Modifier
                                        .background(
                                            color = when {
                                                itemSelected -> ProjectColors.OffRed1
                                                else -> ProjectColors.OffWhite4
                                            }
                                        )
                                        .clickable {
                                            selectedDeletePendingSheets = selectedDeletePendingSheets.toMutableSet().apply {
                                                when {
                                                    sheet.classSection in this -> remove(sheet.classSection)
                                                    else -> add(sheet.classSection)
                                                }
                                            }
                                        }
                                        .padding(
                                            horizontal = 12.dp,
                                            vertical = 8.dp
                                        ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = sheet.classSection.toSectionString(isSpaced = true),
                                        Modifier
                                            .weight(1F),
                                        color = when {
                                            itemSelected -> ProjectColors.OffWhite4
                                            else -> Color.Black
                                        },
                                    )
                                    val totalStudents = (sheet.maleStudents.filter { it is StudentList.StudentDetails } +
                                            sheet.femaleStudents.filter { it is StudentList.StudentDetails }).size
                                    Text(
                                        text = "$totalStudents students",
                                        Modifier
                                            .weight(1F),
                                        color = when {
                                            itemSelected -> ProjectColors.OffWhite4
                                            else -> Color.Black
                                        },
                                        textAlign = TextAlign.End
                                    )
                                }
                            }
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .background(color = ProjectColors.OffWhite4)
                            .padding(
                                horizontal = 12.dp,
                                vertical = 12.dp,
                            )
                            .weight(1F)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Empty",
                            modifier = Modifier,
                            color = Color.Black,
                            fontSize = 18.sp.nonScaledSp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .background(color = ProjectColors.OffYellow2)
                        .padding(
                            horizontal = 12.dp,
                            vertical = 20.dp
                        )
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    OutlinedButton(
                        onClick = {
                            currentSheets = currentSheets.toMutableList().apply {
                                val newDeletePendingSheets = filter { it.classSection in selectedSheets }
                                currentDeletePendingSheets = currentDeletePendingSheets.toMutableList().apply {
                                    addAll(newDeletePendingSheets)
                                }
                                removeAll(newDeletePendingSheets)
                            }
                            selectedSheets = emptySet()
                        },
                        modifier = Modifier,
                        enabled = isDeleteEnabled,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonColors(
                            containerColor = ProjectColors.OffAquaGreen2,
                            contentColor = ProjectColors.OffWhite4,
                            disabledContainerColor = ProjectColors.OffBrown1,
                            disabledContentColor = ProjectColors.OffBrown3
                        ),
                        border = BorderStroke(
                            width = 1.dp,
                            color = ProjectColors.OffBrown2
                        ),
                        contentPadding = PaddingValues(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        )
                    ) {
                        Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.TwoTone.Delete,
                                contentDescription = "Delete",
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Delete",
                                modifier = Modifier,
                            )
                        }
                    }
                    OutlinedButton(
                        onClick = {
                            currentDeletePendingSheets = currentDeletePendingSheets.toMutableList().apply {
                                val newRestoredSheets = filter { it.classSection in selectedDeletePendingSheets }
                                currentSheets = currentSheets.toMutableList().apply {
                                    addAll(newRestoredSheets)
                                }
                                removeAll(newRestoredSheets)
                            }
                            selectedDeletePendingSheets = emptySet()
                        },
                        modifier = Modifier,
                        enabled = isRestoreEnabled,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonColors(
                            containerColor = ProjectColors.OffAquaGreen2,
                            contentColor = ProjectColors.OffWhite4,
                            disabledContainerColor = ProjectColors.OffBrown1,
                            disabledContentColor = ProjectColors.OffBrown3
                        ),
                        border = BorderStroke(
                            width = 1.dp,
                            color = ProjectColors.OffBrown2
                        ),
                        contentPadding = PaddingValues(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        )
                    ) {
                        Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.TwoTone.Restore,
                                contentDescription = "Restore",
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Restore",
                                modifier = Modifier,
                            )
                        }
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .background(color = ProjectColors.OffWhite4)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(
                        horizontal = 12.dp,
                        vertical = 8.dp
                    ),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedButton(
                    onClick = {  },
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
                            imageVector = Icons.TwoTone.Save,
                            contentDescription = "Save",
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Save",
                            modifier = Modifier,
                            color = Color.Black,
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                OutlinedButton(
                    onClick = { onBackClick() },
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
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        //dialogs
    }
}

@Preview
@Composable
fun DeleteSectionsPagePreview() {
    DeleteSectionsPage(
        sheets = listOf(dummyStudentListsEightAmethyst, dummyStudentListsEightDiamond)
    )
}