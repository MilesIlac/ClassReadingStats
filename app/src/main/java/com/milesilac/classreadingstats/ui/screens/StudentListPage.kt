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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.milesilac.classreadingstats.helpers.nonScaledSp
import com.milesilac.classreadingstats.model.Student
import com.milesilac.classreadingstats.model.StudentList
import com.milesilac.classreadingstats.model.StudentSexOrient
import com.milesilac.classreadingstats.model.mapToStudentPersistenceIds
import com.milesilac.classreadingstats.model.mapToStudents
import com.milesilac.classreadingstats.ui.components.SectionHeader
import com.milesilac.classreadingstats.ui.components.StudentEntry
import com.milesilac.classreadingstats.ui.dummyStudentListsEightAmethyst
import com.milesilac.classreadingstats.ui.theme.ProjectColors
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.ReorderableLazyListState
import sh.calvin.reorderable.rememberReorderableLazyListState

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
    var maleList by remember(maleStudents) { mutableStateOf(maleStudents) }
    var femaleList by remember(femaleStudents) { mutableStateOf(femaleStudents) }
    val maleCount = maleStudents.count { it is StudentList.StudentDetails && it.student.sex == StudentSexOrient.MALE }
    val femaleCount = femaleStudents.count { it is StudentList.StudentDetails && it.student.sex == StudentSexOrient.FEMALE }
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
    val reorderableMaleLazyListState = rememberReorderableLazyListState(lazyListState) { from, to ->
        // can't use .index because there are other items in the list (headers, footers, etc)
        val fromIndex = maleList.indexOfFirst { it is StudentList.StudentDetails && it.listItemId == from.key }
        val toIndex = maleList.indexOfFirst { it is StudentList.StudentDetails && it.listItemId == to.key }
        var fromOrderId = (maleList[fromIndex] as StudentList.StudentDetails).student.orderId
        var toOrderId = (maleList[toIndex] as StudentList.StudentDetails).student.orderId

        maleList = maleList.toMutableList().apply {
            add(toIndex, removeAt(fromIndex))
        }

        when {
            fromIndex > toIndex -> {
                for (i in toIndex .. fromIndex) {
                    if (maleList[i] is StudentList.StudentDetails) {
                        (maleList[i] as StudentList.StudentDetails).student.orderId = toOrderId
                        toOrderId++
                    }
                }
            }
            fromIndex < toIndex -> {
                for (i in fromIndex .. toIndex) {
                    if (maleList[i] is StudentList.StudentDetails) {
                        (maleList[i] as StudentList.StudentDetails).student.orderId = fromOrderId
                        fromOrderId++
                    }
                }
            }
        }
    }

    val reorderableFemaleLazyListState = rememberReorderableLazyListState(lazyListState) { from, to ->
        // can't use .index because there are other items in the list (headers, footers, etc)
        val fromIndex = femaleList.indexOfFirst { it is StudentList.StudentDetails && it.listItemId == from.key }
        val toIndex = femaleList.indexOfFirst { it is StudentList.StudentDetails && it.listItemId == to.key }
        var fromOrderId = (femaleList[fromIndex] as StudentList.StudentDetails).student.orderId
        var toOrderId = (femaleList[toIndex] as StudentList.StudentDetails).student.orderId

        femaleList = femaleList.toMutableList().apply {
            add(toIndex, removeAt(fromIndex))
        }

        when {
            fromIndex > toIndex -> {
                for (i in toIndex .. fromIndex) {
                    if (femaleList[i] is StudentList.StudentDetails) {
                        (femaleList[i] as StudentList.StudentDetails).student.orderId = toOrderId
                        toOrderId++
                    }
                }
            }
            fromIndex < toIndex -> {
                for (i in fromIndex .. toIndex) {
                    if (femaleList[i] is StudentList.StudentDetails) {
                        (femaleList[i] as StudentList.StudentDetails).student.orderId = fromOrderId
                        fromOrderId++
                    }
                }
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .background(color = ProjectColors.OffWhite4)
            .fillMaxSize(),
        state = lazyListState,
    ) {
        maleList.forEach { studentItem ->
            manageItem(
                isDeleteMode = isDeleteMode,
                isHeaderCheckBoxChecked = maleHeaderCheckBoxChecked,
                studentIdsToDelete = studentIdsToDelete,
                studentItem = studentItem,
                headerCount = maleCount,
                reorderableLazyListState = reorderableMaleLazyListState,
                onStudentEntryClick = onStudentEntryClick,
                onCheckBoxClick = onCheckBoxClick,
                onHeaderCheckBoxClick = { isChecked ->
                    onHeaderCheckBoxClick(
                        isChecked,
                        maleList.mapToStudents()
                    )
                }
            )
        }
        femaleList.forEach { studentItem ->
            manageItem(
                isDeleteMode = isDeleteMode,
                isHeaderCheckBoxChecked = femaleHeaderCheckBoxChecked,
                studentIdsToDelete = studentIdsToDelete,
                studentItem = studentItem,
                headerCount = femaleCount,
                reorderableLazyListState = reorderableFemaleLazyListState,
                onStudentEntryClick = onStudentEntryClick,
                onCheckBoxClick = onCheckBoxClick,
                onHeaderCheckBoxClick = { isChecked ->
                    onHeaderCheckBoxClick(
                        isChecked,
                        femaleList.mapToStudents()
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
    reorderableLazyListState: ReorderableLazyListState,
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
                ReorderableItem(reorderableLazyListState, key = studentItem.listItemId) { isDragging ->
                    StudentEntry(
                        isDeleteMode = isDeleteMode,
                        isChecked = studentItem.student.persistenceId in studentIdsToDelete,
                        reorderableItemScope = this,
                        isDragging = isDragging,
                        textString = "${studentItem.student.orderId.toInt()} ${studentItem.student.name}".trim(),
                        onStudentDetailsCheck = { onStudentEntryClick(studentItem.student) },
                        onCheckBoxClick = { onCheckBoxClick(studentItem.student) }
                    )
                }
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
        Text(
            text = "Click the 3-dots above to add or import an Excel workbook\nor\nStart a new one by adding a section using the Manage button below",
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