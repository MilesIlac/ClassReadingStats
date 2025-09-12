package com.milesilac.classreadingstats.activity

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.milesilac.classreadingstats.helpers.transformFromJsonString
import com.milesilac.classreadingstats.helpers.transformToJsonString
import com.milesilac.classreadingstats.model.ClassSheet
import com.milesilac.classreadingstats.model.Student
import com.milesilac.classreadingstats.model.StudentList
import com.milesilac.classreadingstats.model.StudentSexOrient
import com.milesilac.classreadingstats.model.emptyStudent
import com.milesilac.classreadingstats.model.initClassSheet
import com.milesilac.classreadingstats.repository.StudentsRepository
import com.milesilac.classreadingstats.ui.screens.DeleteClassSheet
import com.milesilac.classreadingstats.ui.screens.StudentDetailEditEvent
import com.milesilac.classreadingstats.ui.screens.UpdateTempClassSheetForAddSection
import com.milesilac.classreadingstats.ui.screens.UpdateTempClassSheetsForInputGrades
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    init {
        println("classInits ${this::class.simpleName} init")
    }

    companion object {
        private const val TEMP_CLASS_SHEET = "tempClassSheet"
        private const val TEMP_STUDENT = "tempStudent"
        private const val TEMP_LOCAL_STUDENT = "tempLocalStudent"
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

    private val _tempClassSheetState = savedStateHandle.getMutableStateFlow<String?>(TEMP_CLASS_SHEET, null)
    val tempClassSheetState = _tempClassSheetState
        .filterNotNull()
        .map { thisString ->
            thisString.transformFromJsonString<ClassSheet>()
        }
        .stateInWhileSubscribed(
            initialValue = initClassSheet()
        )

    fun updateTempClassSheet(event: UpdateTempClassSheetForAddSection) {
        when(event) {
            UpdateTempClassSheetForAddSection.EventDelete -> {
                savedStateHandle[TEMP_CLASS_SHEET] = initClassSheet().transformToJsonString()
            }
            is UpdateTempClassSheetForAddSection.EventGradeLevel -> {
                savedStateHandle[TEMP_CLASS_SHEET] = tempClassSheetState.value.let {
                    it.copy(classSection = it.classSection.copy(gradeLevel = event.gradeLevel))
                }.transformToJsonString()
            }
            is UpdateTempClassSheetForAddSection.EventSectionName -> {
                savedStateHandle[TEMP_CLASS_SHEET] = tempClassSheetState.value.let {
                    it.copy(classSection = it.classSection.copy(sectionName = event.sectionName))
                }.transformToJsonString()
            }
            is UpdateTempClassSheetForAddSection.EventSectionStudent -> {
                val student = tempStudentState.value
                when (student.sex) {
                    StudentSexOrient.MALE -> {
                        savedStateHandle[TEMP_CLASS_SHEET] = tempClassSheetState.value.let {
                            it.copy(
                                maleStudents = it.maleStudents.toMutableList().apply {
                                    add(StudentList.StudentDetails(student = student))
                                }
                            )
                        }.transformToJsonString()
                    }
                    StudentSexOrient.FEMALE -> {
                        savedStateHandle[TEMP_CLASS_SHEET] = tempClassSheetState.value.let {
                            it.copy(
                                femaleStudents = it.femaleStudents.toMutableList().apply {
                                    add(StudentList.StudentDetails(student = student))
                                }
                            )
                        }.transformToJsonString()
                    }
                    StudentSexOrient.ERROR -> {}
                }
                updateTempStudent(event = StudentDetailEditEvent.EventReset(section = student.section))
            }
        }
    }

    fun saveClassSheet() {
        viewModelScope.launch {
            repository.saveClassSheets(classSheets = listOf(tempClassSheetState.value))
        }.invokeOnCompletion {
            savedStateHandle[TEMP_CLASS_SHEET] = initClassSheet().transformToJsonString()
        }
    }

    private val _localStudentState = savedStateHandle.getMutableStateFlow<String?>(TEMP_LOCAL_STUDENT, null)
    val localStudentState = _localStudentState
        .filterNotNull()
        .map { thisString ->
            thisString.transformFromJsonString<Student>()
        }
        .stateInWhileSubscribed(
            initialValue = emptyStudent()
        )

    fun getLocalSourceStudent(studentPersistenceId: Long, withTempStudentUpdate: Boolean = true) {
        getPersistenceStudentJob?.cancel()
        getPersistenceStudentJob = repository.getStudent(studentId = studentPersistenceId)
            .onEach { student ->
                savedStateHandle[TEMP_LOCAL_STUDENT] = student.transformToJsonString()
                if (withTempStudentUpdate) {
                    savedStateHandle[TEMP_STUDENT] = student.transformToJsonString()
                }
            }
            .launchIn(viewModelScope)
    }

    fun updateLocalSourceStudent(withTempStudentUpdate: Boolean) {
        viewModelScope.launch {
            repository.updateStudent(student = tempStudentState.value)
        }.invokeOnCompletion {
            getLocalSourceStudent(
                studentPersistenceId = tempStudentState.value.persistenceId,
                withTempStudentUpdate = withTempStudentUpdate
            )
        }
    }

    private val _tempStudentState = savedStateHandle.getMutableStateFlow<String?>(TEMP_STUDENT, null)
    val tempStudentState = _tempStudentState
        .filterNotNull()
        .map { thisString ->
            thisString.transformFromJsonString<Student>()
        }
        .stateInWhileSubscribed(
            initialValue = emptyStudent()
        )

    fun updateTempStudent(event: StudentDetailEditEvent) {
        when (event) {
            is StudentDetailEditEvent.EventName -> {
                savedStateHandle[TEMP_STUDENT] = tempStudentState.value.copy(
                    name = event.studentName
                ).transformToJsonString()
            }
            is StudentDetailEditEvent.EventSection -> {
                savedStateHandle[TEMP_STUDENT] = tempStudentState.value.copy(
                    section = event.section
                ).transformToJsonString()
            }
            is StudentDetailEditEvent.EventSexOrient -> {
                savedStateHandle[TEMP_STUDENT] = tempStudentState.value.copy(
                    sex = event.sex
                ).transformToJsonString()
            }
            is StudentDetailEditEvent.EventReadingTest -> {
                savedStateHandle[TEMP_STUDENT] = tempStudentState.value.let {
                    when {
                        event.isPostTest -> it.copy(postTest = event.readingTest)
                        else -> it.copy(preTest = event.readingTest)
                    }
                }.transformToJsonString()
            }
            is StudentDetailEditEvent.EventReset -> {
                savedStateHandle[TEMP_STUDENT] = emptyStudent(section = event.section).transformToJsonString()
            }
        }
    }

    fun saveCurrentTempStudent() {
        viewModelScope.launch {
            repository.updateStudent(student = tempStudentState.value)
        }.invokeOnCompletion {
            savedStateHandle[TEMP_STUDENT] = emptyStudent().transformToJsonString()
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
