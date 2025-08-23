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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Close
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material.icons.twotone.Edit
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.milesilac.classreadingstats.helpers.nonScaledSp
import com.milesilac.classreadingstats.model.LearnerLevel
import com.milesilac.classreadingstats.model.Student
import com.milesilac.classreadingstats.model.StudentList
import com.milesilac.classreadingstats.model.calculateLearnerOverallReadingProfile
import com.milesilac.classreadingstats.ui.dummyStudentListsEightAmethyst
import com.milesilac.classreadingstats.ui.theme.ProjectColors
import kotlinx.coroutines.launch

@Composable
fun StudentDetailsPage(
    student: Student,
    onEditClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onDelete: (Student) -> Unit = {},
    onVisible: () -> Unit = {}
) {
    onVisible()
    val pagerState = rememberPagerState(pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()

    val hasPostTest = true //student.postTest != null

    val oralReadingLearnerLevel = student.preTest.oralReading?.level ?: LearnerLevel.ERROR
    val readingComprehensionLearnerLevel = student.preTest.readingComprehension?.level ?: LearnerLevel.ERROR

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
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Column(
                    modifier = Modifier
                        .background(color = ProjectColors.OffBlue2)
                        .padding(
                            start = 12.dp,
                            end = 12.dp,
                            top = 28.dp,
                            bottom = 28.dp
                        )
                        .fillMaxWidth()
                        .align(Alignment.Center),
//                        .align(Alignment.CenterHorizontally),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = student.name.trim(),
                        modifier = Modifier,
                        color = ProjectColors.OffWhite4,
                        fontSize = 28.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = student.section.trim(),
                            modifier = Modifier,
                            color = ProjectColors.OffWhite4,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "|",
                            modifier = Modifier,
                            color = ProjectColors.OffWhite4,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = student.sex.wordedLabel,
                            modifier = Modifier,
                            color = ProjectColors.OffWhite4,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                IconButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd),
                    onClick = {
                        onDelete(student)
                    },
                ) {
                    Icon(
                        imageVector = Icons.TwoTone.Delete,
                        contentDescription = "More",
                        tint = ProjectColors.OffWhite4
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
                    StudentGradeDetailPage(
                        studentTest = student.postTest ?: student.preTest,
                    )
                }
                else -> StudentGradeDetailPage(
                    studentTest = student.preTest,
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
        }
        Column(
            modifier = Modifier
                .background(color = ProjectColors.OffWhite4)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column (
                modifier = Modifier
                    .background(color = ProjectColors.OffYellow1)
                    .padding(vertical = 20.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "PRETEST READING PROFILE",
                    modifier = Modifier,
                    color = Color.Black,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "${calculateLearnerOverallReadingProfile(orLevel = oralReadingLearnerLevel, rcLevel = readingComprehensionLearnerLevel)}",
                    modifier = Modifier,
                    color = Color.Black,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
            }
            if (hasPostTest) {
                Column (
                    modifier = Modifier
                        .background(color = ProjectColors.OffBlue2)
                        .padding(vertical = 20.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "POSTTEST READING PROFILE",
                        modifier = Modifier,
                        color = ProjectColors.OffWhite4,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "${calculateLearnerOverallReadingProfile(orLevel = oralReadingLearnerLevel, rcLevel = readingComprehensionLearnerLevel)}",
                        modifier = Modifier,
                        color = ProjectColors.OffWhite4,
                        fontSize = 24.sp,
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
                    onClick = { onEditClick() },
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
                            imageVector = Icons.TwoTone.Edit,
                            contentDescription = "Edit",
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Edit",
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
}

@Preview
@Composable
fun StudentDetailsPagePreview() {
    StudentDetailsPage(
        student = (dummyStudentListsEightAmethyst.maleStudents[15] as StudentList.StudentDetails).student
    )
}