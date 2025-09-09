package com.milesilac.classreadingstats.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.milesilac.classreadingstats.model.ClassSheet
import com.milesilac.classreadingstats.model.StudentList
import com.milesilac.classreadingstats.model.StudentSexOrient
import com.milesilac.classreadingstats.model.initClassSheet
import com.milesilac.classreadingstats.repository.StudentsRepository
import com.milesilac.classreadingstats.ui.screens.UpdateTempClassSheet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(): ViewModel() {

    init {
        println("classInits ${this::class.simpleName} init")
    }

    private fun <T> Flow<T>.stateInWhileSubscribed(initialValue: T): StateFlow<T> {
        return stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = initialValue,
        )
    }

    private val repository = StudentsRepository.getInstance()

    private val _classSheetsState = MutableStateFlow(listOf<ClassSheet>())
    val classSheetsState = _classSheetsState
        .onStart {
            println("classInits classSheetsState: flowing")
            viewModelScope.launch {
                repository.getClassSheets().collect { classSheets -> _classSheetsState.update { classSheets } }
            }
        }
        .stateInWhileSubscribed(
            initialValue = listOf()
        )
//
//    private var tempClassSheet = initClassSheet()
//
//    private val _tempClassSheetGradeLevelState = MutableStateFlow(GradeLevel.ERROR )
//    val tempClassSheetGradeLevelState = _tempClassSheetGradeLevelState
//        .stateInWhileSubscribed(
//            initialValue = GradeLevel.ERROR
//        )
//
//    private val _tempClassSheetSectionNameState = MutableStateFlow("" )
//    val tempClassSheetSectionNameState = _tempClassSheetSectionNameState
//        .stateInWhileSubscribed(
//            initialValue = ""
//        )

    private val _tempClassSheetState = MutableStateFlow(initClassSheet())
    val tempClassSheetState = _tempClassSheetState
        .stateInWhileSubscribed(
            initialValue = initClassSheet()
        )

    fun updateTempClassSheet(event: UpdateTempClassSheet) {
        when(event) {
            UpdateTempClassSheet.EventDelete -> {
                _tempClassSheetState.update { initClassSheet() }
            }
            is UpdateTempClassSheet.EventGradeLevel -> {
                _tempClassSheetState.update { it.copy(classSection = it.classSection.copy(gradeLevel = event.gradeLevel)) }
            }
            is UpdateTempClassSheet.EventSectionName -> {
                _tempClassSheetState.update { it.copy(classSection = it.classSection.copy(sectionName = event.sectionName)) }
            }
            is UpdateTempClassSheet.EventSectionStudent -> {
                when (event.student.sex) {
                    StudentSexOrient.MALE -> {
                        _tempClassSheetState.update {
                            it.copy(
                                maleStudents = it.maleStudents.toMutableList().apply {
                                    add(StudentList.StudentDetails(student = event.student))
                                }
                            )
                        }
                    }
                    StudentSexOrient.FEMALE -> {
                        _tempClassSheetState.update {
                            it.copy(
                                femaleStudents = it.femaleStudents.toMutableList().apply {
                                    add(StudentList.StudentDetails(student = event.student))
                                }
                            )
                        }
                    }
                    StudentSexOrient.ERROR -> {}
                }
            }
        }
    }
}
