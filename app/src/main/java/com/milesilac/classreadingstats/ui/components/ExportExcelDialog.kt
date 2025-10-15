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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.milesilac.classreadingstats.helpers.nonScaledSp
import com.milesilac.classreadingstats.ui.theme.ProjectColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExportExcelDialog(
    savedExcelName: String = "workbook",
    onDismissDialog: () -> Unit = {},
    onOkayClick: (String) -> Unit = {},
) {
    BasicAlertDialog(
        onDismissRequest = {
            onDismissDialog()
        }
    ) {
        ExportExcelDialogLayout(
            savedExcelName = savedExcelName,
            onOkayClick = onOkayClick
        )
    }
}

@Preview
@Composable
fun ExportExcelDialogPreview() {
    ExportExcelDialog()
}

@Composable
fun ExportExcelDialogLayout(
    savedExcelName: String = "",
    onOkayClick: (String) -> Unit = {},
) {
    var inputName by remember { mutableStateOf(savedExcelName) }
    val baseName = "_csrapp.xlsx"
    val currentPreviewName = "$inputName$baseName".trim()

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
            text = "Export Workbook",
            color = ProjectColors.OffWhite4
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputName,
                onValueChange = { newValue ->
                    inputName = newValue.trim()
                },
                modifier = Modifier
                    .weight(1F),
                label = {
                    Text(
                        text = "Excel Name"
                    )
                },
                placeholder = {
                    Text(
                        text = "Input Excel custom Name...",
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
            Text(
                text = "_csrapp.xlsx",
                color = ProjectColors.OffWhite4,
                fontSize = 14.sp.nonScaledSp,
                maxLines = 1
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Preview: $currentPreviewName",
            modifier = Modifier
                .fillMaxWidth(),
            color = ProjectColors.OffWhite4,
            fontSize = 16.sp.nonScaledSp,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedButton(
            onClick = { onOkayClick(inputName.trim()) },
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
fun ExportExcelDialogLayoutPreview() {
    ExportExcelDialogLayout(savedExcelName = "workbook")
}