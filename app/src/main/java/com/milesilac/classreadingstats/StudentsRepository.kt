package com.milesilac.classreadingstats

class StudentsRepository() {

    init {
        println("classInits ${this::class.simpleName} init")
    }

    private val studentsDBSource = StudentsDBSource()

    fun getClassSheets() = studentsDBSource.getClassSheets()

}