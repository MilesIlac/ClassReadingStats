package com.milesilac.classreadingstats

import com.milesilac.classreadingstats.persistence.AppDatabase
import com.milesilac.classreadingstats.ui.dummyRoomList
import kotlinx.coroutines.flow.flowOf

class StudentsDBSource() {

    init {
        println("classInits ${this::class.simpleName} init")
    }

    private val roomDAO = AppDatabase.getInstance(appContext = CRSApplication.context).roomDao()

//    fun getClassSheets() = roomDAO.getClassSheets()

    fun getClassSheets() = flowOf(dummyRoomList)

}