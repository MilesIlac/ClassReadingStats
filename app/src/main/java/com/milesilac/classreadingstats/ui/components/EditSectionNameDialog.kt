package com.milesilac.classreadingstats.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.milesilac.classreadingstats.helpers.capitalizeMaybeWithTrim
import com.milesilac.classreadingstats.helpers.isAllLetters
import com.milesilac.classreadingstats.ui.theme.ProjectColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditSectionNameDialog(
    currentSectionName: String = "",
    onDismissDialog: () -> Unit = {},
    onOkayClick: (String) -> Unit = {},
) {
    BasicAlertDialog(
        onDismissRequest = {
            onDismissDialog()
        }
    ) {
        EditSectionNameDialogLayout(
            currentSectionName = currentSectionName,
            onOkayClick = onOkayClick
        )
    }
}

@Preview
@Composable
fun EditSectionNameDialogPreview() {
    EditSectionNameDialog()
}

@Composable
fun EditSectionNameDialogLayout(
    currentSectionName: String = "",
    onOkayClick: (String) -> Unit = {},
) {
    var inputName by remember { mutableStateOf(currentSectionName) }

    Column(
        modifier = Modifier
            .background(
                color = ProjectColors.OffViolet2,
                shape = RoundedCornerShape(24.dp)
            )
            .clip(shape = RoundedCornerShape(24.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Edit Section Name",
            color = ProjectColors.OffWhite4
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = inputName,
            onValueChange = { newValue ->
                if (newValue.isAllLetters()) {
                    inputName = newValue.capitalizeMaybeWithTrim()
                }
            },
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                Text(
                    text = "Section Name"
                )
            },
            placeholder = {
                Text(
                    text = "Input Section Name...",
                    maxLines = 1
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = ProjectColors.OffWhite4,
                unfocusedTextColor = ProjectColors.OffWhite4,
                focusedContainerColor = ProjectColors.OffViolet1,
                unfocusedContainerColor = ProjectColors.OffViolet1,
                focusedBorderColor = ProjectColors.OffWhite4,
                unfocusedBorderColor = ProjectColors.OffWhite4,
                focusedLabelColor = ProjectColors.OffWhite4,
                unfocusedLabelColor = ProjectColors.OffWhite4,
                focusedPlaceholderColor = ProjectColors.OffWhite4,
                unfocusedPlaceholderColor = ProjectColors.OffWhite4,
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedButton(
            onClick = {
                onOkayClick(inputName.trim())
            },
            modifier = Modifier,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonColors(
                containerColor = ProjectColors.OffViolet1,
                contentColor = ProjectColors.OffWhite4,
                disabledContainerColor = ProjectColors.OffViolet1,
                disabledContentColor = ProjectColors.OffWhite4
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
fun EditSectionNameDialogLayoutPreview() {
    EditSectionNameDialogLayout()
}