package com.milesilac.classreadingstats.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.milesilac.classreadingstats.StudentsRepository
import com.milesilac.classreadingstats.model.ClassSheet
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

    private val repository = StudentsRepository()

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
}
