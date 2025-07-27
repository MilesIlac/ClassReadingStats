package com.milesilac.classreadingstats.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.milesilac.classreadingstats.model.Student
import com.milesilac.classreadingstats.model.toGradeLevelInt
import com.milesilac.classreadingstats.ui.components.BottomNavBar
import com.milesilac.classreadingstats.ui.components.TopInfoBar
import com.milesilac.classreadingstats.ui.dummySections
import com.milesilac.classreadingstats.ui.dummyStudentListsEightAmethyst
import com.milesilac.classreadingstats.ui.dummyStudentListsEightDiamond
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@Composable
fun HomePage(
    onStudentEntryClick: (Student) -> Unit = {}
) {
    val currentSheetLists = listOf(dummyStudentListsEightAmethyst, dummyStudentListsEightDiamond)
    val pagerState = rememberPagerState(pageCount = { currentSheetLists.size })

    var currentSection by remember {
        mutableStateOf(
            "${currentSheetLists[0].classSection.gradeLevel.toGradeLevelInt()} - ${currentSheetLists[0].classSection.sectionName}"
        )
    }
    val coroutineScope = rememberCoroutineScope()

    // Listen for page settling
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.settledPage }
            .distinctUntilChanged()
            .collect { page ->
                // Trigger your side-effect here
                println("Pager settled at page: $page") // Replace with your action
                currentSection = "${currentSheetLists[page].classSection.gradeLevel.toGradeLevelInt()} - ${currentSheetLists[page].classSection.sectionName}"
            }
    }

    Column(
        modifier = Modifier
            .background(color = Color.Red)
            .fillMaxSize()
    ) {
        TopInfoBar(
            section = currentSection
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
                studentList = currentSheetLists[page].students,
                onStudentEntryClick = onStudentEntryClick
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Green)
        ) {
            Box(
                modifier = Modifier
                    .background(color = Color.Yellow)
                    .padding(ButtonDefaults.ContentPadding)
                    .align(Alignment.CenterVertically)
                    .clickable {

                    },
            ) {
                Text(
                    text = "${dummySections[0].gradeLevel.toGradeLevelInt()}",
                    modifier = Modifier
                        .align(Alignment.Center),
                    color = Color.Black,
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)
                    .padding(vertical = 2.dp),
                state = rememberLazyListState(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                itemsIndexed(items = dummySections) { index, section ->
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
        BottomNavBar()
    }
}

@Preview
@Composable
fun HomePagePreview() {
    HomePage()
}