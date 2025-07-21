package com.milesilac.classreadingstats.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.milesilac.classreadingstats.model.Student
import com.milesilac.classreadingstats.ui.components.StudentEntry
import com.milesilac.classreadingstats.ui.dummyStudentListsEightAmethyst
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@Composable
fun StudentListPage(
    studentList: List<Student>
) {
    var list by remember { mutableStateOf(studentList) }
    val lazyListState = rememberLazyListState()
    val reorderableLazyListState = rememberReorderableLazyListState(lazyListState) { from, to ->
        var fromOrderId = list[from.index].orderId
        var toOrderId = list[to.index].orderId
        list = list.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
        when {
            from.index > to.index -> {
                for (i in to.index .. from.index) {
                    list[i].orderId = toOrderId
                    toOrderId++
                }
            }
            from.index < to.index -> {
                for (i in from.index .. to.index) {
                    list[i].orderId = fromOrderId
                    fromOrderId++
                }
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize(),
        state = lazyListState,
    ) {
        items(list, key = { it.listId }) {
            ReorderableItem(reorderableLazyListState, key = it.listId) { isDragging ->
                StudentEntry(
                    reorderableItemScope = this,
                    isDragging = isDragging,
                    textString = "${it.orderId} ${it.name}",
                    onClick = {}
                )
            }
        }
    }
}

@Preview
@Composable
fun StudentListPagePreview() {
    StudentListPage(
        studentList = dummyStudentListsEightAmethyst.students[0].second
    )
}