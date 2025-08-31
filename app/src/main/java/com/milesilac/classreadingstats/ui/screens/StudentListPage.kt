package com.milesilac.classreadingstats.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.milesilac.classreadingstats.model.Student
import com.milesilac.classreadingstats.model.StudentList
import com.milesilac.classreadingstats.model.StudentSexOrient
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
    studentsToDelete: List<Student> = listOf(),
    maleStudents: List<StudentList>,
    femaleStudents: List<StudentList>,
    onStudentEntryClick: (Student) -> Unit = {},
    onCheckBoxClick: (Student) -> Unit = {},
    onHeaderCheckBoxClick: (Boolean, List<Student>) -> Unit = { _,_ -> },
) {
    var maleList by remember { mutableStateOf(maleStudents) }
    var femaleList by remember { mutableStateOf(femaleStudents) }
    val maleCount = maleStudents.count { it is StudentList.StudentDetails && it.student.sex == StudentSexOrient.MALE }
    val femaleCount = femaleStudents.count { it is StudentList.StudentDetails && it.student.sex == StudentSexOrient.FEMALE }
    val mappedMaleStudents = maleStudents.filter {
        it is StudentList.StudentDetails
    }.map {
        (it as StudentList.StudentDetails).student
    }
    val mappedFemaleStudents = femaleStudents.filter {
        it is StudentList.StudentDetails
    }.map {
        (it as StudentList.StudentDetails).student
    }
    val maleHeaderCheckBoxChecked = when {
        mappedMaleStudents.isEmpty() -> false
        else -> studentsToDelete.containsAll(mappedMaleStudents)
    }
    val femaleHeaderCheckBoxChecked = when {
        mappedFemaleStudents.isEmpty() -> false
        else -> studentsToDelete.containsAll(mappedFemaleStudents)
    }

    val lazyListState = rememberLazyListState()
    val reorderableMaleLazyListState = rememberReorderableLazyListState(lazyListState) { from, to ->
        // can't use .index because there are other items in the list (headers, footers, etc)
        val fromIndex = maleList.indexOfFirst { it is StudentList.StudentDetails && it.listId == from.key }
        val toIndex = maleList.indexOfFirst { it is StudentList.StudentDetails && it.listId == to.key }
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
        val fromIndex = femaleList.indexOfFirst { it is StudentList.StudentDetails && it.listId == from.key }
        val toIndex = femaleList.indexOfFirst { it is StudentList.StudentDetails && it.listId == to.key }
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
                studentsToDelete = studentsToDelete,
                studentItem = studentItem,
                headerCount = maleCount,
                reorderableLazyListState = reorderableMaleLazyListState,
                onStudentEntryClick = onStudentEntryClick,
                onCheckBoxClick = onCheckBoxClick,
                onHeaderCheckBoxClick = { isChecked ->
                    onHeaderCheckBoxClick(
                        isChecked,
                        maleList.filter {
                            it is StudentList.StudentDetails
                        }.map {
                            (it as StudentList.StudentDetails).student
                        }
                    )
                }
            )
        }
        femaleList.forEach { studentItem ->
            manageItem(
                isDeleteMode = isDeleteMode,
                isHeaderCheckBoxChecked = femaleHeaderCheckBoxChecked,
                studentsToDelete = studentsToDelete,
                studentItem = studentItem,
                headerCount = femaleCount,
                reorderableLazyListState = reorderableFemaleLazyListState,
                onStudentEntryClick = onStudentEntryClick,
                onCheckBoxClick = onCheckBoxClick,
                onHeaderCheckBoxClick = { isChecked ->
                    onHeaderCheckBoxClick(
                        isChecked,
                        femaleList.filter {
                            it is StudentList.StudentDetails
                        }.map {
                            (it as StudentList.StudentDetails).student
                        }
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
    studentsToDelete: List<Student> = listOf(),
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
            item(key = studentItem.listId) {
                ReorderableItem(reorderableLazyListState, key = studentItem.listId) { isDragging ->
                    StudentEntry(
                        isDeleteMode = isDeleteMode,
                        isChecked = studentItem.student in studentsToDelete,
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