package com.milesilac.classreadingstats.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.OpenInNew
import androidx.compose.material.icons.twotone.Close
import androidx.compose.material.icons.twotone.Edit
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.milesilac.classreadingstats.helpers.nonScaledSp
import com.milesilac.classreadingstats.model.Student
import com.milesilac.classreadingstats.model.StudentList
import com.milesilac.classreadingstats.model.calculateComprehensionLevel
import com.milesilac.classreadingstats.model.calculateLearnerOralReading
import com.milesilac.classreadingstats.model.calculateLearnerOverallReadingProfile
import com.milesilac.classreadingstats.model.calculateLearnerReadingComprehension
import com.milesilac.classreadingstats.model.shouldGradePassage
import com.milesilac.classreadingstats.model.toComprehensionLevelString
import com.milesilac.classreadingstats.model.toLearnerLevelString
import com.milesilac.classreadingstats.ui.dummyStudentListsEightAmethyst
import com.milesilac.classreadingstats.ui.theme.ProjectColors

@Composable
fun StudentDetailsPage(
    student: Student,
    onBackClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val shouldGradePassage = student.shouldGradePassage()

    val oralReadingPercentage = student.oralReading?.percentage ?: -1F
    val oralReadingLearnerLevel = calculateLearnerOralReading(percentage = oralReadingPercentage)

    val readingComprehensionPercentage = student.readingComprehension?.inputPercentage ?: -1F
    val readingComprehensionLearnerLevel = calculateLearnerReadingComprehension(percentage = readingComprehensionPercentage)

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
                        start = 12.dp,
                        end = 12.dp,
                        top = 28.dp,
                        bottom = 20.dp
                    )
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
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
                    val sex = if (student.sex == "F") {
                        "Female"
                    } else "Male"
                    Text(
                        text = sex,
                        modifier = Modifier,
                        color = ProjectColors.OffWhite4,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "View Student Info",
                        modifier = Modifier,
                        color = ProjectColors.OffWhite4,
                        fontSize = 14.sp.nonScaledSp,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.TwoTone.OpenInNew,
                        contentDescription = "View Student Info",
                        modifier = Modifier
                            .size(20.dp),
                        tint = ProjectColors.OffWhite4
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .background(color = ProjectColors.OffWhite4)
                .weight(1F)
                .fillMaxSize()
                .verticalScroll(state = scrollState)
        ) {
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
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.TwoTone.Edit,
                        contentDescription = "Click to Edit Group Screening Test Score",
                        modifier = Modifier.size(20.dp),
                        tint = ProjectColors.OffWhite4
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Click to Edit",
                        modifier = Modifier,
                        color = ProjectColors.OffWhite4,
                        fontSize = 14.sp.nonScaledSp,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            }
            val gstScore = student.groupScreeningTest.score
            val gstComprehensionLevel = calculateComprehensionLevel(
                score = gstScore
            )
            Text(
                text = "$gstScore (Level - ${gstComprehensionLevel.toComprehensionLevelString()})",
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.TwoTone.Edit,
                            contentDescription = "Click to Edit Oral Reading",
                            modifier = Modifier.size(20.dp),
                            tint = ProjectColors.OffWhite4
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Click to Edit",
                            modifier = Modifier,
                            color = ProjectColors.OffWhite4,
                            fontSize = 14.sp.nonScaledSp,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
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
                        text = "No. Of Miscues: ${student.oralReading?.numberOfMiscues ?: -1}",
                        modifier = Modifier,
                        color = Color.Black,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Percentage: $oralReadingPercentage",
                        modifier = Modifier,
                        color = Color.Black,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Learner Level: ${oralReadingLearnerLevel.toLearnerLevelString()}",
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.TwoTone.Edit,
                            contentDescription = "Click to Edit Reading Comprehension",
                            modifier = Modifier.size(20.dp),
                            tint = ProjectColors.OffWhite4
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Click to Edit",
                            modifier = Modifier,
                            color = ProjectColors.OffWhite4,
                            fontSize = 14.sp.nonScaledSp,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
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
                        text = "Learner Level: ${readingComprehensionLearnerLevel.toLearnerLevelString()}",
                        modifier = Modifier,
                        color = Color.Black,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column (
                modifier = Modifier
                    .background(color = ProjectColors.OffYellow1)
                    .padding(vertical = 24.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "READING PROFILE",
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
            Row(
                modifier = Modifier
                    .padding(
                        horizontal = 12.dp,
                        vertical = 8.dp
                    ),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
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
        student = (dummyStudentListsEightAmethyst.students[15] as StudentList.StudentDetails).student
    )
}