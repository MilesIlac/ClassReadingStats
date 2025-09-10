package com.milesilac.classreadingstats.activity

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.milesilac.classreadingstats.model.Student
import com.milesilac.classreadingstats.model.emptyStudent
import com.milesilac.classreadingstats.service.exportNewFileToExcel
import com.milesilac.classreadingstats.ui.screens.AddSectionPage
import com.milesilac.classreadingstats.ui.screens.DeleteSectionsPage
import com.milesilac.classreadingstats.ui.screens.EditStudentsGradesPage
import com.milesilac.classreadingstats.ui.screens.HomePage
import com.milesilac.classreadingstats.ui.screens.StudentDetailEditPage
import com.milesilac.classreadingstats.ui.screens.StudentDetailsPage
import com.milesilac.classreadingstats.ui.screens.StudentEditType
import com.milesilac.classreadingstats.ui.screens.UpdateTempClassSheet
import kotlinx.serialization.json.Json

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
            val viewModel: MainViewModel = viewModel()
            val navController = rememberNavController()

            val currentSheets by viewModel.classSheetsState.collectAsStateWithLifecycle()
            val currentSections = currentSheets.map { it.classSection }

            val tempClassSheet by viewModel.tempClassSheetState.collectAsStateWithLifecycle()
            val tempRemainingClassSheets by viewModel.tempRemainingClassSheetState.collectAsStateWithLifecycle()
            val tempDeletePendingClassSheets by viewModel.tempDeletePendingClassSheetState.collectAsStateWithLifecycle()
            val tempSelectedRemainingSheets by viewModel.tempSelectedRemainingState.collectAsStateWithLifecycle()
            val tempSelectedDeletePendingSheets by viewModel.tempSelectedDeletePendingState.collectAsStateWithLifecycle()

            NavHost(navController = navController, startDestination = Routes.RouteHome) {
                composable<Routes.RouteHome> {
                    HomePage(
                        currentSheets = currentSheets,
                        currentSections = currentSections,
                        onAddSectionClick = {
                            navController.navigate(Routes.RouteAddSection)
                        },
                        onAddStudentClick = {
                            navController.navigate(
                                Routes.RouteStudentDetailEdit(
                                    studentString = emptyStudent().toJsonString(),
                                    studentEditType = StudentEditType.ADD,
                                    isFromAddSectionPage = false
                                )
                            )
                        },
                        onDeleteSectionsClick = {
                            navController.navigate(Routes.RouteDeleteSections)
                        },
                        onEditGradesClick = { currentSectionId ->
                            navController.navigate(
                                Routes.RouteEditGrades(
                                    currentSectionId = currentSectionId
                                )
                            )
                        },
                        onStudentEntryClick = { student ->
                            navController.navigate(
                                Routes.RouteStudentDetails(
                                    studentString = student.toJsonString()
                                )
                            )
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
                composable<Routes.RouteAddSection> {
                    AddSectionPage(
                        tempClassSheet = tempClassSheet,
                        onUpdate = { viewModel.updateTempClassSheet(event = it) },
                        onBackClick = {
                            navController.navigateUp()
                            viewModel.updateTempClassSheet(event = it)
                        },
                        onAddStudentClick = { inputSection ->
                            navController.navigate(
                                Routes.RouteStudentDetailEdit(
                                    studentString = emptyStudent(section = inputSection).toJsonString(),
                                    studentEditType = StudentEditType.ADD,
                                    isFromAddSectionPage = true
                                )
                            )
                        },
                        onVisible = {
                            if (WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightNavigationBars.not()) {
                                WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightNavigationBars = true
                            }
                        }
                    )
                }
                composable<Routes.RouteDeleteSections> {
                    DeleteSectionsPage(
                        currentSheets = tempRemainingClassSheets,
                        currentDeletePendingSheets = tempDeletePendingClassSheets,
                        selectedSheets = tempSelectedRemainingSheets,
                        selectedDeletePendingSheets = tempSelectedDeletePendingSheets,
                        onDeleteSectionEvent = { viewModel.manageDeleteSectionEvent(event = it) },
                        onBackClick = {
                            navController.navigateUp()
                            viewModel.manageDeleteSectionEvent(event = it)
                        },
                        onVisible = {
                            if (WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightNavigationBars) {
                                WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightNavigationBars = false
                            }
                        }
                    )
                }
                composable<Routes.RouteEditGrades> { backStackEntry ->
                    val routeEditGrades: Routes.RouteEditGrades = backStackEntry.toRoute()
                    EditStudentsGradesPage(
                        sheets = currentSheets,
                        currentSectionId = routeEditGrades.currentSectionId,
                        onBackClick = { navController.navigateUp() },
                        onVisible = {
                            if (WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightNavigationBars.not()) {
                                WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightNavigationBars = true
                            }
                        }
                    )
                }
                composable<Routes.RouteStudentDetails> { backStackEntry ->
                    val routeStudentDetails: Routes.RouteStudentDetails = backStackEntry.toRoute()
                    val student = Json.decodeFromString<Student>(routeStudentDetails.studentString)
                    StudentDetailsPage(
                        student = student,
                        onEditClick = {
                            navController.navigate(
                                Routes.RouteStudentDetailEdit(
                                    studentString = student.toJsonString(),
                                    studentEditType = StudentEditType.EDIT,
                                    isFromAddSectionPage = false
                                )
                            )
                        },
                        onBackClick = { navController.navigateUp() },
                        onDelete = { student ->

                        },
                        onVisible = {
                            if (WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightNavigationBars.not()) {
                                WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightNavigationBars = true
                            }
                        }
                    )
                }
                composable<Routes.RouteStudentDetailEdit> { backStackEntry ->
                    val routeStudentDetailEdit: Routes.RouteStudentDetailEdit = backStackEntry.toRoute()
                    val student = Json.decodeFromString<Student>(routeStudentDetailEdit.studentString)
                    StudentDetailEditPage(
                        studentEditType = routeStudentDetailEdit.studentEditType,
                        student = student,
                        isFromAddSectionPage = routeStudentDetailEdit.isFromAddSectionPage,
                        classSections = currentSections,
                        onSaveClick = { editedStudent ->
                            if (routeStudentDetailEdit.isFromAddSectionPage) {
                                viewModel.updateTempClassSheet(
                                    event = UpdateTempClassSheet.EventSectionStudent(student = editedStudent)
                                )
                            }
                            navController.navigateUp()
                        },
                        onBackClick = { navController.navigateUp() },
                        onVisible = {
                            if (WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightNavigationBars.not()) {
                                WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightNavigationBars = true
                            }
                        }
                    )
                }
                // Add more destinations similarly.
            }
        }
    }
}

private sealed class Routes {
    @kotlinx.serialization.Serializable
    data object RouteHome : Routes()

    @kotlinx.serialization.Serializable
    data object RouteAddSection : Routes()

    @kotlinx.serialization.Serializable
    data object RouteDeleteSections : Routes()

    @kotlinx.serialization.Serializable
    data class RouteEditGrades(val currentSectionId: Int) : Routes()

    @kotlinx.serialization.Serializable
    data class RouteStudentDetails(val studentString: String) : Routes() //complex classes crash NavGraph

    @kotlinx.serialization.Serializable
    data class RouteStudentDetailEdit(
        val studentString: String,
        val studentEditType: StudentEditType,
        val isFromAddSectionPage: Boolean
    ) : Routes() //complex classes crash NavGraph
}