package com.milesilac.classreadingstats.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Close
import androidx.compose.material.icons.twotone.DocumentScanner
import androidx.compose.material.icons.twotone.Save
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.milesilac.classreadingstats.model.ClassSheet
import com.milesilac.classreadingstats.model.OralReading
import com.milesilac.classreadingstats.model.ReadingComprehension
import com.milesilac.classreadingstats.model.ReadingTest
import com.milesilac.classreadingstats.model.StudentList
import com.milesilac.classreadingstats.model.emptyReadingTest
import com.milesilac.classreadingstats.model.initClassSheet
import com.milesilac.classreadingstats.model.toSectionString
import com.milesilac.classreadingstats.ui.components.EditStudentGradesDialog
import com.milesilac.classreadingstats.ui.components.EditStudentInfoDialog
import com.milesilac.classreadingstats.ui.components.GradeEditType
import com.milesilac.classreadingstats.ui.components.StudentGradeEditList
import com.milesilac.classreadingstats.ui.components.StudentInfoType
import com.milesilac.classreadingstats.ui.dummyStudentListsEightAmethyst
import com.milesilac.classreadingstats.ui.dummyStudentListsEightDiamond
import com.milesilac.classreadingstats.ui.theme.ProjectColors
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun EditStudentsGradesPage(
    sheets: List<ClassSheet>,
    currentSectionId: Long,
    onUpdateGrade: (UpdateTempClassSheetsForInputGrades) -> Unit = {},
    onScanClick: () -> Unit = {},
    onSaveClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onVisible: () -> Unit = {}
) {
    onVisible()
    var currentSheet by remember(sheets) {
        mutableStateOf(
            sheets.find {
                it.classSection.persistenceId == currentSectionId
            } ?: runCatching { sheets[0] }.getOrElse { initClassSheet() }
        )
    }
    var hasPostTest by rememberSaveable(currentSheet) {
        mutableStateOf(
            (currentSheet.maleStudents + currentSheet.femaleStudents).any { sList ->
                sList is StudentList.StudentDetails && sList.student.hasPostTest
            }
        )
    }
    var currentTest by rememberSaveable { mutableStateOf(TestEditType.PRETEST) }
    var currentGradeEditType by rememberSaveable { mutableStateOf(GradeEditType.GST) }
    var showPickSectionDialog by rememberSaveable { mutableStateOf(false) }
    var showPickGradeTypeDialog by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(ProjectColors.OffGreen2, ProjectColors.OffWhite4)
                )
            )
            .systemBarsPadding()
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .background(color = ProjectColors.OffGreen2)
                .clickable {
                    showPickSectionDialog = true
                }
                .padding(
                    horizontal = 12.dp,
                    vertical = 16.dp
                )
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = currentSheet.classSection.toSectionString(isSpaced = true),
                modifier = Modifier,
                color = ProjectColors.OffWhite4,
                fontSize = 28.sp,
                textAlign = TextAlign.Center
            )
        }
        Row(
            modifier = Modifier
                .background(color = ProjectColors.OffGreen1)
                .height(IntrinsicSize.Max),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .background(color = ProjectColors.OffGreen1)
                    .padding(
                        horizontal = 12.dp,
                        vertical = 8.dp
                    )
                    .weight(0.5F),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "STUDENT",
                    modifier = Modifier,
                    color = ProjectColors.OffWhite4,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
            Column(
                modifier = Modifier
                    .clickable {
                        showPickGradeTypeDialog = true
                    }
                    .weight(0.5F),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .background(
                            color = when (currentTest) {
                                TestEditType.PRETEST -> ProjectColors.OffYellow1
                                TestEditType.POSTTEST -> ProjectColors.OffBlue2
                            }
                        )
                        .padding(
                            horizontal = 12.dp,
                            vertical = 16.dp
                        )
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = currentTest.name,
                        modifier = Modifier,
                        color = when (currentTest) {
                            TestEditType.PRETEST -> Color.Black
                            TestEditType.POSTTEST -> ProjectColors.OffWhite4
                        },
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
                Column(
                    modifier = Modifier
                        .background(
                            color = when (currentGradeEditType) {
                                GradeEditType.GST -> ProjectColors.OffViolet1
                                GradeEditType.OR -> ProjectColors.OffOrange3
                                GradeEditType.RC -> ProjectColors.OffAquaGreen1
                            }
                        )
                        .padding(
                            horizontal = 12.dp,
                            vertical = 16.dp
                        )
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = when (currentGradeEditType) {
                            GradeEditType.GST -> "GST"
                            GradeEditType.OR -> "OR"
                            GradeEditType.RC -> "RC"
                        },
                        modifier = Modifier,
                        color = ProjectColors.OffWhite4,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        LazyColumn(
            modifier = Modifier
                .background(color = ProjectColors.OffWhite4)
                .weight(1F),
        ) {
            (currentSheet.maleStudents + currentSheet.femaleStudents).forEach { studentItem ->
                when (studentItem) {
                    is StudentList.Header -> {}
                    is StudentList.StudentDetails -> {
                        item {
                            var inputGST by rememberSaveable(studentItem.student) { mutableStateOf("${studentItem.student.gst.score.toInt()}") }
                            var inputORTotalWordsPreTest by rememberSaveable(studentItem.student) { mutableStateOf("${studentItem.student.preTest.oralReading.totalNumberOfWordsInSelection.toInt()}") }
                            var inputORMiscuesPreTest by rememberSaveable(studentItem.student) { mutableStateOf("${studentItem.student.preTest.oralReading.numberOfMiscues.toInt()}") }
                            var inputRCPreTest by rememberSaveable(studentItem.student) { mutableStateOf("${studentItem.student.preTest.readingComprehension.inputPercentage}") }
                            var inputORTotalWordsPostTest by rememberSaveable(studentItem.student) { mutableStateOf("${(studentItem.student.postTest?.oralReading?.totalNumberOfWordsInSelection ?: -1.0).toInt()}") }
                            var inputORMiscuesPostTest by rememberSaveable(studentItem.student) { mutableStateOf("${(studentItem.student.postTest?.oralReading?.numberOfMiscues ?: -1.0).toInt()}") }
                            var inputRCPostTest by rememberSaveable(studentItem.student) { mutableStateOf("${(studentItem.student.postTest?.readingComprehension?.inputPercentage ?: -1.0)}") }
                            val newReadingTestPre by remember {
                                derivedStateOf {
                                    ReadingTest(
                                        oralReading = OralReading(
                                            totalNumberOfWordsInSelection = runCatching { inputORTotalWordsPreTest.toDouble() }.getOrElse { -1.0 },
                                            numberOfMiscues = runCatching { inputORMiscuesPreTest.toDouble() }.getOrElse { -1.0 }
                                        ),
                                        readingComprehension = ReadingComprehension(
                                            inputPercentage = runCatching { inputRCPreTest.toDouble() }.getOrElse { -1.0 }
                                        )
                                    )
                                }
                            }
                            val newReadingTestPost by remember {
                                derivedStateOf {
                                    ReadingTest(
                                        oralReading = OralReading(
                                            totalNumberOfWordsInSelection = runCatching { inputORTotalWordsPostTest.toDouble() }.getOrElse { -1.0 },
                                            numberOfMiscues = runCatching { inputORMiscuesPostTest.toDouble() }.getOrElse { -1.0 }
                                        ),
                                        readingComprehension = ReadingComprehension(
                                            inputPercentage = runCatching { inputRCPostTest.toDouble() }.getOrElse { -1.0 }
                                        )
                                    )
                                }
                            }

                            // Listen for gst input change
                            LaunchedEffect(studentItem.student.persistenceId) {
                                snapshotFlow { inputGST }
                                    .distinctUntilChanged()
                                    .debounce(800L)
                                    .collect { gst ->
                                        // Trigger your side-effect here
                                        onUpdateGrade(
                                            UpdateTempClassSheetsForInputGrades.EventGST(
                                                sectionPersistenceId = currentSheet.classSection.persistenceId,
                                                studentPersistenceId = studentItem.student.persistenceId,
                                                gstScore = runCatching { gst.toDouble() }.getOrElse { 0.0 }
                                            )
                                        )
                                    }
                            }

                            // Listen for readingTest input change
                            LaunchedEffect(studentItem.student.persistenceId) {
                                snapshotFlow { newReadingTestPre }
                                    .distinctUntilChanged()
                                    .debounce(800L)
                                    .collect { readingTest ->
                                        // Trigger your side-effect here
                                        if (studentItem.student.preTest != readingTest) {
                                            onUpdateGrade(
                                                UpdateTempClassSheetsForInputGrades.EventReadingTest(
                                                    sectionPersistenceId = currentSheet.classSection.persistenceId,
                                                    studentPersistenceId = studentItem.student.persistenceId,
                                                    hasPostTest = studentItem.student.hasPostTest,
                                                    isPostTest = false,
                                                    newReadingTest = readingTest
                                                )
                                            )
                                        }
                                    }
                            }

                            // Listen for readingTest input change
                            LaunchedEffect(studentItem.student.persistenceId) {
                                snapshotFlow { newReadingTestPost }
                                    .distinctUntilChanged()
                                    .debounce(800L)
                                    .collect { readingTest ->
                                        // Trigger your side-effect here
                                        if (studentItem.student.postTest != readingTest || readingTest != emptyReadingTest()) {
                                            onUpdateGrade(
                                                UpdateTempClassSheetsForInputGrades.EventReadingTest(
                                                    sectionPersistenceId = currentSheet.classSection.persistenceId,
                                                    studentPersistenceId = studentItem.student.persistenceId,
                                                    hasPostTest = hasPostTest,
                                                    isPostTest = true,
                                                    newReadingTest = readingTest
                                                )
                                            )
                                        }
                                    }
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    modifier = Modifier
                                        .background(color = ProjectColors.OffWhite4)
                                        .padding(
                                            horizontal = 12.dp,
                                            vertical = 8.dp
                                        )
                                        .weight(0.5F),
                                ) {
                                    Text(
                                        text = "${studentItem.student.sex.sex} - ${studentItem.student.name.trim()}" ,
                                        modifier = Modifier,
                                        color = Color.Black,
                                        fontSize = 16.sp,
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 1
                                    )
                                }
                                StudentGradeEditList(
                                    gradeEditType = currentGradeEditType,
                                    modifier = Modifier
                                        .weight(0.5F),
                                    gstScoreIntString = when (currentTest) {
                                        TestEditType.PRETEST -> inputGST
                                        TestEditType.POSTTEST -> ""
                                    },
                                    orTotalWordsIntString = when (currentTest) {
                                        TestEditType.PRETEST -> inputORTotalWordsPreTest
                                        TestEditType.POSTTEST -> inputORTotalWordsPostTest
                                    },
                                    orTotalMiscuesIntString = when (currentTest) {
                                        TestEditType.PRETEST -> inputORMiscuesPreTest
                                        TestEditType.POSTTEST -> inputORMiscuesPostTest
                                    },
                                    rcPercentString = when (currentTest) {
                                        TestEditType.PRETEST -> inputRCPreTest
                                        TestEditType.POSTTEST -> inputRCPostTest
                                    },
                                    onGSTScoreEdit = { newInput ->
                                        when (currentTest) {
                                            TestEditType.PRETEST -> inputGST = newInput
                                            TestEditType.POSTTEST -> {}
                                        }
                                    },
                                    onORTotalWordsEdit = { newInput ->
                                        when (currentTest) {
                                            TestEditType.PRETEST -> inputORTotalWordsPreTest = newInput
                                            TestEditType.POSTTEST -> inputORTotalWordsPostTest = newInput
                                        }
                                    },
                                    onORMiscuesEdit = { newInput ->
                                        when (currentTest) {
                                            TestEditType.PRETEST -> inputORMiscuesPreTest = newInput
                                            TestEditType.POSTTEST -> inputORMiscuesPostTest = newInput
                                        }
                                    },
                                    onRCPercentEdit = { newInput ->
                                        when (currentTest) {
                                            TestEditType.PRETEST -> inputRCPreTest = newInput
                                            TestEditType.POSTTEST -> inputRCPostTest = newInput
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .background(color = ProjectColors.OffBlue2)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(
                        vertical = 8.dp
                    )
                    .fillMaxWidth(),
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
                            imageVector = Icons.TwoTone.DocumentScanner,
                            contentDescription = "Scan",
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Scan",
                            modifier = Modifier,
                            color = Color.Black,
                        )
                    }
                }
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
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Save",
                            modifier = Modifier,
                            color = Color.Black,
                        )
                    }
                }
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
                        Spacer(modifier = Modifier.width(6.dp))
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
        when {
            showPickSectionDialog -> {
                showPickGradeTypeDialog = false
                EditStudentInfoDialog(
                    studentInfoType = StudentInfoType.SECTION,
                    currentSection = currentSheet.classSection,
                    sections = sheets.map { it.classSection },
                    onDismissDialog = { showPickSectionDialog = false },
                    onSectionPick = { selected ->
                        currentSheet = sheets.find { it.classSection == selected } ?: currentSheet
                        showPickSectionDialog = false
                    },
                )
            }
            showPickGradeTypeDialog -> {
                showPickSectionDialog = false
                EditStudentGradesDialog(
                    hasPostTest = hasPostTest,
                    currentTestType = currentTest,
                    currentGradeType = currentGradeEditType,
                    onDismissDialog = { showPickGradeTypeDialog = false },
                    onOkayClick = { hasPostTestValue, selectedTest, selectedGradeType ->
                        hasPostTest = hasPostTestValue
                        currentTest = selectedTest
                        currentGradeEditType = selectedGradeType
                        showPickGradeTypeDialog = false
                    },
                )
            }
        }
    }
}

enum class TestEditType(val test: String) {
    PRETEST("PreTest"),
    POSTTEST("PostTest")
}

@Preview
@Composable
fun EditStudentsGradesPagePreview() {
    EditStudentsGradesPage(
        sheets = listOf(dummyStudentListsEightAmethyst, dummyStudentListsEightDiamond),
        currentSectionId = 111
    )
}

sealed class UpdateTempClassSheetsForInputGrades {
    data class EventGST(
        val sectionPersistenceId: Long,
        val studentPersistenceId: Long,
        val gstScore: Double
    ) : UpdateTempClassSheetsForInputGrades()
    data class EventReadingTest(
        val sectionPersistenceId: Long,
        val studentPersistenceId: Long,
        val hasPostTest: Boolean,
        val isPostTest: Boolean,
        val newReadingTest: ReadingTest
    ) : UpdateTempClassSheetsForInputGrades()
}