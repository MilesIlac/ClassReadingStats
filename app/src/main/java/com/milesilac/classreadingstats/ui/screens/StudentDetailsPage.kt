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
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.milesilac.classreadingstats.model.Student
import com.milesilac.classreadingstats.model.calculateComprehensionLevel
import com.milesilac.classreadingstats.model.calculateLearnerOralReading
import com.milesilac.classreadingstats.model.calculateLearnerReadingComprehension
import com.milesilac.classreadingstats.model.shouldGradePassage
import com.milesilac.classreadingstats.model.toComprehensionLevelString
import com.milesilac.classreadingstats.model.toLearnerLevelString
import com.milesilac.classreadingstats.ui.dummyStudentListsEightAmethyst

@Composable
fun StudentDetailsPage(
    student: Student
) {
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .padding(12.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .background(color = Color.White)
                .weight(1F)
                .fillMaxSize()
        ) {
            Text(
                text = student.name.trim(),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                color = Color.Black,
                fontSize = 28.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = student.section.trim(),
                    modifier = Modifier,
                    color = Color.Black,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "|",
                    modifier = Modifier,
                    color = Color.Black,
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
                    color = Color.Black,
                    fontSize = 16.sp,
                )
            }
            Spacer(modifier = Modifier.height(28.dp))
            Text(
                text = "Group Screening Test Score",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                color = Color.Black,
                fontSize = 24.sp,
            )
            Spacer(modifier = Modifier.height(8.dp))
            val gstScore = student.groupScreeningTest.score
            val gstComprehensionLevel = calculateComprehensionLevel(
                score = gstScore
            )
            Text(
                text = "$gstScore (Level - ${gstComprehensionLevel.toComprehensionLevelString()})",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                color = Color.Black,
                fontSize = 20.sp,
            )
            if (student.shouldGradePassage()) {
                Spacer(modifier = Modifier.height(28.dp))
                Text(
                    text = "Oral Reading",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    color = Color.Black,
                    fontSize = 24.sp,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "No. Of Miscues: ${student.oralReading?.numberOfMiscues ?: -1}",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    color = Color.Black,
                    fontSize = 20.sp,
                )
                val oralReadingPercentage = student.oralReading?.percentage ?: -1F
                val oralReadingLearnerLevel = calculateLearnerOralReading(percentage = oralReadingPercentage)
                Text(
                    text = "Percentage: $oralReadingPercentage",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    color = Color.Black,
                    fontSize = 20.sp,
                )
                Text(
                    text = "Learner Level: ${oralReadingLearnerLevel.toLearnerLevelString()}",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    color = Color.Black,
                    fontSize = 20.sp,
                )
                Spacer(modifier = Modifier.height(28.dp))
                Text(
                    text = "Reading Comprehension",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    color = Color.Black,
                    fontSize = 24.sp,
                )
                Spacer(modifier = Modifier.height(8.dp))
                val readingComprehensionPercentage = student.readingComprehension?.inputPercentage ?: -1F
                val readingComprehensionLearnerLevel = calculateLearnerReadingComprehension(percentage = readingComprehensionPercentage)
                Text(
                    text = "Percentage: $readingComprehensionPercentage",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    color = Color.Black,
                    fontSize = 20.sp,
                )
                Text(
                    text = "Learner Level: ${readingComprehensionLearnerLevel.toLearnerLevelString()}",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    color = Color.Black,
                    fontSize = 20.sp,
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                if (student.shouldGradePassage()) {
                    OutlinedButton(
                        onClick = {},
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
                                contentDescription = "Edit Oral Reading",
                                tint = Color.Black
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Edit Oral Reading",
                                modifier = Modifier,
                                color = Color.Black,
                            )
                        }
                    }
                    OutlinedButton(
                        onClick = {},
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
                                contentDescription = "Edit Reading Compre",
                                tint = Color.Black
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Edit Reading Compre",
                                modifier = Modifier,
                                color = Color.Black,
                            )
                        }
                    }
                }
            }
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedButton(
                    onClick = {},
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
                            contentDescription = "Edit GST",
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Edit GST",
                            modifier = Modifier,
                            color = Color.Black,
                        )
                    }
                }
                OutlinedButton(
                    onClick = {},
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
        student = dummyStudentListsEightAmethyst.students[0].second[14]
    )
}