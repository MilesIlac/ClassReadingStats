package com.milesilac.classreadingstats.repository

import com.milesilac.classreadingstats.StudentsDBSource

class StudentsRepositoryImpl(): StudentsRepository {

    init {
        println("classInits ${this::class.simpleName} init")
    }

    private val studentsDBSource = StudentsDBSource()

    override fun getClassSheets() = studentsDBSource.getClassSheets()

}