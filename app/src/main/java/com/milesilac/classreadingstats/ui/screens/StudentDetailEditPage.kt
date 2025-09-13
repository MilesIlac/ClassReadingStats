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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.milesilac.classreadingstats.helpers.checkIfAddOtherModifier
import com.milesilac.classreadingstats.helpers.nonScaledSp
import com.milesilac.classreadingstats.model.ClassSection
import com.milesilac.classreadingstats.model.ReadingTest
import com.milesilac.classreadingstats.model.Student
import com.milesilac.classreadingstats.model.StudentList
import com.milesilac.classreadingstats.model.StudentSexOrient
import com.milesilac.classreadingstats.model.emptyReadingTest
import com.milesilac.classreadingstats.model.emptyStudent
import com.milesilac.classreadingstats.model.initClassSection
import com.milesilac.classreadingstats.model.toSectionString
import com.milesilac.classreadingstats.ui.components.EditStudentInfoDialog
import com.milesilac.classreadingstats.ui.components.EditStudentNameDialog
import com.milesilac.classreadingstats.ui.components.StudentInfoType
import com.milesilac.classreadingstats.ui.dummyStudentListsEightAmethyst
import com.milesilac.classreadingstats.ui.theme.ProjectColors
import kotlinx.coroutines.launch

@Composable
fun StudentDetailEditPage(
    studentEditType: StudentEditType = StudentEditType.ADD,
    isFromAddSectionPage: Boolean = false,
    student: Student = emptyStudent(),
    classSections: List<ClassSection> = listOf(),
    onUpdate: (StudentDetailEditEvent) -> Unit = {},
    onSaveClick: () -> Unit = {},
    onBackClick: (ClassSection) -> Unit = {},
    onVisible: () -> Unit = {}
) {
    onVisible()
    val pagerState = rememberPagerState(pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()
    var showStudentNameEditDialog by rememberSaveable { mutableStateOf(false) }
    var showPickSectionDialog by rememberSaveable { mutableStateOf(false) }
    var showMaleOrFemaleDialog by rememberSaveable { mutableStateOf(false) }

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
                    text = when (studentEditType) {
                        StudentEditType.EDIT -> "EDIT STUDENT"
                        StudentEditType.ADD -> "ADD STUDENT"
                    },
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
                    Text(
                        text = "Student Name:",
                        modifier = Modifier,
                        color = ProjectColors.OffWhite4,
                        fontSize = 16.sp.nonScaledSp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = student.name,
                        modifier = Modifier
                            .weight(1F),
                        color = ProjectColors.OffWhite4,
                        fontSize = 16.sp,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    OutlinedButton(
                        onClick = {
                            showStudentNameEditDialog = true
                        },
                        modifier = Modifier,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonColors(
                            containerColor = ProjectColors.OffGreen2,
                            contentColor = ProjectColors.OffWhite4,
                            disabledContainerColor = ProjectColors.OffGreen2,
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
            Row(
                modifier = Modifier
                    .background(color = ProjectColors.OffWhite4)
                    .padding(vertical = 1.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                Column(
                    modifier = Modifier
                        .background(color = ProjectColors.OffBlue2)
                        .checkIfAddOtherModifier(
                            shouldAddOtherModifier = isFromAddSectionPage.not(),
                            otherModifier = Modifier.clickable {
                                showPickSectionDialog = true
                            }
                        )
                        .padding(
                            vertical = 12.dp
                        )
                        .weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val inputSection = if (student.section == initClassSection()) {
                        "--"
                    } else student.section.toSectionString()
                    Text(
                        text = inputSection,
                        modifier = Modifier,
                        color = ProjectColors.OffWhite4,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                    val inputSectionSubtext = if (isFromAddSectionPage.not()) {
                        "Click to Edit"
                    } else "(From Add Section)"
                    Text(
                        text = inputSectionSubtext,
                        modifier = Modifier,
                        color = ProjectColors.OffWhite4,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
                Column(
                    modifier = Modifier
                        .background(color = ProjectColors.OffBlue2)
                        .clickable {
                            showMaleOrFemaleDialog = true
                        }
                        .padding(
                            vertical = 12.dp
                        )
                        .weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val inputSOrient = if (student.sex == StudentSexOrient.ERROR) {
                        "--"
                    } else student.sex.wordedLabel
                    Text(
                        text = inputSOrient,
                        modifier = Modifier,
                        color = ProjectColors.OffWhite4,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Click to Edit",
                        modifier = Modifier,
                        color = ProjectColors.OffWhite4,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .weight(1F),
            beyondViewportPageCount = 1
        ) { page ->
            // Our page content
            when (page) {
                1 -> {
                    StudentGradeEditPage(
                        modifier = Modifier,
                        studentPersistenceId = student.persistenceId,
                        studentTest = student.postTest ?: emptyReadingTest(isPostTest = true),
                        onUpdateGrade = { readingTest ->
                            onUpdate(
                                StudentDetailEditEvent.EventReadingTest(
                                    isPostTest = true, readingTest = readingTest
                                )
                            )
                            println("classInits Grade updated; gstScore: ${readingTest.groupScreeningTest.score}")
                        }
                    )
                }
                else -> StudentGradeEditPage(
                    modifier = Modifier,
                    studentPersistenceId = student.persistenceId,
                    studentTest = student.preTest,
                    onUpdateGrade = { readingTest ->
                        onUpdate(
                            StudentDetailEditEvent.EventReadingTest(
                                isPostTest = false, readingTest = readingTest
                            )
                        )
                        println("classInits Grade updated; gstScore: ${readingTest.groupScreeningTest.score}")
                    }
                )
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
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = Color.DarkGray
                        )
                        .padding(horizontal = 2.dp, vertical = 4.dp)
                        .fillMaxWidth()
                        .weight(1F),
                ) {
                    Text(
                        text = "PreTest",
                        modifier = Modifier
                            .background(
                                color = ProjectColors.OffWhite4,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clip(shape = RoundedCornerShape(12.dp))
                            .clickable {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(page = 0)
                                }
                            }
                            .padding(vertical = 12.dp)
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        color = Color.Black,
                        fontSize = 20.sp.nonScaledSp,
                        textAlign = TextAlign.Center
                    )
                }
                Box(
                    modifier = Modifier
                        .background(
                            color = Color.DarkGray
                        )
                        .padding(horizontal = 2.dp, vertical = 4.dp)
                        .fillMaxWidth()
                        .weight(1F),
                ) {
                    Text(
                        text = "PostTest",
                        modifier = Modifier
                            .background(
                                color = ProjectColors.OffWhite4,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clip(shape = RoundedCornerShape(12.dp))
                            .clickable {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(page = 1)
                                }
                            }
                            .padding(vertical = 12.dp)
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        color = Color.Black,
                        fontSize = 20.sp.nonScaledSp,
                        textAlign = TextAlign.Center
                    )
                }
            }
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
                    onClick = { onBackClick(student.section) },
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
        when {
            showStudentNameEditDialog -> {
                EditStudentNameDialog(
                    currentStudentName = student.name,
                    onDismissDialog = { showStudentNameEditDialog = false },
                    onOkayClick = { newInputName ->
                        onUpdate(StudentDetailEditEvent.EventName(studentName = newInputName))
                        showStudentNameEditDialog = false
                    }
                )
            }
            showPickSectionDialog -> {
                showMaleOrFemaleDialog = false
                EditStudentInfoDialog(
                    studentInfoType = StudentInfoType.SECTION,
                    currentSection = student.section,
                    sections = classSections,
                    onDismissDialog = { showPickSectionDialog = false },
                    onSectionPick = { selected ->
                        onUpdate(StudentDetailEditEvent.EventSection(section = selected))
                        showPickSectionDialog = false
                    },
                )
            }
            showMaleOrFemaleDialog -> {
                showPickSectionDialog = false
                EditStudentInfoDialog(
                    studentInfoType = StudentInfoType.SEX_ORIENT,
                    currentSex = student.sex,
                    onDismissDialog = { showMaleOrFemaleDialog = false },
                    onSexOrientPick = { selected ->
                        onUpdate(StudentDetailEditEvent.EventSexOrient(sex = selected))
                        showMaleOrFemaleDialog = false
                    },
                )
            }
        }
    }
}

@Preview
@Composable
fun StudentDetailEditPagePreview() {
    StudentDetailEditPage(
        student = (dummyStudentListsEightAmethyst.maleStudents[15] as StudentList.StudentDetails).student,
//        student = emptyStudent()
    )
}

enum class StudentEditType {
    ADD, EDIT
}

sealed class StudentDetailEditEvent {
    data class EventName(val studentName: String) : StudentDetailEditEvent()
    data class EventSection(val section: ClassSection) : StudentDetailEditEvent()
    data class EventSexOrient(val sex: StudentSexOrient) : StudentDetailEditEvent()
    data class EventReadingTest(
        val isPostTest: Boolean,
        val readingTest: ReadingTest
    ) : StudentDetailEditEvent()
    data class EventReset(
        val section: ClassSection = initClassSection()
    ) : StudentDetailEditEvent()
}