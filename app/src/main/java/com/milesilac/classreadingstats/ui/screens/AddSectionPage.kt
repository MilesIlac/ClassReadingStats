package com.milesilac.classreadingstats.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material.icons.twotone.Close
import androidx.compose.material.icons.twotone.Edit
import androidx.compose.material.icons.twotone.Save
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.milesilac.classreadingstats.helpers.nonScaledSp
import com.milesilac.classreadingstats.model.ClassSection
import com.milesilac.classreadingstats.model.ClassSheet
import com.milesilac.classreadingstats.model.GradeLevel
import com.milesilac.classreadingstats.model.Student
import com.milesilac.classreadingstats.model.StudentList
import com.milesilac.classreadingstats.model.initClassSheet
import com.milesilac.classreadingstats.ui.components.EditSectionNameDialog
import com.milesilac.classreadingstats.ui.theme.ProjectColors
import kotlin.enums.enumEntries

@Composable
fun AddSectionPage(
    tempClassSheet: ClassSheet = initClassSheet(),
    onUpdate: (UpdateTempClassSheet) -> Unit = {},
    onSaveClick: () -> Unit = {},
    onBackClick: (UpdateTempClassSheet) -> Unit = {},
    onAddStudentClick: (ClassSection) -> Unit = {},
    onVisible: () -> Unit = {}
) {
    onVisible()
    val selectedGradeLevel = tempClassSheet.classSection.gradeLevel
    val inputSectionName = tempClassSheet.classSection.sectionName
    val hasGradeAndSection = selectedGradeLevel != GradeLevel.ERROR && inputSectionName.isNotEmpty()
    var showEditDialog by rememberSaveable { mutableStateOf(false) }

//    val students = dummyStudentListsEightAmethyst.maleStudents.filter { it is StudentList.StudentDetails }.map {
//        (it as StudentList.StudentDetails).student
//    } + dummyStudentListsEightAmethyst.femaleStudents.filter { it is StudentList.StudentDetails }.map {
//        (it as StudentList.StudentDetails).student
//    } //test

    val students = (tempClassSheet.maleStudents + tempClassSheet.femaleStudents).mapNotNull {
        if (it is StudentList.StudentDetails) it.student else null
    }
    Column(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(ProjectColors.OffBlue2, ProjectColors.OffWhite4)
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
                    .background(color = ProjectColors.OffBlue2)
                    .padding(
                        horizontal = 12.dp,
                        vertical = 28.dp
                    )
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "ADD SECTION",
                    modifier = Modifier,
                    color = ProjectColors.OffWhite4,
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center
                )
            }
            Column(
                modifier = Modifier
                    .background(color = ProjectColors.OffWhite4)
                    .padding(
                        vertical = 16.dp
                    )
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    ProjectColors.OffAquaGreen1,
                                    ProjectColors.OffAquaGreen1
                                )
                            ),
                            alpha = 0.5F
                        )
                        .padding(
                            horizontal = 12.dp,
                            vertical = 12.dp
                        )
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val gradeLevels = enumEntries<GradeLevel>().filterNot { it == GradeLevel.ERROR }
                    Text(
                        text = "Grade Level:",
                        modifier = Modifier,
                        color = ProjectColors.OffWhite4,
                        fontSize = 16.sp.nonScaledSp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.width(32.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(gradeLevels) { gradeLevel ->
                            Box(
                                Modifier
                                    .size(40.dp)
                                    .background(
                                        color = when {
                                            selectedGradeLevel == gradeLevel -> ProjectColors.OffYellow2
                                            else -> ProjectColors.OffAquaGreen1
                                        },
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .border(
                                        color = ProjectColors.OffAquaGreen1,
                                        shape = RoundedCornerShape(12.dp),
                                        width = 2.dp
                                    )
                                    .clip(
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .selectable(
                                        selected = selectedGradeLevel == gradeLevel,
                                        onClick = { onUpdate(UpdateTempClassSheet.EventGradeLevel(gradeLevel = gradeLevel)) },
                                        role = Role.RadioButton
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${gradeLevel.grade}",
                                    modifier = Modifier,
                                    color = when {
                                        selectedGradeLevel == gradeLevel -> Color.Black
                                        else -> ProjectColors.OffWhite4
                                    },
                                    fontSize = 16.sp.nonScaledSp
                                )
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(ProjectColors.OffViolet2, ProjectColors.OffViolet2)
                            ),
                            alpha = 0.5F
                        )
                        .padding(
                            horizontal = 12.dp,
                            vertical = 12.dp
                        )
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Section Name:",
                        modifier = Modifier,
                        color = ProjectColors.OffWhite4,
                        fontSize = 16.sp.nonScaledSp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = inputSectionName,
                        modifier = Modifier
                            .weight(1F),
                        color = ProjectColors.OffWhite4,
                        fontSize = 16.sp,
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    OutlinedButton(
                        onClick = {
                            showEditDialog = true
                        },
                        modifier = Modifier,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonColors(
                            containerColor = ProjectColors.OffViolet2,
                            contentColor = ProjectColors.OffWhite4,
                            disabledContainerColor = ProjectColors.OffViolet2,
                            disabledContentColor = ProjectColors.OffWhite4
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
                                imageVector = Icons.TwoTone.Edit,
                                contentDescription = "Edit",
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Edit",
                                modifier = Modifier,
                            )
                        }
                    }
                }
            }
            if (hasGradeAndSection) {
                Column(
                    modifier = Modifier
                        .background(color = ProjectColors.OffBlue2)
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .background(color = ProjectColors.OffBlue2)
                            .padding(
                                horizontal = 12.dp,
                                vertical = 28.dp
                            )
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "ADD STUDENTS?",
                            modifier = Modifier,
                            color = ProjectColors.OffWhite4,
                            fontSize = 28.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                    if (students.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier
                                .background(color = ProjectColors.OffWhite4)
                                .padding(
                                    horizontal = 12.dp,
                                    vertical = 12.dp,
                                )
                                .weight(1F)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            item { //SAMPLE
                                Row(
                                    modifier = Modifier,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "1",
                                        modifier = Modifier,
                                        color = ProjectColors.OffGreen2,
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Juan Dela Cruz",
                                        modifier = Modifier
                                            .weight(1F),
                                        color = ProjectColors.OffGreen2,
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "M",
                                        modifier = Modifier,
                                        color = ProjectColors.OffGreen2,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                            students.forEach { inputStudent ->
                                item {
                                    Row(
                                        modifier = Modifier,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "${inputStudent.orderId.toInt()}",
                                            modifier = Modifier,
                                            color = ProjectColors.OffGreen2,
                                            textAlign = TextAlign.Center
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = inputStudent.name.trim(),
                                            modifier = Modifier
                                                .weight(1F),
                                            color = ProjectColors.OffGreen2,
                                            textAlign = TextAlign.Center
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = inputStudent.sex.sex,
                                            modifier = Modifier,
                                            color = ProjectColors.OffGreen2,
                                            textAlign = TextAlign.Center
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
                                text = "No added students at the moment.\nPlease click button below to add students.",
                                modifier = Modifier,
                                color = ProjectColors.OffGreen2,
                                fontSize = 18.sp.nonScaledSp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    OutlinedButton(
                        onClick = {
                            onAddStudentClick(
                                ClassSection(
                                    gradeLevel = selectedGradeLevel,
                                    sectionName = inputSectionName
                                )
                            )
                        },
                        modifier = Modifier
                            .background(color = ProjectColors.OffViolet1)
                            .padding(
                                horizontal = 12.dp,
                                vertical = 28.dp
                            )
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonColors(
                            containerColor = ProjectColors.OffYellow2,
                            contentColor = Color.DarkGray,
                            disabledContainerColor = ProjectColors.OffYellow2,
                            disabledContentColor = Color.DarkGray
                        ),
                        border = BorderStroke(
                            width = 2.dp,
                            color = ProjectColors.OffOrange2
                        )
                    ) {
                        Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.TwoTone.Add,
                                contentDescription = "Add Student",
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Add Student",
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
                    onClick = { onSaveClick() },
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
                    onClick = { onBackClick(UpdateTempClassSheet.EventDelete) },
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
        if (showEditDialog) {
            EditSectionNameDialog(
                currentSectionName = inputSectionName,
                onDismissDialog = { showEditDialog = false },
                onOkayClick = { newInputName ->
                    onUpdate(UpdateTempClassSheet.EventSectionName(sectionName = newInputName))
                    showEditDialog = false
                }
            )
        }
    }
}

@Preview
@Composable
fun AddSectionPagePreview() {
    AddSectionPage()
}

sealed class UpdateTempClassSheet {
    data object EventDelete : UpdateTempClassSheet()
    data class EventGradeLevel(val gradeLevel: GradeLevel) : UpdateTempClassSheet()
    data class EventSectionName(val sectionName: String) : UpdateTempClassSheet()
    data class EventSectionStudent(val student: Student) : UpdateTempClassSheet()
}