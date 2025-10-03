package com.milesilac.classreadingstats.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.milesilac.classreadingstats.ui.theme.ProjectColors
import kotlin.enums.enumEntries

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WarningDialog(
    event: WarningEvent = WarningEvent.PickORParameter(),
    onDismissDialog: () -> Unit = {},
    onOkayClick: (OralReadingParam) -> Unit = {},
) {
    BasicAlertDialog(
        onDismissRequest = {
            onDismissDialog()
        }
    ) {
        WarningDialogLayout(
            event = event,
            onOkayClick = onOkayClick,
            onBackClick = onDismissDialog
        )
    }
}

@Preview
@Composable
fun WarningDialogLayoutPreview() {
    WarningDialog()
}

@Composable
fun WarningDialogLayout(
    event: WarningEvent = WarningEvent.PickORParameter(),
    onOkayClick: (OralReadingParam) -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    var selectedORParam by remember { mutableStateOf(OralReadingParam.NONE) }

    val radioButtonColors = RadioButtonDefaults.colors(
        selectedColor = Color.Black,
        unselectedColor = Color.Black,
        disabledSelectedColor = Color.LightGray,
        disabledUnselectedColor = Color.LightGray
    )

    Column(
        modifier = Modifier
            .background(
                color = ProjectColors.OffOrange4,
                shape = RoundedCornerShape(24.dp)
            )
            .clip(shape = RoundedCornerShape(24.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "NOTE",
            color = ProjectColors.OffGreen4
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .background(
                    color = ProjectColors.OffOrange1,
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    width = 1.dp,
                    color = ProjectColors.OffBrown1,
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(shape = RoundedCornerShape(8.dp))
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val content = when (event) {
                is WarningEvent.ConfirmEditPostTest -> event.message
                is WarningEvent.ConfirmDeleteStudent -> event.message
                is WarningEvent.PickORParameter -> event.message
                is WarningEvent.ConfirmSaveInputGrades -> event.message
            }
            Text(
                text = content,
                modifier = Modifier
                    .padding(
                        horizontal = 8.dp,
                        vertical = 12.dp
                    )
                    .fillMaxWidth(),
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            if (event is WarningEvent.PickORParameter) {
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val params = enumEntries<OralReadingParam>().filterNot { it == OralReadingParam.NONE }
                    params.forEach { param ->
                        Row(
                            modifier = Modifier
                                .selectable(
                                    selected = (selectedORParam == param),
                                    role = Role.RadioButton,
                                    onClick = {
                                        selectedORParam = when (selectedORParam) {
                                            param -> OralReadingParam.NONE
                                            else -> param
                                        }
                                    },
                                )
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (selectedORParam == param),
                                onClick = null, // null recommended for accessibility with screen readers,
                                modifier = Modifier
                                    .padding(start = 16.dp),
                                colors = radioButtonColors
                            )
                            Text(
                                text = param.typeLabel,
                                modifier = Modifier
                                    .padding(
                                        horizontal = 8.dp,
                                        vertical = 16.dp
                                    ),
                                color = Color.Black
                            )
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
                onClick = { onOkayClick(selectedORParam) },
                modifier = Modifier,
                enabled = when (event) {
                    is WarningEvent.PickORParameter -> selectedORParam != OralReadingParam.NONE
                    else -> true
                },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonColors(
                    containerColor = ProjectColors.OffOrange3,
                    contentColor = ProjectColors.OffGreen4,
                    disabledContainerColor = ProjectColors.OffBrown1,
                    disabledContentColor = ProjectColors.OffBrown2
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = ProjectColors.OffBrown3
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
            OutlinedButton(
                onClick = { onBackClick() },
                modifier = Modifier,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonColors(
                    containerColor = ProjectColors.OffOrange3,
                    contentColor = ProjectColors.OffGreen4,
                    disabledContainerColor = ProjectColors.OffOrange3,
                    disabledContentColor = ProjectColors.OffGreen4
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = ProjectColors.OffBrown3
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
fun WarningDialogLayoutLayoutPreview() {
    WarningDialogLayout()
}

sealed class WarningEvent {
    data class ConfirmEditPostTest(
        val message: String = "Confirm editing of Post-Test?"
    ) : WarningEvent()
    data class ConfirmDeleteStudent(
        val message: String = "Confirm delete of this Student?"
    ) : WarningEvent()
    data class PickORParameter(
        val message: String = "Which Oral Reading parameter will you input?"
    ) : WarningEvent()
    data class ConfirmSaveInputGrades(
        val message: String = "Save changes? This will affect the whole workbook."
    ) : WarningEvent()
}

enum class OralReadingParam(val typeLabel: String) {
    TOTAL_WORDS("Total Number of Words"),
    TOTAL_MISCUES("Number of Miscues"),
    NONE("")
}