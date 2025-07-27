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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.milesilac.classreadingstats.model.Student
import com.milesilac.classreadingstats.model.StudentList
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
                    stickyHeader {
                        Text(
                            text = studentItem.sex.toStudentSexOrientString(),
                            Modifier
                                .animateItem()
                                .fillMaxWidth()
                                .background(color = Color.Green)
                                .padding(8.dp),
                            color = Color.Black,
                        )
                    }
                }
                is StudentList.StudentDetails -> {
                    item(key = studentItem.student.listId) {
                        ReorderableItem(reorderableLazyListState, key = studentItem.student.listId) { isDragging ->
                            StudentEntry(
                                reorderableItemScope = this,
                                isDragging = isDragging,
                                textString = "${studentItem.student.orderId} ${studentItem.student.name}",
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