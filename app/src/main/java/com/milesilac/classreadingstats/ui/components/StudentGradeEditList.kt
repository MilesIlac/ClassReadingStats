package com.milesilac.classreadingstats.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.milesilac.classreadingstats.helpers.isPositiveInteger
import com.milesilac.classreadingstats.helpers.removeExtraZeroes
import com.milesilac.classreadingstats.ui.theme.ProjectColors

@Composable
fun StudentGradeEditList(
    gradeEditType: GradeEditType,
    modifier: Modifier,
    gstScoreIntString: String = ""
) {
    when (gradeEditType) {
        GradeEditType.GST -> {
            EditListGST(
                modifier = modifier,
                gstScoreIntString = gstScoreIntString
            )
        }
        GradeEditType.OR -> {
            EditListOR(
                modifier = modifier,
                gstScoreIntString = gstScoreIntString
            )
        }
        GradeEditType.RC -> {
            EditListRC(
                modifier = modifier,
                gstScoreIntString = gstScoreIntString
            )
        }
    }
}

enum class GradeEditType(val typeLabel: String) {
    GST("Group Screening Test"),
    OR("Oral Reading"),
    RC("Reading Comprehension"),
}

@Composable
fun EditListGST(
    modifier: Modifier = Modifier,
    gstScoreIntString: String = ""
) {
    val focusManager = LocalFocusManager.current
    var inputGST by remember { mutableStateOf(gstScoreIntString) }
    Column(
        modifier = modifier
            .background(color = ProjectColors.OffWhite4)
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = inputGST,
            onValueChange = { newValue ->
                if (newValue.isPositiveInteger()) {
                    inputGST = newValue.removeExtraZeroes()
                }
            },
            modifier = Modifier,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions {
                focusManager.moveFocus(FocusDirection.Next)
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedContainerColor = ProjectColors.OffWhite4,
                unfocusedContainerColor = ProjectColors.OffWhite4,
                cursorColor = Color.Black,
                focusedBorderColor = ProjectColors.OffViolet1,
                unfocusedBorderColor = ProjectColors.OffViolet1,
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "SCORE",
            modifier = Modifier,
            color = ProjectColors.OffViolet2,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
fun EditListGSTPreview() {
    EditListGST()
}

@Composable
fun EditListOR(
    modifier: Modifier = Modifier,
    gstScoreIntString: String = ""
) {
    val focusManager = LocalFocusManager.current
    var inputTotalWords by remember { mutableStateOf(gstScoreIntString) }
    var inputTotalMiscues by remember { mutableStateOf(gstScoreIntString) }
    Row(
        modifier = modifier
            .background(color = ProjectColors.OffWhite4)
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1F),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = inputTotalWords,
                onValueChange = { newValue ->
                    if (newValue.isPositiveInteger()) {
                        inputTotalWords = newValue.removeExtraZeroes()
                    }
                },
                modifier = Modifier,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions {
                    focusManager.moveFocus(FocusDirection.Next)
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = ProjectColors.OffWhite4,
                    unfocusedContainerColor = ProjectColors.OffWhite4,
                    cursorColor = Color.Black,
                    focusedBorderColor = ProjectColors.OffOrange3,
                    unfocusedBorderColor = ProjectColors.OffOrange3,
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "TOTAL WORDS",
                modifier = Modifier,
                color = ProjectColors.OffOrange1,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .weight(1F),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = inputTotalMiscues,
                onValueChange = { newValue ->
                    if (newValue.isPositiveInteger()) {
                        inputTotalMiscues = newValue.removeExtraZeroes()
                    }
                },
                modifier = Modifier,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions {
                    focusManager.moveFocus(FocusDirection.Next)
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = ProjectColors.OffWhite4,
                    unfocusedContainerColor = ProjectColors.OffWhite4,
                    cursorColor = Color.Black,
                    focusedBorderColor = ProjectColors.OffOrange3,
                    unfocusedBorderColor = ProjectColors.OffOrange3,
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "MISCUES",
                modifier = Modifier,
                color = ProjectColors.OffOrange1,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview
@Composable
fun EditListORPreview() {
    EditListOR()
}

@Composable
fun EditListRC(
    modifier: Modifier = Modifier,
    gstScoreIntString: String = ""
) {
    val focusManager = LocalFocusManager.current
    var inputGST by remember { mutableStateOf(gstScoreIntString) }
    Column(
        modifier = modifier
            .background(color = ProjectColors.OffWhite4)
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = inputGST,
            onValueChange = { newValue ->
                if (newValue.isPositiveInteger()) {
                    inputGST = newValue.removeExtraZeroes()
                }
            },
            modifier = Modifier,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions {
                focusManager.moveFocus(FocusDirection.Next)
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedContainerColor = ProjectColors.OffWhite4,
                unfocusedContainerColor = ProjectColors.OffWhite4,
                cursorColor = Color.Black,
                focusedBorderColor = ProjectColors.OffAquaGreen1,
                unfocusedBorderColor = ProjectColors.OffAquaGreen1,
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "PERCENT",
            modifier = Modifier,
            color = ProjectColors.OffGreen2,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
fun EditListRCPreview() {
    EditListRC()
}