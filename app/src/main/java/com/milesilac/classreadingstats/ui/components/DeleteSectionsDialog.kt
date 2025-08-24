package com.milesilac.classreadingstats.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.Undo
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material.icons.twotone.Save
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.milesilac.classreadingstats.model.ClassSection
import com.milesilac.classreadingstats.model.ClassSheet
import com.milesilac.classreadingstats.model.StudentList
import com.milesilac.classreadingstats.model.toSectionString
import com.milesilac.classreadingstats.ui.dummyStudentListsEightAmethyst
import com.milesilac.classreadingstats.ui.dummyStudentListsEightDiamond
import com.milesilac.classreadingstats.ui.theme.ProjectColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteSectionsDialog(
    currentSheets: List<ClassSheet> = listOf(),
    onDismissDialog: () -> Unit = {},
    onSaveClick: (List<ClassSheet>) -> Unit = {},
) {
    BasicAlertDialog(
        onDismissRequest = {
            onDismissDialog()
        }
    ) {
        DeleteSectionsDialogLayout(
            currentSheets = currentSheets,
            onSaveClick = onSaveClick,
            onBackClick = onDismissDialog
        )
    }
}

@Preview
@Composable
fun DeleteSectionsDialogPreview() {
    DeleteSectionsDialog()
}

@Composable
fun DeleteSectionsDialogLayout(
    currentSheets: List<ClassSheet> = listOf(),
    onSaveClick: (List<ClassSheet>) -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    val lazyListState = rememberLazyListState()

    var currentSheets by remember { mutableStateOf(currentSheets) }
    var selectedSheets by remember { mutableStateOf(listOf<ClassSection>()) }

    Column(
        modifier = Modifier
            .background(
                color = ProjectColors.OffBrown1,
                shape = RoundedCornerShape(24.dp)
            )
            .clip(shape = RoundedCornerShape(24.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Delete Sections",
            color = ProjectColors.OffWhite4
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyColumn(
            modifier = Modifier
                .defaultMinSize(minHeight = 252.dp)
                .border(
                    width = 1.dp,
                    color = ProjectColors.OffWhite4,
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(shape = RoundedCornerShape(8.dp)),
            state = lazyListState
        ) {
            currentSheets.forEach { sheet ->
                item {
                    Row(
                        modifier = Modifier
                            .background(
                                color = when {
                                    sheet.classSection in selectedSheets -> ProjectColors.OffBrown3
                                    else -> ProjectColors.OffBrown1
                                },
                            )
                            .padding(
                                horizontal = 12.dp,
                                vertical = 12.dp
                            )
                            .clickable {
                                selectedSheets = selectedSheets.toMutableList().apply {
                                    when {
                                        sheet.classSection in this -> remove(sheet.classSection)
                                        else -> add(sheet.classSection)
                                    }
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = sheet.classSection.toSectionString(isSpaced = true),
                            Modifier
                                .weight(1F),
                            color = ProjectColors.OffWhite4,
                        )
                        val totalStudents = (sheet.maleStudents.filter { it is StudentList.StudentDetails } +
                                sheet.femaleStudents.filter { it is StudentList.StudentDetails }).size
                        Text(
                            text = "$totalStudents students",
                            Modifier
                                .weight(1F),
                            color = ProjectColors.OffWhite4,
                        )
                        Checkbox(
                            checked = sheet.classSection in selectedSheets,
                            onCheckedChange = null,
                            colors = CheckboxDefaults.colors(
                                checkedColor = ProjectColors.OffWhite4,
                                uncheckedColor = ProjectColors.OffWhite4,
                                checkmarkColor = ProjectColors.OffBrown1
                            )
                        )
                    }

                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = {
//                onOkayClick(inputName.trim())
                },
                modifier = Modifier,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonColors(
                    containerColor = ProjectColors.OffBrown2,
                    contentColor = ProjectColors.OffBrown1,
                    disabledContainerColor = ProjectColors.OffBrown2,
                    disabledContentColor = ProjectColors.OffBrown1
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = ProjectColors.OffWhite4
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
                        imageVector = Icons.AutoMirrored.TwoTone.Undo,
                        contentDescription = "Undo",
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Undo",
                        modifier = Modifier,
                    )
                }
            }
            OutlinedButton(
                onClick = {
//                onOkayClick(inputName.trim())
                },
                modifier = Modifier,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonColors(
                    containerColor = ProjectColors.OffBrown2,
                    contentColor = ProjectColors.OffBrown1,
                    disabledContainerColor = ProjectColors.OffBrown2,
                    disabledContentColor = ProjectColors.OffBrown1
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = ProjectColors.OffWhite4
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
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = {
                    onSaveClick(currentSheets)
                },
                modifier = Modifier,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonColors(
                    containerColor = ProjectColors.OffBrown2,
                    contentColor = ProjectColors.OffBrown1,
                    disabledContainerColor = ProjectColors.OffBrown2,
                    disabledContentColor = ProjectColors.OffBrown1
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = ProjectColors.OffWhite4
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
                        imageVector = Icons.TwoTone.Save,
                        contentDescription = "Save",
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Save",
                        modifier = Modifier,
                    )
                }
            }
            OutlinedButton(
                onClick = {
                    onBackClick()
                },
                modifier = Modifier,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonColors(
                    containerColor = ProjectColors.OffBrown2,
                    contentColor = ProjectColors.OffBrown1,
                    disabledContainerColor = ProjectColors.OffBrown2,
                    disabledContentColor = ProjectColors.OffBrown1
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = ProjectColors.OffWhite4
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
                    Text(
                        text = "Back",
                        modifier = Modifier,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun DeleteSectionsDialogLayoutPreview() {
    DeleteSectionsDialogLayout(
        currentSheets = listOf(dummyStudentListsEightAmethyst, dummyStudentListsEightDiamond)
    )
}