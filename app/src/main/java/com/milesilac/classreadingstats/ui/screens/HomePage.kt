package com.milesilac.classreadingstats.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.milesilac.classreadingstats.model.ClassSection
import com.milesilac.classreadingstats.model.ClassSheet
import com.milesilac.classreadingstats.model.Student
import com.milesilac.classreadingstats.model.hasStudents
import com.milesilac.classreadingstats.model.initClassSection
import com.milesilac.classreadingstats.model.toSectionString
import com.milesilac.classreadingstats.ui.components.AnimatedBottomBar
import com.milesilac.classreadingstats.ui.components.ConfirmDeleteStudentsBottomSheet
import com.milesilac.classreadingstats.ui.components.HomePageBottomSheet
import com.milesilac.classreadingstats.ui.components.TopInfoBar
import com.milesilac.classreadingstats.ui.dummyStudentListsEightAmethyst
import com.milesilac.classreadingstats.ui.dummyStudentListsEightDiamond
import com.milesilac.classreadingstats.ui.theme.ProjectColors
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    currentSheets: List<ClassSheet> = listOf(),
    currentSections: List<ClassSection> = listOf(),
    onAddSectionClick: () -> Unit = {},
    onAddStudentClick: () -> Unit = {},
    onDeleteSectionsClick: () -> Unit = {},
    onDeleteStudentsClick: (List<Long>) -> Unit = {},
    onEditGradesClick: (Long) -> Unit = {},
    onStudentEntryClick: (Student) -> Unit = {},
    onExportClick: (List<ClassSheet>) -> Unit = {},
    bottomSheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    ),
    onVisible: () -> Unit = {}
) {
    onVisible()
    val pagerState = rememberPagerState(pageCount = { currentSheets.size })
    var currentSection by remember(currentSheets) {
        mutableStateOf(
            runCatching {
                currentSheets[0].classSection
            }.getOrElse {
                initClassSection()
            }
        )
    }
//    println("classInits homePager currentSheets size preLaunchEffect ${currentSheets.size}")
//    println("classInits homePager currentSection $currentSection")
    var showBottomSheet by remember { mutableStateOf(false) }
    var isDeleteMode by remember { mutableStateOf(false) }
    var showDeleteStudentsBottomSheet by remember { mutableStateOf(false) }
    var studentsToDelete by remember { mutableStateOf(listOf<Student>()) }
    val studentIdsToDelete = studentsToDelete.map { it.persistenceId }
    val coroutineScope = rememberCoroutineScope()

    // Listen for page settling
    LaunchedEffect(currentSheets) {
        snapshotFlow { pagerState.currentPage }
            .distinctUntilChanged()
            .collect { page ->
                // Trigger your side-effect here
//                println("classInits homePager currentSheets size ${currentSheets.size}")
//                println("classInits homePager page $page")
                if (page == pagerState.targetPage) {
//                    println("classInits homePager got target $page")
                    currentSection = runCatching {
//                        println("classInits homePager section ${currentSheets[page].classSection}")
                        currentSheets[page].classSection
                    }.getOrElse {
//                        println("classInits homePager section reached else")
                        initClassSection()
                    }
                    println("classInits homePager currentSection after flip $currentSection")
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
            isDeleteMode = isDeleteMode,
            section = currentSection.toSectionString(
                isSpaced = true,
                isSectionNameUpperCased = false
            ),
            onExportClick = { onExportClick(currentSheets) }
        )
        if (currentSheets.isNotEmpty()) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1F),
                beyondViewportPageCount = 2
            ) { page ->
                // Our page content
                StudentListPage(
                    isDeleteMode = isDeleteMode,
                    studentIdsToDelete = studentIdsToDelete,
                    maleStudents = currentSheets[page].maleStudents,
                    femaleStudents = currentSheets[page].femaleStudents,
                    onStudentEntryClick = onStudentEntryClick,
                    onCheckBoxClick = { student ->
                        studentsToDelete = studentsToDelete.toMutableList().apply {
                            when {
                                student in this -> remove(student)
                                else -> add(student)
                            }
                        }
                    },
                    onHeaderCheckBoxClick = { isChecked, students ->
                        studentsToDelete = studentsToDelete.toMutableList().apply {
                            when {
                                isChecked -> addAll(students)
                                else -> removeAll(students)
                            }
                        }
                    }
                )
            }
        } else {
            EmptyWorkbookPage(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1F)
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
                enabled = currentSections.isNotEmpty(),
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
                    text = when {
                        currentSections.isNotEmpty() -> "${currentSections[0].gradeLevel.grade}"
                        else -> "-"
                    },
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
        AnimatedBottomBar(
            isDeleteMode = isDeleteMode,
            isDeleteBtnEnabled = studentIdsToDelete.isNotEmpty(),
            onManageClick = { showBottomSheet = true },
            onDeleteClick = { showDeleteStudentsBottomSheet = true },
            onBackClick = {
                isDeleteMode = false
                studentsToDelete = listOf()
            }
        )
    }
    if (showBottomSheet) {
        HomePageBottomSheet(
            bottomSheetState = bottomSheetState,
            onDismiss = { showBottomSheet = false },
            hasSections = currentSections.isNotEmpty(),
            hasStudents = currentSheets.hasStudents(),
            onAddSectionClick = {
                showBottomSheet = false
                onAddSectionClick()
            },
            onAddStudentClick = {
                showBottomSheet = false
                onAddStudentClick()
            },
            onDeleteSectionsClick = {
                showBottomSheet = false
                onDeleteSectionsClick()
            },
            onDeleteStudentsClick = {
                showBottomSheet = false
                isDeleteMode = true
            },
            onEditGradesClick = {
                showBottomSheet = false
                onEditGradesClick(currentSection.persistenceId)
            }
        )
    }
    if (showDeleteStudentsBottomSheet) {
        ConfirmDeleteStudentsBottomSheet(
            bottomSheetState = bottomSheetState,
            studentsToDelete = studentsToDelete,
            onDismiss = { showDeleteStudentsBottomSheet = false },
            onDeleteClick = {
                showDeleteStudentsBottomSheet = false
                isDeleteMode = false
                onDeleteStudentsClick(studentIdsToDelete)
                studentsToDelete = listOf()
            }
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        //dialogs
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun HomePagePreview() {
    val currentSheets = listOf(dummyStudentListsEightAmethyst, dummyStudentListsEightDiamond)
    val currentSections = currentSheets.map { it.classSection }
    HomePage(
        currentSheets = currentSheets,
        currentSections = currentSections
    )
}