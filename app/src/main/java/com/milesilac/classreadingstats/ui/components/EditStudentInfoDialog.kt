package com.milesilac.classreadingstats.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.milesilac.classreadingstats.model.ClassSection
import com.milesilac.classreadingstats.model.StudentSexOrient
import com.milesilac.classreadingstats.model.initClassSection
import com.milesilac.classreadingstats.model.toSectionString
import com.milesilac.classreadingstats.ui.dummySections
import com.milesilac.classreadingstats.ui.theme.ProjectColors
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditStudentInfoDialog(
    studentInfoType: StudentInfoType,
    sections: List<ClassSection> = listOf(),
    currentSection: ClassSection = initClassSection(),
    currentSex: StudentSexOrient = StudentSexOrient.ERROR,
    onDismissDialog: () -> Unit = {},
    onSectionPick: (ClassSection) -> Unit = {},
    onSexOrientPick: (StudentSexOrient) -> Unit = {},
) {
    BasicAlertDialog(
        onDismissRequest = {
            onDismissDialog()
        }
    ) {
        when (studentInfoType) {
            StudentInfoType.SECTION -> {
                SectionDialog(
                    currentSection = currentSection,
                    sections = sections,
                    onPick = onSectionPick
                )
            }
            StudentInfoType.SEX_ORIENT -> {
                SexOrientDialog(
                    currentSex = currentSex,
                    onPick = onSexOrientPick
                )
            }
            StudentInfoType.ERROR -> { onDismissDialog() }
        }
    }
}

@Preview
@Composable
fun EditStudentInfoDialogPreview() {
    EditStudentInfoDialog(
        studentInfoType = StudentInfoType.SECTION,
        sections = dummySections
    )
}

enum class StudentInfoType {
    SECTION, SEX_ORIENT, ERROR
}

@Composable
fun SectionDialog(
    currentSection: ClassSection = initClassSection(),
    sections: List<ClassSection> = listOf(),
    onPick: (ClassSection) -> Unit = {}
) {
    val lazyListState = rememberLazyListState()
    var selectedOption by remember { mutableStateOf(currentSection) }
    // Listen for page settling
    LaunchedEffect(selectedOption) {
        snapshotFlow { selectedOption }
            .drop(1) //drop initial value
            .distinctUntilChanged()
            .collect { selectedSection ->
                // Trigger your side-effect here
                onPick(selectedSection)
            }
    }

    Column(
        modifier = Modifier
            .background(
                color = ProjectColors.OffBlue2,
                shape = RoundedCornerShape(24.dp)
            )
            .clip(shape = RoundedCornerShape(24.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Pick Section",
            color = ProjectColors.OffWhite4
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyColumn(
            modifier = Modifier
                .height(252.dp)
                .border(
                    width = 1.dp,
                    color = ProjectColors.OffWhite4,
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(shape = RoundedCornerShape(8.dp)),
            state = lazyListState
        ) {
            sections.forEach { section ->
                item {
                    Text(
                        text = section.toSectionString(isSpaced = true),
                        Modifier
                            .background(
                                color = when {
                                    section == selectedOption -> ProjectColors.OffBlue1
                                    else -> ProjectColors.OffBlue2
                                },
                            )
                            .clickable {
                                selectedOption = section
                            }
                            .fillMaxWidth()
                            .padding(
                                horizontal = 8.dp,
                                vertical = 16.dp
                            ),
                        color = when {
                            section == selectedOption -> ProjectColors.OffYellow2
                            else -> ProjectColors.OffWhite4
                        },
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SectionDialogPreview() {
    SectionDialog(
        sections = dummySections
    )
}

@Composable
fun SexOrientDialog(
    currentSex: StudentSexOrient,
    onPick: (StudentSexOrient) -> Unit = {}
) {
    var selectedOption by remember { mutableStateOf(currentSex) }
    // Listen for page settling
    LaunchedEffect(selectedOption) {
        snapshotFlow { selectedOption }
            .drop(1) //drop initial value
            .distinctUntilChanged()
            .collect { selectedSexOrient ->
                // Trigger your side-effect here
                onPick(selectedSexOrient)
            }
    }

    Column(
        modifier = Modifier
            .background(
                color = ProjectColors.OffBlue2,
                shape = RoundedCornerShape(24.dp)
            )
            .clip(shape = RoundedCornerShape(24.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Male or Female?",
            color = ProjectColors.OffWhite4
        )
        Spacer(modifier = Modifier.height(12.dp))
        Column(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = ProjectColors.OffWhite4,
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(shape = RoundedCornerShape(8.dp)),
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (currentSex == selectedOption),
                        onClick = { selectedOption = StudentSexOrient.MALE },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (StudentSexOrient.MALE == selectedOption),
                    onClick = null // null recommended for accessibility with screen readers
                )
                Text(
                    text = "Male",
                    modifier = Modifier
                        .padding(
                            horizontal = 8.dp,
                            vertical = 16.dp
                        ),
                    color = ProjectColors.OffWhite4
                )
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (currentSex == selectedOption),
                        onClick = { selectedOption = StudentSexOrient.FEMALE },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (StudentSexOrient.FEMALE == selectedOption),
                    onClick = null // null recommended for accessibility with screen readers
                )
                Text(
                    text = "Female",
                    modifier = Modifier
                        .padding(
                            horizontal = 8.dp,
                            vertical = 16.dp
                        ),
                    color = ProjectColors.OffWhite4
                )
            }
        }
    }
}

@Preview
@Composable
fun SexOrientDialogPreview() {
    SexOrientDialog(
        currentSex = StudentSexOrient.MALE
    )
}