package com.milesilac.classreadingstats.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.milesilac.classreadingstats.helpers.inputFullCheckForDecimalString
import com.milesilac.classreadingstats.helpers.inputFullCheckForIntString
import com.milesilac.classreadingstats.model.GroupScreeningTest
import com.milesilac.classreadingstats.model.OralReading
import com.milesilac.classreadingstats.model.ReadingComprehension
import com.milesilac.classreadingstats.model.ReadingTest
import com.milesilac.classreadingstats.model.StudentList
import com.milesilac.classreadingstats.ui.dummyStudentListsEightAmethyst
import com.milesilac.classreadingstats.ui.theme.ProjectColors
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun StudentGradeEditPage(
    modifier: Modifier = Modifier,
    isPostTest: Boolean = false,
    studentPersistenceId: Long = 0, // for key
    gst: GroupScreeningTest,
    studentTest: ReadingTest,
    onUpdateGST: (Double) -> Unit = {},
    onUpdateGrade: (ReadingTest) -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    var inputGST by rememberSaveable { mutableStateOf(gst.score.toInt().toString()) }
    var inputORTotalWords by rememberSaveable { mutableStateOf(studentTest.oralReading.totalNumberOfWordsInSelection.toInt().toString()) }
    var inputORMiscues by rememberSaveable { mutableStateOf(studentTest.oralReading.numberOfMiscues.toInt().toString()) }
    var inputRC by rememberSaveable { mutableStateOf(studentTest.readingComprehension.inputPercentage.toString()) }
    val newReadingTest by remember {
        derivedStateOf {
            ReadingTest(
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = runCatching { inputORTotalWords.toDouble() }.getOrElse { -1.0 },
                    numberOfMiscues = runCatching { inputORMiscues.toDouble() }.getOrElse { -1.0 }
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = runCatching { inputRC.toDouble() }.getOrElse { -1.0 }
                )
            )
        }
    }
    val shouldGradePassage = when {
        isPostTest -> true
        else -> gst.shouldGradePassage()
    }
    val oralReadingPercentage = newReadingTest.oralReading.percentage
    val oralReadingLearnerLevel = newReadingTest.oralReading.level
    val readingComprehensionLearnerLevel = newReadingTest.readingComprehension.level

    // Listen for gst input change
    LaunchedEffect(studentPersistenceId) {
        snapshotFlow { inputGST }
            .distinctUntilChanged()
            .debounce(800L)
            .collect { newGST ->
                // Trigger your side-effect here
                val newScore = runCatching { newGST.toDouble() }.getOrElse { -1.0 }
                if (gst.score != newScore) {
                    onUpdateGST(newScore)
                }
            }
    }

    // Listen for readingTest input change
    LaunchedEffect(studentPersistenceId) {
        snapshotFlow { newReadingTest }
            .distinctUntilChanged()
            .debounce(800L)
            .collect { readingTest ->
                // Trigger your side-effect here
                if (studentTest != readingTest) {
                    onUpdateGrade(readingTest)
                }
            }
    }

    Column(
        modifier = modifier
            .background(color = ProjectColors.OffWhite4)
            .fillMaxSize()
            .verticalScroll(state = scrollState)
    ) {
        if (isPostTest.not()) {
            Column(
                modifier = Modifier
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                ProjectColors.OffViolet1,
                                ProjectColors.OffViolet1
                            ),
                        ),
                        alpha = 0.5F
                    )
                    .padding(
                        horizontal = 12.dp,
                        vertical = 8.dp
                    )
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
            ) {
                Text(
                    text = "Group Screening Test Score",
                    modifier = Modifier,
                    color = Color.Black,
                    fontSize = 16.sp,
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
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = inputGST,
                        onValueChange = { newValue ->
                            newValue.inputFullCheckForIntString(
                                currentValue = inputGST,
                                errorValue = "-1",
                                returnValue = { returnValue ->
                                    inputGST = returnValue
                                }
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1F),
                        label = {
                            Text(
                                text = "Score"
                            )
                        },
                        placeholder = {
                            Text(
                                text = "0",
                                color = Color.Gray,
                                maxLines = 1
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        keyboardActions = KeyboardActions {
                            focusManager.moveFocus(FocusDirection.Next)
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    OutlinedButton(
                        onClick = {

                        },
                        modifier = Modifier
                            .padding(top = 8.dp)
                    ) {
                        Text(
                            text = "Scan",
                            modifier = Modifier
                                .padding(
                                    vertical = 4.dp
                                ),
                            color = Color.Black,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Text(
                    text = "Comprehension Level: ${gst.comprehensionLevel.level}",
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    color = Color.Black,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
        if (shouldGradePassage) {
            Column(
                modifier = Modifier
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                ProjectColors.OffOrange3,
                                ProjectColors.OffOrange3
                            ),
                        ),
                        alpha = 0.5F
                    )
                    .padding(
                        horizontal = 12.dp,
                        vertical = 8.dp
                    )
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
            ) {
                Text(
                    text = "Oral Reading",
                    modifier = Modifier,
                    color = Color.Black,
                    fontSize = 16.sp,
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
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = inputORTotalWords,
                        onValueChange = { newValue ->
                            newValue.inputFullCheckForIntString(
                                currentValue = inputORTotalWords,
                                errorValue = "-1",
                                returnValue = { returnValue ->
                                    inputORTotalWords = returnValue
                                }
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1F),
                        label = {
                            Text(
                                text = "Total No. Of Words in Selection"
                            )
                        },
                        placeholder = {
                            Text(
                                text = "0",
                                color = Color.Gray,
                                maxLines = 1
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        keyboardActions = KeyboardActions {
                            focusManager.moveFocus(FocusDirection.Next)
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    OutlinedButton(
                        onClick = {

                        },
                        modifier = Modifier
                            .padding(top = 8.dp)
                    ) {
                        Text(
                            text = "Scan",
                            modifier = Modifier
                                .padding(
                                    vertical = 4.dp
                                ),
                            color = Color.Black,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = inputORMiscues,
                        onValueChange = { newValue ->
                            newValue.inputFullCheckForIntString(
                                currentValue = inputORMiscues,
                                errorValue = "-1",
                                returnValue = { returnValue ->
                                    inputORMiscues = returnValue
                                }
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1F),
                        label = {
                            Text(
                                text = "No. Of Miscues"
                            )
                        },
                        placeholder = {
                            Text(
                                text = "0",
                                color = Color.Gray,
                                maxLines = 1
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        keyboardActions = KeyboardActions {
                            focusManager.moveFocus(FocusDirection.Next)
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    OutlinedButton(
                        onClick = {

                        },
                        modifier = Modifier
                            .padding(top = 8.dp)
                    ) {
                        Text(
                            text = "Scan",
                            modifier = Modifier
                                .padding(
                                    vertical = 4.dp
                                ),
                            color = Color.Black,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Text(
                    text = "Percentage: %.2f".format(oralReadingPercentage),
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth(),
                    color = Color.Black,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Learner Level: ${oralReadingLearnerLevel.level}",
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color.Black,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
            Column(
                modifier = Modifier
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                ProjectColors.OffAquaGreen1,
                                ProjectColors.OffAquaGreen1
                            ),
                        ),
                        alpha = 0.5F
                    )
                    .padding(
                        horizontal = 12.dp,
                        vertical = 8.dp
                    )
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
            ) {
                Text(
                    text = "Reading Comprehension",
                    modifier = Modifier,
                    color = Color.Black,
                    fontSize = 16.sp,
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
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = inputRC,
                        onValueChange = { newValue ->
                            newValue.inputFullCheckForDecimalString(
                                currentValue = inputRC,
                                errorValue = "-1.0",
                                returnValue = { returnValue ->
                                    inputRC = returnValue
                                }
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1F),
                        label = {
                            Text(
                                text = "Percentage"
                            )
                        },
                        placeholder = {
                            Text(
                                text = "0",
                                color = Color.Gray,
                                maxLines = 1
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        ),
                        keyboardActions = KeyboardActions {
                            focusManager.moveFocus(FocusDirection.Next)
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    OutlinedButton(
                        onClick = {

                        },
                        modifier = Modifier
                            .padding(top = 8.dp)
                    ) {
                        Text(
                            text = "Scan",
                            modifier = Modifier
                                .padding(
                                    vertical = 4.dp
                                ),
                            color = Color.Black,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Text(
                    text = "Learner Level: ${readingComprehensionLearnerLevel.level}",
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth(),
                    color = Color.Black,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview
@Composable
fun StudentGradeEditPagePreview() {
    val student = (dummyStudentListsEightAmethyst.maleStudents[15] as StudentList.StudentDetails).student
    StudentGradeEditPage(
        gst = student.gst,
        studentTest = student.preTest
    )
}