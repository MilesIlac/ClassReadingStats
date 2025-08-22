package com.milesilac.classreadingstats.activity

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.core.view.WindowCompat
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.milesilac.classreadingstats.model.Student
import com.milesilac.classreadingstats.service.exportNewFileToExcel
import com.milesilac.classreadingstats.ui.dummySections
import com.milesilac.classreadingstats.ui.dummyStudentListsEightAmethyst
import com.milesilac.classreadingstats.ui.dummyStudentListsEightDiamond
import com.milesilac.classreadingstats.ui.screens.HomePage
import com.milesilac.classreadingstats.ui.screens.StudentDetailEditPage
import com.milesilac.classreadingstats.ui.screens.StudentDetailsPage

class MainActivity : AppCompatActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = false
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightNavigationBars = true
        window.isNavigationBarContrastEnforced = false
        setContent {
            val currentSections = dummySections
            val currentSheets = listOf(dummyStudentListsEightAmethyst, dummyStudentListsEightDiamond)
            val backStack = rememberNavBackStack(RouteHome)

            NavDisplay(
                backStack = backStack,
                onBack = { backStack.removeLastOrNull() },
                entryProvider = entryProvider {
                    entry<RouteHome> {
                        HomePage(
                            currentSections = currentSections,
                            currentSheets = currentSheets,
                            onStudentEntryClick = { student ->
                                backStack.add(RouteStudentDetails(student))
                            },
                            onExportClick = { currentSheetLists ->
                                exportNewFileToExcel(
                                    contentResolver = contentResolver,
                                    classBook = currentSheetLists,
                                    onToast = { toastText ->
                                        this@MainActivity.run {
                                            Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                )
                            },
                            onVisible = {
                                if (WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightNavigationBars) {
                                    WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightNavigationBars = false
                                }
                            }
                        )
                    }
                    entry<RouteStudentDetails> { key ->
                        StudentDetailsPage(
                            student = key.student,
                            onEditClick = { backStack.add(RouteStudentDetailEdit(key.student)) },
                            onBackClick = { backStack.removeLastOrNull() },
                            onDelete = { student ->

                            },
                            onVisible = {
                                if (WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightNavigationBars.not()) {
                                    WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightNavigationBars = true
                                }
                            }
                        )
                    }
                    entry<RouteStudentDetailEdit> { key ->
                        StudentDetailEditPage(
                            student = key.student,
                            classSections = currentSections,
                            onSaveClick = { editedStudent ->

                            },
                            onBackClick = { backStack.removeLastOrNull() },
                            onVisible = {
                                if (WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightNavigationBars.not()) {
                                    WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightNavigationBars = true
                                }
                            }
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