package com.milesilac.classreadingstats.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.milesilac.classreadingstats.model.GroupScreeningTest
import com.milesilac.classreadingstats.model.ReadingTest
import com.milesilac.classreadingstats.model.StudentList
import com.milesilac.classreadingstats.ui.dummyStudentListsEightAmethyst
import com.milesilac.classreadingstats.ui.theme.ProjectColors

@Composable
fun StudentGradeDetailPage(
    isPostTest: Boolean = false,
    gst: GroupScreeningTest,
    studentTest: ReadingTest,
) {
    val scrollState = rememberScrollState()
    val shouldGradePassage = when {
        isPostTest -> true
        else -> gst.shouldGradePassage()
    }

    val oralReadingPercentage = studentTest.oralReading.percentage
    val oralReadingLearnerLevel = studentTest.oralReading.level

    val readingComprehensionPercentage = studentTest.readingComprehension.inputPercentage
    val readingComprehensionLearnerLevel = studentTest.readingComprehension.level

    Column(
        modifier = Modifier
            .background(color = ProjectColors.OffWhite4)
            .fillMaxSize()
            .verticalScroll(state = scrollState)
    ) {
        if (isPostTest.not()) {
            Column(
                modifier = Modifier
                    .background(color = ProjectColors.OffViolet1)
                    .padding(
                        horizontal = 12.dp,
                        vertical = 12.dp
                    )
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Group Screening Test Score",
                    modifier = Modifier,
                    color = ProjectColors.OffWhite4,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
            }
            Text(
                text = "${gst.score.toInt()} (Level - ${gst.comprehensionLevel.level})",
                modifier = Modifier
                    .padding(
                        horizontal = 12.dp,
                        vertical = 12.dp
                    )
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                color = Color.Black,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
        if (shouldGradePassage) {
            Column(
                modifier = Modifier
                    .background(color = ProjectColors.OffOrange3)
                    .padding(
                        horizontal = 12.dp,
                        vertical = 12.dp
                    )
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Oral Reading",
                    modifier = Modifier,
                    color = ProjectColors.OffWhite4,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
            }
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = 12.dp,
                        vertical = 12.dp
                    )
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No. Of Miscues: ${studentTest.oralReading.numberOfMiscues.toInt()}",
                    modifier = Modifier,
                    color = Color.Black,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Percentage: %.2f".format(oralReadingPercentage),
                    modifier = Modifier,
                    color = Color.Black,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Learner Level: ${oralReadingLearnerLevel.level}",
                    modifier = Modifier,
                    color = Color.Black,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
            Column(
                modifier = Modifier
                    .background(color = ProjectColors.OffAquaGreen1)
                    .padding(
                        horizontal = 12.dp,
                        vertical = 12.dp
                    )
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Reading Comprehension",
                    modifier = Modifier,
                    color = ProjectColors.OffWhite4,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
            }
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = 12.dp,
                        vertical = 12.dp
                    )
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Percentage: $readingComprehensionPercentage",
                    modifier = Modifier,
                    color = Color.Black,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Learner Level: ${readingComprehensionLearnerLevel.level}",
                    modifier = Modifier,
                    color = Color.Black,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview
@Composable
fun StudentGradeDetailPagePreview() {
    val student = (dummyStudentListsEightAmethyst.maleStudents[15] as StudentList.StudentDetails).student
    StudentGradeDetailPage(
        gst = student.gst,
        studentTest = student.preTest
    )
}