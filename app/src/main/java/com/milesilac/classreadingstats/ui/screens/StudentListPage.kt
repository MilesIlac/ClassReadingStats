package com.milesilac.classreadingstats.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.milesilac.classreadingstats.model.Student
import com.milesilac.classreadingstats.model.StudentList
import com.milesilac.classreadingstats.model.StudentSexOrient
import com.milesilac.classreadingstats.model.toStudentSexOrientString
import com.milesilac.classreadingstats.ui.components.StudentEntry
import com.milesilac.classreadingstats.ui.dummyStudentListsEightAmethyst
import com.milesilac.classreadingstats.ui.theme.ProjectColors
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@Composable
fun StudentListPage(
    studentList: List<StudentList>,
    onStudentEntryClick: (Student) -> Unit = {}
) {
    var list by remember { mutableStateOf(studentList) }
    val maleCount = list.count { it is StudentList.StudentDetails && it.student.sex == StudentSexOrient.MALE.toStudentSexOrientString() }
    val femaleCount = list.count { it is StudentList.StudentDetails && it.student.sex == StudentSexOrient.FEMALE.toStudentSexOrientString() }
    val lazyListState = rememberLazyListState()
    val reorderableLazyListState = rememberReorderableLazyListState(lazyListState) { from, to ->
        // can't use .index because there are other items in the list (headers, footers, etc)
        val fromIndex = list.indexOfFirst { it is StudentList.StudentDetails && it.student.listId == from.key }
        val toIndex = list.indexOfFirst {  it is StudentList.StudentDetails && it.student.listId == to.key }

        var fromOrderId = (list[fromIndex] as StudentList.StudentDetails).student.orderId
        var toOrderId = (list[toIndex] as StudentList.StudentDetails).student.orderId
        list = list.toMutableList().apply {
            add(toIndex, removeAt(fromIndex))
        }
        when {
            fromIndex > toIndex -> {
                for (i in toIndex .. fromIndex) {
                    if (list[i] is StudentList.StudentDetails) {
                        (list[i] as StudentList.StudentDetails).student.orderId = toOrderId
                        toOrderId++
                    }
                }
            }
            fromIndex < toIndex -> {
                for (i in fromIndex .. toIndex) {
                    if (list[i] is StudentList.StudentDetails) {
                        (list[i] as StudentList.StudentDetails).student.orderId = fromOrderId
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
        list.forEach { studentItem ->
            when (studentItem) {
                is StudentList.Header -> {
                    val headerText = if (studentItem.sex.toStudentSexOrientString() == StudentSexOrient.MALE.toStudentSexOrientString()) {
                        "${studentItem.sex.toStudentSexOrientString()} - $maleCount"
                    } else {
                        "${studentItem.sex.toStudentSexOrientString()} - $femaleCount"
                    }
                    stickyHeader {
                        Text(
                            text = headerText,
                            Modifier
                                .animateItem()
                                .fillMaxWidth()
                                .background(color = ProjectColors.OffGreen1)
                                .padding(
                                    horizontal = 8.dp,
                                    vertical = 16.dp
                                ),
                            color = ProjectColors.OffWhite4,
                            fontSize = 16.sp
                        )
                    }
                }
                is StudentList.StudentDetails -> {
                    item(key = studentItem.student.listId) {
                        ReorderableItem(reorderableLazyListState, key = studentItem.student.listId) { isDragging ->
                            StudentEntry(
                                reorderableItemScope = this,
                                isDragging = isDragging,
                                student = studentItem.student,
                                onClick = { onStudentEntryClick(studentItem.student) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun StudentListPagePreview() {
    StudentListPage(
        studentList = dummyStudentListsEightAmethyst.students
    )
}