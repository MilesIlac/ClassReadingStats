package com.milesilac.classreadingstats.repository

import com.milesilac.classreadingstats.StudentsDBSource
import com.milesilac.classreadingstats.model.ClassSection
import com.milesilac.classreadingstats.model.ClassSheet
import com.milesilac.classreadingstats.persistence.model.mapPartitionForStudentList
import kotlinx.coroutines.flow.map

class StudentsRepositoryImpl(): StudentsRepository {

    init {
        println("classInits ${this::class.simpleName} init")
    }

    private val studentsDBSource = StudentsDBSource()

    override fun getClassSheets() = studentsDBSource.getClassSheets().map { results ->
        results.map { classSheet ->
            val (maleStudents, femaleStudents) = classSheet.students.mapPartitionForStudentList(
                classSection = ClassSection(
                    gradeLevel = classSheet.section.gradeLevel,
                    sectionName = classSheet.section.sectionName
                )
            )
            ClassSheet(
                classSection = ClassSection(
                    gradeLevel = classSheet.section.gradeLevel,
                    sectionName = classSheet.section.sectionName
                ),
                maleStudents = maleStudents,
                femaleStudents = femaleStudents
            )
        }
    }

}