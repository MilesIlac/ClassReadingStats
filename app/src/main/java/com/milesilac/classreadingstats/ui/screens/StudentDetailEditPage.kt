package com.milesilac.classreadingstats.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.twotone.Save
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.milesilac.classreadingstats.helpers.nonScaledSp
import com.milesilac.classreadingstats.model.ClassSection
import com.milesilac.classreadingstats.model.Student
import com.milesilac.classreadingstats.model.StudentList
import com.milesilac.classreadingstats.model.emptyReadingTest
import com.milesilac.classreadingstats.model.toGradeLevelInt
import com.milesilac.classreadingstats.model.toStudentSexOrient
import com.milesilac.classreadingstats.model.toStudentSexOrientString
import com.milesilac.classreadingstats.ui.components.EditStudentInfoDialog
import com.milesilac.classreadingstats.ui.components.StudentInfoType
import com.milesilac.classreadingstats.ui.dummySections
import com.milesilac.classreadingstats.ui.dummyStudentListsEightAmethyst
import com.milesilac.classreadingstats.ui.theme.ProjectColors
import kotlinx.coroutines.launch

@Composable
fun StudentDetailEditPage(
    student: Student,
    classSections: List<ClassSection> = listOf(),
    onSaveClick: (Student) -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()
    val inputStudent = remember { student }
    var showPickSectionDialog by rememberSaveable { mutableStateOf(false) }
    var showMaleOrFemaleDialog by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(color = ProjectColors.OffWhite4)
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
                    text = inputStudent.name.trim(),
                    modifier = Modifier,
                    color = ProjectColors.OffWhite4,
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center
                )
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
                        .clickable {
                            showPickSectionDialog = true
                        }
                        .padding(
                            vertical = 12.dp
                        )
                        .weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = inputStudent.section.trim(),
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
                    val sex = if (inputStudent.sex == "F") {
                        "Female"
                    } else "Male"
                    Text(
                        text = sex,
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
                        studentName = inputStudent.name,
                        studentTest = inputStudent.postTest ?: emptyReadingTest(),
                        onUpdateGrade = { readingTest ->
                            inputStudent.postTest = readingTest
                            println("Grade updated; gstScore: ${inputStudent.postTest?.groupScreeningTest?.score}")
                        }
                    )
                }
                else -> StudentGradeEditPage(
                    modifier = Modifier,
                    studentName = inputStudent.name,
                    studentTest = inputStudent.preTest,
                    onUpdateGrade = { readingTest ->
                        inputStudent.preTest = readingTest
                        println("Grade updated; gstScore: ${inputStudent.preTest.groupScreeningTest.score}")
                    }
                )
            }
        }
        Column(
            modifier = Modifier
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
                    onClick = { onSaveClick(inputStudent) },
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
        when {
            showPickSectionDialog -> {
                showMaleOrFemaleDialog = false
                EditStudentInfoDialog(
                    studentInfoType = StudentInfoType.SECTION,
                    currentSection = inputStudent.section,
                    sections = dummySections,
                    onDismissDialog = { showPickSectionDialog = false },
                    onSectionPick = { selected ->
                        inputStudent.section = "${selected.gradeLevel.toGradeLevelInt()}-${selected.sectionName.uppercase()}"
                        showPickSectionDialog = false
                    },
                )
            }
            showMaleOrFemaleDialog -> {
                showPickSectionDialog = false
                EditStudentInfoDialog(
                    studentInfoType = StudentInfoType.SEX_ORIENT,
                    currentSex = inputStudent.sex.toStudentSexOrient(),
                    onDismissDialog = { showMaleOrFemaleDialog = false },
                    onSexOrientPick = { selected ->
                        inputStudent.sex = selected.toStudentSexOrientString()
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
        classSections = dummySections
    )
}