package com.milesilac.classreadingstats.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Edit
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.milesilac.classreadingstats.model.ClassSection
import com.milesilac.classreadingstats.model.ClassSheet
import com.milesilac.classreadingstats.model.Student
import com.milesilac.classreadingstats.ui.components.HomePageBottomSheet
import com.milesilac.classreadingstats.ui.components.TopInfoBar
import com.milesilac.classreadingstats.ui.dummySections
import com.milesilac.classreadingstats.ui.dummyStudentListsEightAmethyst
import com.milesilac.classreadingstats.ui.dummyStudentListsEightDiamond
import com.milesilac.classreadingstats.ui.theme.ProjectColors
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    currentSections: List<ClassSection> = listOf(),
    currentSheets: List<ClassSheet> = listOf(),
    onAddSectionClick: () -> Unit = {},
    onStudentEntryClick: (Student) -> Unit = {},
    onExportClick: (List<ClassSheet>) -> Unit = {},
    bottomSheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    ),
    onVisible: () -> Unit = {}
) {
    onVisible()
    val pagerState = rememberPagerState(pageCount = { currentSheets.size })
    var currentSection by remember {
        mutableStateOf(
            "${currentSheets[0].classSection.gradeLevel.grade} - ${currentSheets[0].classSection.sectionName}"
        )
    }
    var showBottomSheet by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    // Listen for page settling
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .distinctUntilChanged()
            .collect { page ->
                // Trigger your side-effect here
                if (page == pagerState.targetPage) {
                    currentSection = "${currentSheets[page].classSection.gradeLevel.grade} - ${currentSheets[page].classSection.sectionName}"
                }
            }
    }

    Column(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(ProjectColors.OffGreen1, ProjectColors.OffGreen1)
                )
            )
            .systemBarsPadding()
            .fillMaxSize()
    ) {
        TopInfoBar(
            section = currentSection,
            onExportClick = { onExportClick(currentSheets) }
        )
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .weight(1F),
            beyondViewportPageCount = 2
        ) { page ->
            // Our page content
            StudentListPage(
                maleStudents = currentSheets[page].maleStudents,
                femaleStudents = currentSheets[page].femaleStudents,
                onStudentEntryClick = onStudentEntryClick
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = ProjectColors.OffGreen1),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = {},
                modifier = Modifier
                    .padding(horizontal = 6.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonColors(
                    containerColor = Color.Yellow,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.Yellow,
                    disabledContentColor = Color.Black
                ),
                border = BorderStroke(
                    width = 4.dp,
                    color = ProjectColors.OffOrange1
                )
            ) {
                Text(
                    text = "${currentSections[0].gradeLevel.grade}",
                    modifier = Modifier,
                )
            }
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)
                    .padding(vertical = 2.dp),
                state = rememberLazyListState(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                itemsIndexed(items = currentSections) { index, section ->
                    //val selected = TODO
                    OutlinedButton(
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(page = index)
                            }
                        },
                        modifier = Modifier,
                        colors = ButtonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black,
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.White
                        )
                    ) {
                        Text(
                            text = section.sectionName,
                            modifier = Modifier,
                            color = Color.Black,
                        )
                    }
                }
            }
        }
        Surface(
            onClick = { showBottomSheet = true },
            modifier = Modifier
                .semantics { role = Role.Button }
                .fillMaxWidth()
                .height(80.dp),
            shape = RectangleShape,
            color = ProjectColors.OffGreen3,
            contentColor = ProjectColors.OffWhite4, //Color.Black,
            interactionSource = remember { MutableInteractionSource() }
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.TwoTone.Edit,
                    contentDescription = "Manage",
                )
                Text(
                    text = "Manage",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
    if (showBottomSheet) {
        HomePageBottomSheet(
            bottomSheetState = bottomSheetState,
            onDismiss = { showBottomSheet = false },
            onAddSectionClick = {
                showBottomSheet = false
                onAddSectionClick()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun HomePagePreview() {
    HomePage(
        currentSections = dummySections,
        currentSheets = listOf(dummyStudentListsEightAmethyst, dummyStudentListsEightDiamond)
    )
}