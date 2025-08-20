package com.milesilac.classreadingstats.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.milesilac.classreadingstats.model.StudentList
import com.milesilac.classreadingstats.ui.dummyStudentListsEightAmethyst
import com.milesilac.classreadingstats.ui.theme.ProjectColors
import sh.calvin.reorderable.ReorderableCollectionItemScope

@Composable
fun StudentEntry(
    reorderableItemScope: ReorderableCollectionItemScope? = null,
    isDragging: Boolean = true,
    textString: String,
    onClick: () -> Unit = {}
) {
    val elevation by animateDpAsState(if (isDragging) 4.dp else 0.dp)

    Surface(
        modifier = Modifier
            .clickable {
                onClick()
            }
            .fillMaxWidth(),
        color = ProjectColors.OffWhite4,
        shadowElevation = elevation
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = 8.dp,
                    vertical = 2.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1F)
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = textString,
                        modifier = Modifier
                            .weight(1F)
                            .padding(end = 8.dp),
                        color = Color.Black,
                        overflow = TextOverflow.Clip,
                        softWrap = false,
                        maxLines = 1
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                modifier = reorderableItemScope?.let {
                    with (it) {
                        Modifier.draggableHandle()
                    }
                } ?: Modifier,
                onClick = {},
            ) {
                Icon(
                    imageVector = Icons.TwoTone.Menu,
                    contentDescription = "Reorder",
                    tint = Color.Black
                )
            }
        }
    }
}

@Preview
@Composable
fun StudentEntryPreview() {
    val student = (dummyStudentListsEightAmethyst.maleStudents[15] as StudentList.StudentDetails).student
    val textString = "${student.orderId.toInt()} ${student.name}".trim()
    StudentEntry(
        textString = textString
    )
}