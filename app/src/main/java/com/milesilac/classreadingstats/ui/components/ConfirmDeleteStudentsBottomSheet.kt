package com.milesilac.classreadingstats.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.milesilac.classreadingstats.model.Student
import com.milesilac.classreadingstats.model.StudentList
import com.milesilac.classreadingstats.model.toSectionString
import com.milesilac.classreadingstats.ui.dummyStudentListsEightAmethyst
import com.milesilac.classreadingstats.ui.theme.ProjectColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmDeleteStudentsBottomSheet(
    bottomSheetState: SheetState = rememberModalBottomSheetState(),
    studentsToDelete: List<Student> = listOf(),
    onDismiss: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = bottomSheetState,
        dragHandle = null,
        contentWindowInsets = {
            WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal)
        }
    ) {
        // Sheet content
        ConfirmDeleteStudentsBottomSheetLayout(
            studentsToDelete = studentsToDelete,
            onDeleteClick = onDeleteClick,
            onBackClick = onDismiss
        )
    }
}

@Composable
fun ConfirmDeleteStudentsBottomSheetLayout(
    studentsToDelete: List<Student> = listOf(),
    onDeleteClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    val buttonColors = ButtonColors(
        containerColor = ProjectColors.OffGreen2,
        contentColor = ProjectColors.OffWhite4,
        disabledContainerColor = ProjectColors.OffGreen2,
        disabledContentColor = ProjectColors.OffWhite4
    )

    Column(
        modifier = Modifier
            .background(color = ProjectColors.OffGreen1)
            .navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = 20.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Delete the following entries?",
            modifier = Modifier,
            color = ProjectColors.OffWhite4,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier
                .heightIn(min = 100.dp, max = 360.dp)
                .background(
                    color = ProjectColors.OffGreen2,
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    width = 1.dp,
                    color = ProjectColors.OffWhite4,
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(shape = RoundedCornerShape(8.dp))
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            studentsToDelete.forEach { student ->
                item {
                    Row(
                        modifier = Modifier

                            ,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = student.section.toSectionString(isSpaced = true),
                            Modifier
                                .weight(1F),
                            color = ProjectColors.OffWhite4,
                        )
                        Text(
                            text = student.name,
                            Modifier
                                .weight(1F),
                            color = ProjectColors.OffWhite4,
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { onDeleteClick() },
                modifier = Modifier
                    .weight(1F),
                colors = buttonColors
            ) {
                Text(text = "Delete")
            }
            Button(
                onClick = { onBackClick() },
                modifier = Modifier
                    .weight(1F),
                colors = buttonColors
            ) {
                Text(text = "Back")
            }
        }

    }
}

@Preview
@Composable
fun ConfirmDeleteStudentsBottomSheetPreview() {
    ConfirmDeleteStudentsBottomSheetLayout(
        studentsToDelete = dummyStudentListsEightAmethyst.maleStudents.filter {
            it is StudentList.StudentDetails
        }.map {
            (it as StudentList.StudentDetails).student
        }
    )
}