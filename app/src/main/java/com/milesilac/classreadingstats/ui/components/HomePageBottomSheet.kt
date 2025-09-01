package com.milesilac.classreadingstats.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.milesilac.classreadingstats.ui.theme.ProjectColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageBottomSheet(
    bottomSheetState: SheetState = rememberModalBottomSheetState(),
    onDismiss: () -> Unit = {},
    hasSections: Boolean = false,
    hasStudents: Boolean = false,
    onAddSectionClick: () -> Unit = {},
    onAddStudentClick: () -> Unit = {},
    onDeleteSectionsClick: () -> Unit = {},
    onDeleteStudentsClick: () -> Unit = {},
//    onEditStudentInfoClick: () -> Unit = {},
    onEditGradesClick: () -> Unit = {},
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
        HomePageBottomSheetLayout(
            hasSections = hasSections,
            hasStudents = hasStudents,
            onAddSectionClick = onAddSectionClick,
            onAddStudentClick = onAddStudentClick,
            onDeleteSectionsClick = onDeleteSectionsClick,
            onDeleteStudentsClick = onDeleteStudentsClick,
//            onEditStudentInfoClick = onEditStudentInfoClick,
            onEditGradesClick = onEditGradesClick,
            onBackClick = onDismiss
        )
    }
}

@Composable
fun HomePageBottomSheetLayout(
    hasSections: Boolean = false,
    hasStudents: Boolean = false,
    onAddSectionClick: () -> Unit = {},
    onAddStudentClick: () -> Unit = {},
    onDeleteSectionsClick: () -> Unit = {},
    onDeleteStudentsClick: () -> Unit = {},
//    onEditStudentInfoClick: () -> Unit = {},
    onEditGradesClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    val buttonOptions = setOf(
        "Add Section" to onAddSectionClick,
        "Add Student" to onAddStudentClick,
        "Delete Sections" to onDeleteSectionsClick,
        "Delete Students" to onDeleteStudentsClick,
//        "Edit Students' Information" to onEditStudentInfoClick,
        "Edit Input Grades" to onEditGradesClick,
    )
    val buttonColors = ButtonColors(
        containerColor = ProjectColors.OffGreen2,
        contentColor = ProjectColors.OffWhite4,
        disabledContainerColor = ProjectColors.OffGreen4,
        disabledContentColor = Color.DarkGray
    )

    Column(
        modifier = Modifier
            .background(color = ProjectColors.OffGreen1)
            .navigationBarsPadding()
            .padding(horizontal = 52.dp, vertical = 20.dp)
            .fillMaxWidth(),

        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            buttonOptions.forEach { buttonOption ->
                Button(
                    onClick = { buttonOption.second() },
                    modifier = Modifier
                        .fillMaxWidth(),
                    enabled = when (buttonOption.first) {
                        "Delete Sections" -> hasSections
                        "Delete Students", "Edit Input Grades" -> hasStudents
                        else -> true
                    },
                    colors = buttonColors
                ) {
                    Text(text = buttonOption.first)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onBackClick() },
            modifier = Modifier
                .fillMaxWidth(),
            colors = buttonColors
        ) {
            Text(text = "Back")
        }
    }
}

@Preview
@Composable
fun HomePageBottomSheetPreview() {
    HomePageBottomSheetLayout()
}