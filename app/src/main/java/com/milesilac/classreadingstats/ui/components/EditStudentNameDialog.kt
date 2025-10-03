package com.milesilac.classreadingstats.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.milesilac.classreadingstats.helpers.capitalizeMaybe
import com.milesilac.classreadingstats.helpers.checkIfAddOtherModifier
import com.milesilac.classreadingstats.helpers.inputFullCheckForStudentName
import com.milesilac.classreadingstats.ui.theme.ProjectColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditStudentNameDialog(
    currentStudentName: String = "",
    onDismissDialog: () -> Unit = {},
    onOkayClick: (String) -> Unit = {},
) {
    val names = runCatching {
        currentStudentName.split(",", limit = 2)
    }.getOrElse { listOf("","") }
    val lastName = names.getOrNull(0) ?: ""
    val firstNameEtc = names.getOrNull(1) ?: ""

    BasicAlertDialog(
        onDismissRequest = {
            onDismissDialog()
        }
    ) {
        EditStudentNameDialogLayout(
            lastName = lastName,
            firstNameEtc = firstNameEtc,
            onOkayClick = onOkayClick
        )
    }
}

@Preview
@Composable
fun EditStudentNameDialogPreview() {
    EditStudentNameDialog()
}

@Composable
fun EditStudentNameDialogLayout(
    isScanMode: Boolean = false,
    lastName: String = "",
    firstNameEtc: String = "",
    onOkayClick: (String) -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    var isLocked by rememberSaveable { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = ProjectColors.OffWhite4,
        unfocusedTextColor = ProjectColors.OffWhite4,
        disabledTextColor = ProjectColors.OffWhite4,
        focusedContainerColor = ProjectColors.OffAquaGreen1,
        unfocusedContainerColor = ProjectColors.OffAquaGreen1,
        disabledContainerColor = ProjectColors.OffAquaGreen1,
        focusedBorderColor = ProjectColors.OffWhite4,
        unfocusedBorderColor = ProjectColors.OffWhite4,
        disabledBorderColor = ProjectColors.OffWhite4,
        focusedLabelColor = ProjectColors.OffWhite4,
        unfocusedLabelColor = ProjectColors.OffWhite4,
        disabledLabelColor = ProjectColors.OffWhite4,
        focusedPlaceholderColor = ProjectColors.OffWhite4,
        unfocusedPlaceholderColor = ProjectColors.OffWhite4,
        disabledPlaceholderColor = ProjectColors.OffWhite4,
    )
    val buttonColors = ButtonColors(
        containerColor = ProjectColors.OffAquaGreen1,
        contentColor = ProjectColors.OffWhite4,
        disabledContainerColor = ProjectColors.OffGreen4,
        disabledContentColor = ProjectColors.OffGreen2
    )

    var inputLastName by remember(
        when {
            isScanMode -> {
                when {
                    isLocked -> true
                    else -> lastName
                }
            }
            else -> true
        }
    ) { mutableStateOf(lastName) }
    var inputFirstNameEtc by remember(
        when {
            isScanMode -> {
                when {
                    isLocked -> true
                    else -> firstNameEtc
                }
            }
            else -> true
        }
    ) { mutableStateOf(firstNameEtc) }
    val currentPreviewName = "${inputLastName.trim()}, ${inputFirstNameEtc.trim()}".inputFullCheckForStudentName()

    Column(
        modifier = Modifier
            .background(
                color = ProjectColors.OffGreen2,
                shape = RoundedCornerShape(24.dp)
            )
            .clip(shape = RoundedCornerShape(24.dp))
            .checkIfAddOtherModifier(
                shouldAddOtherModifier = isScanMode,
                otherModifier = Modifier.height(360.dp)
            )
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = when {
            isScanMode -> Arrangement.Center
            else -> Arrangement.Top
        }
    ) {
        Text(
            text = "Edit Student Name",
            color = ProjectColors.OffWhite4
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = inputLastName,
            onValueChange = { newValue ->
                inputLastName = newValue.capitalizeMaybe()
            },
            modifier = Modifier
                .fillMaxWidth(),
            enabled = isScanMode.not(),
            label = {
                Text(
                    text = "Last Name"
                )
            },
            placeholder = {
                Text(
                    text = "Last Name",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions {
                focusManager.moveFocus(FocusDirection.Next)
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = textFieldColors
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = inputFirstNameEtc,
            onValueChange = { newValue ->
                inputFirstNameEtc = newValue.capitalizeMaybe()
            },
            modifier = Modifier
                .fillMaxWidth(),
            enabled = isScanMode.not(),
            label = {
                Text(
                    text = "First Name, etc..."
                )
            },
            placeholder = {
                Text(
                    text = "First Name, Middle Name, Suffix (optional)",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions {
                focusManager.moveFocus(FocusDirection.Next)
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = textFieldColors
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Preview: $currentPreviewName",
            modifier = Modifier
                .fillMaxWidth()
                .checkIfAddOtherModifier(
                    shouldAddOtherModifier = isScanMode,
                    otherModifier = Modifier.weight(1F)
                ),
            color = ProjectColors.OffWhite4,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isScanMode) {
                OutlinedButton(
                    onClick = { isLocked = isLocked.not() },
                    modifier = Modifier,
                    shape = RoundedCornerShape(12.dp),
                    colors = buttonColors,
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
                            text = when {
                                isLocked -> "UNLOCK"
                                else -> "LOCK"
                            },
                            modifier = Modifier,
                        )
                    }
                }
            }
            OutlinedButton(
                onClick = { onOkayClick(currentPreviewName) },
                modifier = Modifier,
                enabled = inputLastName.trim().isNotEmpty() && inputFirstNameEtc.trim().isNotEmpty(),
                shape = RoundedCornerShape(12.dp),
                colors = buttonColors,
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
            if (isScanMode) {
                OutlinedButton(
                    onClick = { onBackClick() },
                    modifier = Modifier,
                    shape = RoundedCornerShape(12.dp),
                    colors = buttonColors,
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
}

@Preview
@Composable
fun EditStudentNameDialogLayoutPreview() {
    EditStudentNameDialogLayout(
        isScanMode = true
    )
}