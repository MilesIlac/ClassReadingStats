package com.milesilac.classreadingstats.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.milesilac.classreadingstats.model.Student
import com.milesilac.classreadingstats.ui.dummyStudentListsEightAmethyst
import com.milesilac.classreadingstats.ui.screens.HomePage
import com.milesilac.classreadingstats.ui.screens.StudentDetailsPage

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var currentSheetList by remember { mutableStateOf(dummyStudentListsEightAmethyst) }

            val backStack = rememberNavBackStack(RouteHome)

            NavDisplay(
                backStack = backStack,
                onBack = { backStack.removeLastOrNull() },
                entryProvider = entryProvider {
                    entry<RouteHome> {
                        HomePage(
                            onStudentEntryClick = { student ->
                                backStack.add(RouteStudentDetails(student))
                            }
                        )
                    }
                    entry<RouteStudentDetails> { key ->
                        StudentDetailsPage(
                            student = key.student,
                            onBackClick = { backStack.removeLastOrNull() }
                        )
                    }
                }
            )



        }
    }

}

@kotlinx.serialization.Serializable
private data object RouteHome : NavKey

@kotlinx.serialization.Serializable
private data class RouteStudentDetails(val student: Student) : NavKey