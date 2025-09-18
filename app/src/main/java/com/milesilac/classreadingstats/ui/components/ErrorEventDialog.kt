package com.milesilac.classreadingstats.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.milesilac.classreadingstats.ui.theme.ProjectColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorEventDialog(
    errorEvents: List<ErrorEvent> = listOf(),
    onDismissDialog: () -> Unit = {},
) {
    BasicAlertDialog(
        onDismissRequest = {
            onDismissDialog()
        }
    ) {
        ErrorEventDialogLayout(
            errorEvents = errorEvents,
            onBackClick = onDismissDialog
        )
    }
}

@Preview
@Composable
fun ErrorEventDialogPreview() {
    ErrorEventDialog()
}

@Composable
fun ErrorEventDialogLayout(
    errorEvents: List<ErrorEvent> = listOf(ErrorEvent.AddSectionSaveErrorEvent.AddSectionName()),
    onBackClick: () -> Unit = {},
) {
    val entryColor = ProjectColors.OffWhite4
    Column(
        modifier = Modifier
            .background(
                color = ProjectColors.OffRed1,
                shape = RoundedCornerShape(24.dp)
            )
            .clip(shape = RoundedCornerShape(24.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Error",
            color = ProjectColors.OffWhite4
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Unable to save due to the following reasons:",
            color = ProjectColors.OffWhite4
        )
        Spacer(modifier = Modifier.height(4.dp))
        LazyColumn(
            modifier = Modifier
                .heightIn(max = 360.dp)
                .background(
                    color = ProjectColors.OffRed2,
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
            errorEvents.forEach { error ->
                item {
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        when (error) {
                            is ErrorEvent.AddSectionSaveErrorEvent.AddGradeLevel -> {
                                Text(
                                    text = error.errorMessage,
                                    Modifier
                                        .weight(1F),
                                    color = entryColor,
                                )
                            }
                            is ErrorEvent.AddSectionSaveErrorEvent.AddSectionName -> {
                                Text(
                                    text = error.errorMessage,
                                    Modifier
                                        .weight(1F),
                                    color = entryColor,
                                )
                            }
                            is ErrorEvent.AddSectionSaveErrorEvent.ExistingSection -> {
                                Text(
                                    text = error.errorMessage,
                                    Modifier
                                        .weight(1F),
                                    color = entryColor,
                                )
                            }
                            is ErrorEvent.AddStudentSaveErrorEvent.AddStudentName -> {
                                Text(
                                    text = error.errorMessage,
                                    Modifier
                                        .weight(1F),
                                    color = entryColor,
                                )
                            }
                            is ErrorEvent.AddStudentSaveErrorEvent.PickSection -> {
                                Text(
                                    text = error.errorMessage,
                                    Modifier
                                        .weight(1F),
                                    color = entryColor,
                                )
                            }
                            is ErrorEvent.AddStudentSaveErrorEvent.PickSexOrient -> {
                                Text(
                                    text = error.errorMessage,
                                    Modifier
                                        .weight(1F),
                                    color = entryColor,
                                )
                            }
                            is ErrorEvent.AddStudentSaveErrorEvent.ExistingStudent -> {
                                Text(
                                    text = error.errorMessage,
                                    Modifier
                                        .weight(1F),
                                    color = entryColor,
                                )
                            }
                            is ErrorEvent.WhileDeleteModeEditErrorEvent -> {
                                Text(
                                    text = error.errorMessage,
                                    Modifier
                                        .weight(1F),
                                    color = entryColor,
                                )
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = { onBackClick() },
                modifier = Modifier,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonColors(
                    containerColor = ProjectColors.OffRed4,
                    contentColor = ProjectColors.OffWhite4,
                    disabledContainerColor = ProjectColors.OffOrange3,
                    disabledContentColor = ProjectColors.OffGreen4
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = ProjectColors.OffWhite4
                ),
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "BACK",
                        modifier = Modifier,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ErrorEventDialogLayoutPreview() {
    ErrorEventDialogLayout()
}

sealed class ErrorEvent {
    sealed class AddSectionSaveErrorEvent : ErrorEvent() {
        data class AddGradeLevel(
            val errorMessage: String = "Please pick a Grade Level"
        ) : AddSectionSaveErrorEvent()
        data class AddSectionName(
            val errorMessage: String = "Input Section Name is blank"
        ) : AddSectionSaveErrorEvent()
        data class ExistingSection(
            val errorMessage: String = "Input Section already exists"
        ) : AddSectionSaveErrorEvent()
    }
    sealed class AddStudentSaveErrorEvent : ErrorEvent() {
        data class AddStudentName(
            val errorMessage: String = "Input Student Name is blank"
        ) : AddStudentSaveErrorEvent()
        data class PickSection(
            val errorMessage: String = "Please pick a Section"
        ) : AddStudentSaveErrorEvent()
        data class PickSexOrient(
            val errorMessage: String = "Please pick if Student is Male or Female"
        ) : AddStudentSaveErrorEvent()
        data class ExistingStudent(
            val errorMessage: String = "Student may already exist in this Section; For conflicting names, please add a number at the end to differentiate"
        ) : AddStudentSaveErrorEvent()
    }
    data class WhileDeleteModeEditErrorEvent(
        val errorMessage: String = "You are currently deleting students. You may edit student after exiting delete mode."
    ) : ErrorEvent()
}