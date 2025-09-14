package com.milesilac.classreadingstats.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.milesilac.classreadingstats.ui.screens.TestEditType
import com.milesilac.classreadingstats.ui.theme.ProjectColors
import kotlin.enums.enumEntries

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditStudentGradesDialog(
    hasPostTest: Boolean = false,
    currentTestType: TestEditType = TestEditType.PRETEST,
    currentGradeType: GradeEditType = GradeEditType.GST,
    onDismissDialog: () -> Unit = {},
    onOkayClick: (Boolean, TestEditType, GradeEditType) -> Unit = { _,_,_ -> },
) {
    BasicAlertDialog(
        onDismissRequest = {
            onDismissDialog()
        }
    ) {
        EditStudentGradesDialogLayout(
            hasPostTest = hasPostTest,
            currentTestType = currentTestType,
            currentGradeType = currentGradeType,
            onOkayClick = onOkayClick
        )
    }
}

@Preview
@Composable
fun EditStudentGradesDialogPreview() {
    EditStudentGradesDialog()
}

@Composable
fun EditStudentGradesDialogLayout(
    hasPostTest: Boolean = false,
    currentTestType: TestEditType = TestEditType.PRETEST,
    currentGradeType: GradeEditType = GradeEditType.GST,
    onOkayClick: (Boolean, TestEditType, GradeEditType) -> Unit = { _,_,_ -> },
) {
    var hasPostTestValue by remember { mutableStateOf(hasPostTest) }
    var selectedTest by remember { mutableStateOf(currentTestType) }
    var selectedGradeType by remember { mutableStateOf(currentGradeType) }
    if (selectedTest == TestEditType.POSTTEST && selectedGradeType == GradeEditType.GST) {
        selectedGradeType = GradeEditType.OR
    }

    val radioButtonColors = RadioButtonDefaults.colors(
        selectedColor = ProjectColors.OffWhite4,
        unselectedColor = ProjectColors.OffWhite4,
        disabledSelectedColor = Color.DarkGray,
        disabledUnselectedColor = Color.DarkGray
    )

    Column(
        modifier = Modifier
            .background(
                color = ProjectColors.OffRed1,
                shape = RoundedCornerShape(24.dp)
            )
            .clip(shape = RoundedCornerShape(24.dp))
            .padding(
                horizontal = 32.dp,
                vertical = 12.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "PICK GRADE TYPE",
            color = ProjectColors.OffWhite4
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .background(
                    color = ProjectColors.OffRed2,
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    width = 1.dp,
                    color = ProjectColors.OffWhite4,
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(shape = RoundedCornerShape(8.dp)),
        ) {
            enumEntries<TestEditType>().forEach { test ->
                val isOnPostTest = test == TestEditType.POSTTEST && hasPostTestValue.not()
                val choiceEnabled = when {
                    test == TestEditType.POSTTEST -> hasPostTestValue
                    else -> true
                }
                Row(
                    Modifier
                        .selectable(
                            selected = (selectedTest == test),
                            onClick = {
                                when {
                                    isOnPostTest -> hasPostTestValue = true
                                    else -> selectedTest = test
                                }
                            },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (selectedTest == test),
                        onClick = null, // null recommended for accessibility with screen readers,
                        enabled = choiceEnabled,
                        colors = radioButtonColors
                    )
                    Text(
                        text = test.test,
                        modifier = Modifier
                            .padding(
                                horizontal = 8.dp,
                                vertical = 16.dp
                            )
                            .weight(1F),
                        color = when {
                            choiceEnabled -> ProjectColors.OffWhite4
                            else -> Color.DarkGray
                        }
                    )
                    if (isOnPostTest) {
                        Text(
                            text = "UNLOCK",
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
        Spacer(modifier = Modifier.height(12.dp))
        Column(
            modifier = Modifier
                .background(
                    color = ProjectColors.OffRed2,
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    width = 1.dp,
                    color = ProjectColors.OffWhite4,
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(shape = RoundedCornerShape(8.dp)),
        ) {
            enumEntries<GradeEditType>().forEach { gradeType ->
                val choiceEnabled = when {
                    selectedTest == TestEditType.POSTTEST -> gradeType != GradeEditType.GST
                    else -> true
                }
                Row(
                    Modifier
                        .selectable(
                            selected = (selectedGradeType == gradeType),
                            enabled = choiceEnabled,
                            role = Role.RadioButton,
                            onClick = { selectedGradeType = gradeType },
                        )
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = when {
                            choiceEnabled -> (selectedGradeType == gradeType)
                            else -> false
                        },
                        onClick = null, // null recommended for accessibility with screen readers,
                        enabled = choiceEnabled,
                        colors = radioButtonColors
                    )
                    Text(
                        text = gradeType.typeLabel,
                        modifier = Modifier
                            .padding(
                                horizontal = 8.dp,
                                vertical = 16.dp
                            ),
                        color = when {
                            choiceEnabled -> ProjectColors.OffWhite4
                            else -> Color.DarkGray
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(
            onClick = {
                onOkayClick(hasPostTestValue, selectedTest, selectedGradeType)
            },
            modifier = Modifier,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonColors(
                containerColor = ProjectColors.OffRed3,
                contentColor = ProjectColors.OffBrown1,
                disabledContainerColor = ProjectColors.OffRed3,
                disabledContentColor = ProjectColors.OffBrown1
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
                    text = "OKAY",
                    modifier = Modifier,
                )
            }
        }
    }
}

@Preview
@Composable
fun EditStudentGradesDialogLayoutPreview() {
    EditStudentGradesDialogLayout()
}