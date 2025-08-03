package com.milesilac.classreadingstats.activity

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
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
import com.milesilac.classreadingstats.ui.screens.StudentDetailEditPage
import com.milesilac.classreadingstats.ui.screens.StudentDetailsPage

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.Q)
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
                            contentResolver = contentResolver,
                            onStudentEntryClick = { student ->
                                backStack.add(RouteStudentDetails(student))
                            },
                            onToast = { toastText ->
                                this@MainActivity.run {
                                    Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                    }
                    entry<RouteStudentDetails> { key ->
                        StudentDetailsPage(
                            student = key.student,
                            onEditClick = { backStack.add(RouteStudentDetailEdit(key.student)) },
                            onBackClick = { backStack.removeLastOrNull() }
                        )
                    }
                    entry<RouteStudentDetailEdit> { key ->
                        StudentDetailEditPage(
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

@kotlinx.serialization.Serializable
private data class RouteStudentDetailEdit(val student: Student) : NavKey