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
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.milesilac.classreadingstats.model.StudentToDeleteBundle
import com.milesilac.classreadingstats.model.hasStudents
import com.milesilac.classreadingstats.model.initClassSection
import com.milesilac.classreadingstats.model.prepareStudentsToDelete
import com.milesilac.classreadingstats.model.toSectionString
import com.milesilac.classreadingstats.ui.components.AnimatedBottomBar
import com.milesilac.classreadingstats.ui.components.ConfirmDeleteListBottomSheet
import com.milesilac.classreadingstats.ui.components.HomePageBottomSheet
import com.milesilac.classreadingstats.ui.components.HomePageGradeLevelMenu
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
    studentsToDelete: List<Student> = listOf(),
    onUpdateStudentsToDelete: (UpdateHomePageStudentsToDelete) -> Unit = {},
    onAddSectionClick: () -> Unit = {},
    onAddStudentClick: () -> Unit = {},
    onDeleteSectionsClick: () -> Unit = {},
    onDeleteStudentsClick: (List<StudentToDeleteBundle>) -> Unit = {},
    onEditGradesClick: (Long) -> Unit = {},
    onStudentEntryClick: (Boolean, Student) -> Unit = { _,_ -> },
    onExportClick: (List<ClassSheet>) -> Unit = {},
    bottomSheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    ),
    onVisible: () -> Unit = {}
) {
    onVisible()
    var currentGradeLevel by rememberSaveable(currentSheets) {
        mutableStateOf(
            runCatching {
                currentSections[0].gradeLevel
            }.getOrElse {
                initClassSection().gradeLevel
            }
        )
    }
    var currentSheetsByGrade by remember(currentSheets) {
        mutableStateOf(
            currentSheets.filter {
                it.classSection.gradeLevel == currentGradeLevel
            }
        )
    }
    val pagerState = rememberPagerState(pageCount = { currentSheetsByGrade.size })
    val currentSectionsByGrade = currentSheetsByGrade.map { it.classSection }
    var currentSection by remember(currentSectionsByGrade) {
        mutableStateOf(
            runCatching {
                currentSectionsByGrade[0]
            }.getOrElse {
                initClassSection()
            }
        )
    }
//    println("classInits homePager currentSheets size preLaunchEffect ${currentSheets.size}")
//    println("classInits homePager currentSection $currentSection")
    var showGradeLevelMenu by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }
    var isDeleteMode by rememberSaveable { mutableStateOf(false) }
    var showDeleteStudentsBottomSheet by remember { mutableStateOf(false) }
    val studentIdsToDelete = studentsToDelete.map { it.persistenceId }
//    println("classInits studentIdsToDelete $studentIdsToDelete")
    val studentBundlesToDelete = studentsToDelete.prepareStudentsToDelete()
    val coroutineScope = rememberCoroutineScope()

    // Listen for page settling
    LaunchedEffect(currentSheetsByGrade) {
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
                        currentSheetsByGrade[page].classSection
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
            onExportClick = {
                showGradeLevelMenu = false
                onExportClick(currentSheets)
            }
        )
        if (currentSheetsByGrade.isNotEmpty()) {
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
                    maleStudents = currentSheetsByGrade[page].maleStudents,
                    femaleStudents = currentSheetsByGrade[page].femaleStudents,
                    onStudentEntryClick = { student ->
                        showGradeLevelMenu = false
                        onStudentEntryClick(isDeleteMode, student)
                    },
                    onCheckBoxClick = { student ->
                        onUpdateStudentsToDelete(
                            UpdateHomePageStudentsToDelete.EventOneEdit(student = student)
                        )
                    },
                    onHeaderCheckBoxClick = { isChecked, students ->
                        onUpdateStudentsToDelete(
                            UpdateHomePageStudentsToDelete.EventBatchEdit(
                                isChecked = isChecked,
                                students = students
                            )
                        )
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
            HomePageGradeLevelMenu(
                isExpanded = showGradeLevelMenu,
                buttonText = when {
                    currentSections.isNotEmpty() -> "${currentGradeLevel.grade}"
                    else -> "-"
                },
                isButtonEnabled = currentSections.isNotEmpty(),
                nonEmptyGrades = buildSet {
                    for (currentSection in currentSections) {
                        add(currentSection.gradeLevel)
                    }
                },
                currentGradeLevel = currentGradeLevel,
                onMenuVisibility = { showGradeLevelMenu = showGradeLevelMenu.not() },
                onPick = {
                    currentGradeLevel = it
                    currentSheetsByGrade = currentSheets.filter { sheet ->
                        sheet.classSection.gradeLevel == it
                    }
                    showGradeLevelMenu = false
                }
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)
                    .padding(vertical = 2.dp),
                state = rememberLazyListState(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                itemsIndexed(items = currentSectionsByGrade) { index, section ->
                    val selected = section == currentSection
                    OutlinedButton(
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(page = index)
                            }
                        },
                        modifier = Modifier,
                        enabled = selected.not(),
                        colors = ButtonColors(
                            containerColor = ProjectColors.OffWhite1,
                            contentColor = Color.Black,
                            disabledContainerColor = Color.Yellow,
                            disabledContentColor = Color.White
                        ),
                        border = BorderStroke(
                            width = when {
                                selected -> 4.0.dp
                                else -> 2.0.dp
                            },
                            color = when {
                                selected -> ProjectColors.OffOrange1
                                else -> Color.Gray
                            }
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
            onManageClick = {
                showGradeLevelMenu = false
                showBottomSheet = true
            },
            onDeleteClick = {
                showGradeLevelMenu = false
                showDeleteStudentsBottomSheet = true
            },
            onBackClick = {
                isDeleteMode = false
                onUpdateStudentsToDelete(UpdateHomePageStudentsToDelete.EventReset)
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
                showGradeLevelMenu = false
                showBottomSheet = false
                onAddSectionClick()
            },
            onAddStudentClick = {
                showGradeLevelMenu = false
                showBottomSheet = false
                onAddStudentClick()
            },
            onDeleteSectionsClick = {
                showGradeLevelMenu = false
                showBottomSheet = false
                onDeleteSectionsClick()
            },
            onDeleteStudentsClick = {
                showGradeLevelMenu = false
                showBottomSheet = false
                isDeleteMode = true
            },
            onEditGradesClick = {
                showGradeLevelMenu = false
                showBottomSheet = false
                onEditGradesClick(currentSection.persistenceId)
            }
        )
    }
    if (showDeleteStudentsBottomSheet) {
        ConfirmDeleteListBottomSheet(
            bottomSheetState = bottomSheetState,
            listToDelete = studentsToDelete
                .map { student ->
                    Pair(
                        student.section.toSectionString(isSpaced = true),
                        student.name
                    )
                },
            onDismiss = { showDeleteStudentsBottomSheet = false },
            onDeleteClick = {
                showDeleteStudentsBottomSheet = false
                isDeleteMode = false
                onDeleteStudentsClick(studentBundlesToDelete)
                onUpdateStudentsToDelete(UpdateHomePageStudentsToDelete.EventReset)
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

sealed class UpdateHomePageStudentsToDelete {
    data object EventReset : UpdateHomePageStudentsToDelete()
    data class EventOneEdit(val student: Student) : UpdateHomePageStudentsToDelete()
    data class EventBatchEdit(
        val isChecked: Boolean,
        val students: List<Student>
    ) : UpdateHomePageStudentsToDelete()
}