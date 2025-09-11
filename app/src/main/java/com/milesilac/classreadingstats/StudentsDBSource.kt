package com.milesilac.classreadingstats

import com.milesilac.classreadingstats.persistence.AppDatabase
import com.milesilac.classreadingstats.persistence.model.SectionEntity
import com.milesilac.classreadingstats.persistence.model.StudentEntity

class StudentsDBSource() {

    init {
        println("classInits ${this::class.simpleName} init")
    }

    private val roomDAO = AppDatabase.getInstance(appContext = CRSApplication.context).roomDao()

    fun getAllSections() = roomDAO.getAllSections()

    fun getAllStudents() = roomDAO.getAllStudents()

    fun getStudent(studentId: Long) = roomDAO.getStudent(studentId = studentId)

    suspend fun saveSection(section: SectionEntity) = roomDAO.saveSection(section = section)

    suspend fun updateStudents(students: List<StudentEntity>) = roomDAO.updateStudents(students = students)

    suspend fun deleteSection(sectionId: Long) = roomDAO.deleteSection(sectionId = sectionId)

    suspend fun deleteSectionStudents(sectionId: Long) = roomDAO.deleteSectionStudents(sectionId = sectionId)

}