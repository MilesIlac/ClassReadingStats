package com.milesilac.classreadingstats.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.milesilac.classreadingstats.helpers.chooseOneModifier
import com.milesilac.classreadingstats.helpers.nonScaledSp
import com.milesilac.classreadingstats.model.StudentList
import com.milesilac.classreadingstats.model.level.LearnerLevel
import com.milesilac.classreadingstats.ui.dummyStudentListsEightAmethyst
import com.milesilac.classreadingstats.ui.theme.ProjectColors
import sh.calvin.reorderable.ReorderableCollectionItemScope
import kotlin.enums.enumEntries

@Composable
fun StudentEntry(
    isDeleteMode: Boolean = false,
    isChecked: Boolean = false,
    reorderableItemScope: ReorderableCollectionItemScope? = null,
    isDragging: Boolean = true,
    textString: String,
    learnerLevels: List<LearnerLevel> = listOf(),
    onStudentDetailsCheck: () -> Unit = {},
    onCheckBoxClick: () -> Unit = {},
) {
    val elevation by animateDpAsState(if (isDragging) 4.dp else 0.dp)

    Surface(
        modifier = Modifier
            .chooseOneModifier(
                chooseFirst = isDeleteMode,
                firstModifier = Modifier.combinedClickable(
                    onClick = {
                        onCheckBoxClick()
                    },
                    onLongClick = { onStudentDetailsCheck() }
                ),
                secondModifier = Modifier.clickable { onStudentDetailsCheck() }
            )
            .fillMaxWidth(),
        color = ProjectColors.OffWhite4,
//        border = BorderStroke(1.dp, ProjectColors.OffRed4), //to test listItem height
        shadowElevation = elevation
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = 8.dp,
                    vertical = 2.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1F)
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = textString,
                        modifier = Modifier
                            .weight(1F),
                        color = Color.Black,
                        overflow = TextOverflow.Clip,
                        softWrap = false,
                        maxLines = 1
                    )
                }
            }
            when {
                isDeleteMode -> {
                    Spacer(modifier = Modifier.width(8.dp))
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = null,
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .size(40.dp),
                        colors = CheckboxDefaults.colors(
                            checkedColor = ProjectColors.OffRed1,
                            uncheckedColor = ProjectColors.OffGreen1,
                            checkmarkColor = ProjectColors.OffWhite4
                        )
                    )
                }
                else -> {
                    val hasReadingProfile = learnerLevels.any { it != LearnerLevel.ERROR }
                    if (hasReadingProfile) {
                        Spacer(modifier = Modifier.width(8.dp))
                        ReadingProfileMarkers(learnerLevels = learnerLevels)
                    } else {
                        Spacer(modifier = Modifier.height(48.dp))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun StudentEntryPreview() {
    val student = (dummyStudentListsEightAmethyst.maleStudents[15] as StudentList.StudentDetails).student
    val textString = "${student.orderId.toInt()} ${student.name}".trim()
    StudentEntry(
        textString = textString
    )
}

@Composable
fun SectionHeader(
    modifier: Modifier = Modifier,
    isDeleteMode: Boolean = false,
    isChecked: Boolean = false,
    textString: String,
    onCheck: (Boolean) -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = ProjectColors.OffWhite4
    ) {
        Row(
            modifier = modifier
                .background(color = ProjectColors.OffGreen1)
                .padding(
                    horizontal = 8.dp,
                    vertical = 2.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1F)
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = textString,
                        color = ProjectColors.OffWhite4,
                        fontSize = 16.sp
                    )
                }
            }
            Checkbox(
                checked = isChecked,
                onCheckedChange = onCheck,
                modifier = Modifier
                    .minimumInteractiveComponentSize()
                    .size(40.dp),
                enabled = isDeleteMode,
                colors = CheckboxDefaults.colors(
                    checkedColor = ProjectColors.OffWhite4,
                    uncheckedColor = ProjectColors.OffWhite4,
                    checkmarkColor = ProjectColors.OffGreen1,
                    disabledCheckedColor = Color.Transparent,
                    disabledUncheckedColor = Color.Transparent
                )
            )
        }
    }
}

@Preview
@Composable
fun SectionHeaderPreview() {
    SectionHeader(
        textString = "M - 100"
    )
}

@Composable
fun ReadingProfileMarkers(
    learnerLevels: List<LearnerLevel> = listOf(LearnerLevel.FRUSTRATION, LearnerLevel.FRUSTRATION)
) {
    Surface(
        modifier = Modifier
            .minimumInteractiveComponentSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            learnerLevels.map { it.name.toMarkerDesign() }.forEach { design ->
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(vertical = 6.dp)
                        .background(
                            color = design.backgroundColor,
                            shape = RoundedCornerShape(9.dp)
                        )
                        .clip(
                            shape = RoundedCornerShape(9.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = design.label,
                        color = design.textColor,
                        fontSize = 16.sp.nonScaledSp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ReadingProfileMarkerPreview() {
    Row {
        enumEntries<LearnerLevel>().forEach {
            ReadingProfileMarkers(learnerLevels = listOf(it,it))
        }
    }
}

enum class MarkerDesign(
    val label: String,
    val backgroundColor: Color,
    val textColor: Color
) {
    FRUSTRATION(
        label = "FRU",
        backgroundColor = ProjectColors.OffRed4,
        textColor = ProjectColors.OffWhite1
    ),
    INDEPENDENT(
        label = "IND",
        backgroundColor = ProjectColors.OffGreen1,
        textColor = ProjectColors.OffWhite1
    ),
    INSTRUCTIONAL(
        label = "INS",
        backgroundColor = ProjectColors.OffYellow1,
        textColor = Color.Black
    ),
    ERROR(
        label = "",
        backgroundColor = Color.LightGray,
        textColor = Color.Black
    )
}

fun String.toMarkerDesign(): MarkerDesign {
    val value = this.uppercase()
    return enumEntries<MarkerDesign>().find {
        it.name.uppercase() == value
    } ?: MarkerDesign.ERROR
}