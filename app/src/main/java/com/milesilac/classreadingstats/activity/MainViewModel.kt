package com.milesilac.classreadingstats.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.milesilac.classreadingstats.model.ClassSheet
import com.milesilac.classreadingstats.model.Student
import com.milesilac.classreadingstats.model.StudentList
import com.milesilac.classreadingstats.model.StudentSexOrient
import com.milesilac.classreadingstats.model.emptyStudent
import com.milesilac.classreadingstats.model.initClassSheet
import com.milesilac.classreadingstats.repository.StudentsRepository
import com.milesilac.classreadingstats.ui.screens.DeleteClassSheet
import com.milesilac.classreadingstats.ui.screens.UpdateTempClassSheetForAddSection
import com.milesilac.classreadingstats.ui.screens.UpdateTempClassSheetsForInputGrades
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(): ViewModel() {

    init {
        println("classInits ${this::class.simpleName} init")
    }

    var getPersistenceStudentJob: Job? = null

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
            viewModelScope.launch {
                repository.getClassSheets().collect { classSheets ->
//                    println("classInits classSheetsState: flowing")
                    _classSheetsState.update { classSheets }
                }
            }
        }
        .stateInWhileSubscribed(
            initialValue = listOf()
        )

    private val _tempClassSheetState = MutableStateFlow(initClassSheet())
    val tempClassSheetState = _tempClassSheetState
        .stateInWhileSubscribed(
            initialValue = initClassSheet()
        )

    fun updateTempClassSheet(event: UpdateTempClassSheetForAddSection) {
        when(event) {
            UpdateTempClassSheetForAddSection.EventDelete -> {
                _tempClassSheetState.update { initClassSheet() }
            }
            is UpdateTempClassSheetForAddSection.EventGradeLevel -> {
                _tempClassSheetState.update { it.copy(classSection = it.classSection.copy(gradeLevel = event.gradeLevel)) }
            }
            is UpdateTempClassSheetForAddSection.EventSectionName -> {
                _tempClassSheetState.update { it.copy(classSection = it.classSection.copy(sectionName = event.sectionName)) }
            }
            is UpdateTempClassSheetForAddSection.EventSectionStudent -> {
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

    fun saveClassSheet() {
        viewModelScope.launch {
            repository.saveClassSheets(classSheets = listOf(_tempClassSheetState.value))
        }.invokeOnCompletion {
            _tempClassSheetState.update { initClassSheet() }
        }
    }

    private val _localStudentState = MutableStateFlow(emptyStudent())
    val localStudentState = _localStudentState
        .stateInWhileSubscribed(
            initialValue = emptyStudent()
        )

    fun getLocalSourceStudent(studentPersistenceId: Long) {
        getPersistenceStudentJob?.cancel()
        getPersistenceStudentJob = repository.getStudent(studentId = studentPersistenceId)
            .onEach { student ->
                _localStudentState.update { student }
            }
            .launchIn(viewModelScope)
    }

    fun updateLocalSourceStudent(student: Student) {
        viewModelScope.launch {
            repository.updateStudent(student = student)
        }.invokeOnCompletion {
            getLocalSourceStudent(studentPersistenceId = student.persistenceId)
        }
    }

    private val _tempStudentState = MutableStateFlow(emptyStudent())
    val tempStudentState = _tempStudentState
        .stateInWhileSubscribed(
            initialValue = emptyStudent()
        )

    fun updateTempStudent(student: Student) = _tempStudentState.update { student }

    fun saveCurrentTempStudent(student: Student) {
        _tempStudentState.update { student }
        viewModelScope.launch {
            repository.updateStudent(student = student)
        }.invokeOnCompletion {
            _tempStudentState.update { emptyStudent() }
        }
    }

    private val _tempRemainingClassSheetState = MutableStateFlow(listOf<ClassSheet>())
    val tempRemainingClassSheetState = _tempRemainingClassSheetState
        .onStart {
            viewModelScope.launch {
                repository.getClassSheets().collect { classSheets ->
                    println("classInits classSheetsState: flowing")
                    _tempRemainingClassSheetState.update { classSheets }
                }
            }
        }
        .stateInWhileSubscribed(
            initialValue = listOf()
        )

    private val _tempDeletePendingClassSheetState = MutableStateFlow(listOf<ClassSheet>())
    val tempDeletePendingClassSheetState = _tempDeletePendingClassSheetState
        .stateInWhileSubscribed(
            initialValue = listOf()
        )

    fun manageDeleteSectionEvent(event: DeleteClassSheet) {
        when(event) {
            is DeleteClassSheet.EventDelete -> {
                val newDeletePendingSheets = _tempRemainingClassSheetState.value.filter { it.classSection in event.selectedSections }
                _tempRemainingClassSheetState.update { remainingSheets ->
                    remainingSheets.toMutableList().apply {
                        removeAll(newDeletePendingSheets)
                    }
                }
                _tempDeletePendingClassSheetState.update { deletePendingSheets ->
                    deletePendingSheets.toMutableList().apply {
                        addAll(newDeletePendingSheets)
                    }
                }
            }
            is DeleteClassSheet.EventRestore -> {
                val newRestoredSheets = _tempDeletePendingClassSheetState.value.filter { it.classSection in event.selectedSections }
                _tempDeletePendingClassSheetState.update { deletePendingSheets ->
                    deletePendingSheets.toMutableList().apply {
                        removeAll(newRestoredSheets)
                    }
                }
                _tempRemainingClassSheetState.update { remainingSheets ->
                    remainingSheets.toMutableList().apply {
                        addAll(newRestoredSheets)
                    }
                }
            }
            DeleteClassSheet.EventReset -> {
                _tempRemainingClassSheetState.update { _classSheetsState.value }
                _tempDeletePendingClassSheetState.update { listOf() }
            }
        }
    }

    fun deleteSections() {
        viewModelScope.launch {
            repository.deleteSections(
                sectionsWithCount = _tempDeletePendingClassSheetState.value.map { sheet ->
                    Pair(
                        sheet.classSection.persistenceId,
                        (sheet.maleStudents.filter { it is StudentList.StudentDetails } +
                                sheet.femaleStudents.filter { it is StudentList.StudentDetails }).isNotEmpty()
                    )
                }
            )
        }.invokeOnCompletion {
//            _tempRemainingClassSheetState.update { _classSheetsState.value }
            _tempDeletePendingClassSheetState.update { listOf() }
        }
    }

    fun deleteStudents(studentPersistenceIds: List<Long>) {
        getPersistenceStudentJob?.cancel() //to avoid existing getStudent Flow crash
        viewModelScope.launch {
            repository.deleteStudentsByRoomId(studentIds = studentPersistenceIds)
        }
    }

    private val _tempClassSheetsStateForInputGrades = MutableStateFlow(listOf<ClassSheet>())
    val tempClassSheetsStateForInputGrades = _tempClassSheetsStateForInputGrades
        .onStart {
            viewModelScope.launch {
                repository.getClassSheets().collect { classSheets ->
                    println("classInits classSheetsState: flowing for _tempClassSheetsStateForInputGrades")
                    _tempClassSheetsStateForInputGrades.update { classSheets }
                }
            }
        }
        .stateInWhileSubscribed(
            initialValue = listOf()
        )

    fun updateTempSheetsForInputGrades(event: UpdateTempClassSheetsForInputGrades) {
        when(event) {
            is UpdateTempClassSheetsForInputGrades.EventReadingTest -> {
                _tempClassSheetsStateForInputGrades.update {
                    it.toMutableList().apply {
                        this.find { sheet ->
                            sheet.classSection.persistenceId == event.sectionPersistenceId
                        }?.let { thisSheet ->
                            (thisSheet.maleStudents + thisSheet.femaleStudents).find { sList ->
                                sList is StudentList.StudentDetails && sList.student.persistenceId == event.studentPersistenceId
                            }?.let { thisSList ->
                                when {
                                    event.isPostTest -> (thisSList as StudentList.StudentDetails).student.postTest = event.newReadingTest
                                    else -> (thisSList as StudentList.StudentDetails).student.preTest = event.newReadingTest
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun updateClassSheetsForInputGrades() {
        viewModelScope.launch {
            repository.saveClassSheets(classSheets = _tempClassSheetsStateForInputGrades.value)
        }
    }
}
