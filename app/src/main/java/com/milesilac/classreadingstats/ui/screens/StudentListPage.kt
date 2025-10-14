package com.milesilac.classreadingstats.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.milesilac.classreadingstats.helpers.nonScaledSp
import com.milesilac.classreadingstats.model.Student
import com.milesilac.classreadingstats.model.StudentList
import com.milesilac.classreadingstats.model.level.LearnerLevel
import com.milesilac.classreadingstats.model.level.calculateLearnerOverallReadingProfile
import com.milesilac.classreadingstats.model.mapToStudentPersistenceIds
import com.milesilac.classreadingstats.model.mapToStudents
import com.milesilac.classreadingstats.ui.components.SectionHeader
import com.milesilac.classreadingstats.ui.components.StudentEntry
import com.milesilac.classreadingstats.ui.dummyStudentListsEightAmethyst
import com.milesilac.classreadingstats.ui.theme.ProjectColors

@Composable
fun StudentListPage(
    isDeleteMode: Boolean = false,
    studentIdsToDelete: List<Long> = listOf(),
    maleStudents: List<StudentList>,
    femaleStudents: List<StudentList>,
    onStudentEntryClick: (Student) -> Unit = {},
    onCheckBoxClick: (Student) -> Unit = {},
    onHeaderCheckBoxClick: (Boolean, List<Student>) -> Unit = { _,_ -> },
) {
    val mappedMaleStudents = maleStudents.mapToStudentPersistenceIds()
    val mappedFemaleStudents = femaleStudents.mapToStudentPersistenceIds()
//    println("classInits mappedMaleStudents ${mappedMaleStudents}")
//    println("classInits mappedFemaleStudents ${mappedFemaleStudents}")
//    println("classInits allMaleIds in delete ${studentIdsToDelete.containsAll(mappedMaleStudents)}")
//    println("classInits allFemaleIds in delete ${studentIdsToDelete.containsAll(mappedFemaleStudents)}")
    val maleHeaderCheckBoxChecked = when {
        mappedMaleStudents.isEmpty() -> false
        else -> studentIdsToDelete.containsAll(mappedMaleStudents)
    }
    val femaleHeaderCheckBoxChecked = when {
        mappedFemaleStudents.isEmpty() -> false
        else -> studentIdsToDelete.containsAll(mappedFemaleStudents)
    }

    val lazyListState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .background(color = ProjectColors.OffWhite4)
            .fillMaxSize(),
        state = lazyListState,
    ) {
        maleStudents.forEach { studentItem ->
            manageItem(
                isDeleteMode = isDeleteMode,
                isHeaderCheckBoxChecked = maleHeaderCheckBoxChecked,
                studentIdsToDelete = studentIdsToDelete,
                studentItem = studentItem,
                headerCount = mappedMaleStudents.size,
                onStudentEntryClick = onStudentEntryClick,
                onCheckBoxClick = onCheckBoxClick,
                onHeaderCheckBoxClick = { isChecked ->
                    onHeaderCheckBoxClick(
                        isChecked,
                        maleStudents.mapToStudents()
                    )
                }
            )
        }
        femaleStudents.forEach { studentItem ->
            manageItem(
                isDeleteMode = isDeleteMode,
                isHeaderCheckBoxChecked = femaleHeaderCheckBoxChecked,
                studentIdsToDelete = studentIdsToDelete,
                studentItem = studentItem,
                headerCount = mappedFemaleStudents.size,
                onStudentEntryClick = onStudentEntryClick,
                onCheckBoxClick = onCheckBoxClick,
                onHeaderCheckBoxClick = { isChecked ->
                    onHeaderCheckBoxClick(
                        isChecked,
                        femaleStudents.mapToStudents()
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun StudentListPagePreview() {
    StudentListPage(
        maleStudents = dummyStudentListsEightAmethyst.maleStudents,
        femaleStudents = dummyStudentListsEightAmethyst.femaleStudents
    )
}

private fun LazyListScope.manageItem(
    isDeleteMode: Boolean = false,
    isHeaderCheckBoxChecked: Boolean = false,
    studentIdsToDelete: List<Long> = listOf(),
    studentItem: StudentList,
    headerCount: Int,
    onStudentEntryClick: (Student) -> Unit = {},
    onCheckBoxClick: (Student) -> Unit = {},
    onHeaderCheckBoxClick: (Boolean) -> Unit = {},
) {
    when (studentItem) {
        is StudentList.Header -> {
            val headerText = "${studentItem.sex.sex} - $headerCount"
            stickyHeader {
                SectionHeader(
                    modifier = Modifier
                        .animateItem(),
                    isDeleteMode = isDeleteMode,
                    isChecked = isHeaderCheckBoxChecked,
                    textString = headerText,
                    onCheck = onHeaderCheckBoxClick
                )
            }
        }
        is StudentList.StudentDetails -> {
            item(key = studentItem.listItemId) {
                StudentEntry(
                    isDeleteMode = isDeleteMode,
                    isChecked = studentItem.student.persistenceId in studentIdsToDelete,
                    textString = "${studentItem.student.orderId.toInt()} ${studentItem.student.name}".trim(),
                    learnerLevels = studentItem.student.collectReadingProfiles(),
                    onStudentDetailsCheck = { onStudentEntryClick(studentItem.student) },
                    onCheckBoxClick = { onCheckBoxClick(studentItem.student) }
                )
            }
        }
    }
}

@Composable
fun EmptyWorkbookPage(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(color = ProjectColors.OffGreen1)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
//        val messageTest = "Click the 3-dots above to add or import an Excel workbook\nor\nStart a new one by adding a section using the Manage button below"
        val message = "Start an Excel workbook by adding a section using the Manage button below"
        Text(
            text = message,
            modifier = Modifier
                .padding(32.dp),
            color = ProjectColors.OffWhite4,
            fontSize = 24.sp.nonScaledSp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun EmptyWorkbookPagePreview() {
    EmptyWorkbookPage()
}

fun Student.collectReadingProfiles(): List<LearnerLevel> {
    val studentPreTestOverallReadingProfile = calculateLearnerOverallReadingProfile(
        isGSTPassed = this.gst.shouldGradePassage().not(),
        orLevel = this.preTest.oralReading.level,
        rcLevel = this.preTest.readingComprehension.level
    )
    val studentPostTestOverallReadingProfile = calculateLearnerOverallReadingProfile(
        orLevel = this.postTest?.oralReading?.level ?: LearnerLevel.ERROR,
        rcLevel = this.postTest?.readingComprehension?.level ?: LearnerLevel.ERROR
    )
    return listOf(
        studentPreTestOverallReadingProfile,
        studentPostTestOverallReadingProfile
    )
}