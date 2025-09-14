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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.milesilac.classreadingstats.ui.theme.ProjectColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPostTestDialog(
    onDismissDialog: () -> Unit = {},
    onOkayClick: () -> Unit = {},
) {
    BasicAlertDialog(
        onDismissRequest = {
            onDismissDialog()
        }
    ) {
        EditPostTestDialogLayout(
            onOkayClick = onOkayClick,
            onBackClick = onDismissDialog
        )
    }
}

@Preview
@Composable
fun EditPostTestDialogPreview() {
    EditPostTestDialog()
}

@Composable
fun EditPostTestDialogLayout(
    onOkayClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
) {
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
            text = "Note",
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
                .clip(shape = RoundedCornerShape(8.dp))
                .fillMaxWidth(),
        ) {
            Text(
                text = "Confirm editing of Post-Test?",
                modifier = Modifier
                    .padding(
                        horizontal = 8.dp,
                        vertical = 12.dp
                    )
                    .fillMaxWidth(),
                color = ProjectColors.OffWhite4,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = { onOkayClick() },
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
            OutlinedButton(
                onClick = { onBackClick() },
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
                        text = "Back",
                        modifier = Modifier,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun EditPostTestDialogLayoutPreview() {
    EditPostTestDialogLayout()
}