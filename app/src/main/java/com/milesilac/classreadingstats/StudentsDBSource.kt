package com.milesilac.classreadingstats

import com.milesilac.classreadingstats.model.ClassSection
import com.milesilac.classreadingstats.model.ClassSheet
import com.milesilac.classreadingstats.persistence.AppDatabase
import com.milesilac.classreadingstats.persistence.model.mapPartitionForStudentList
import com.milesilac.classreadingstats.ui.dummyRoomList
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class StudentsDBSource() {

    init {
        println("classInits ${this::class.simpleName} init")
    }

    private val roomDAO = AppDatabase.getInstance(appContext = CRSApplication.context).roomDao()

//    fun getClassSheets() = roomDAO.getClassSheets().map { results ->
//        results.map { classSheet ->
//            val (maleStudents, femaleStudents) = classSheet.students.mapPartitionForStudentList(
//                classSection = ClassSection(
//                    gradeLevel = classSheet.section.gradeLevel,
//                    sectionName = classSheet.section.sectionName
//                )
//            )
//            ClassSheet(
//                classSection = ClassSection(
//                    gradeLevel = classSheet.section.gradeLevel,
//                    sectionName = classSheet.section.sectionName
//                ),
//                maleStudents = maleStudents,
//                femaleStudents = femaleStudents
//            )
//        }
//    }

    fun getClassSheets() = flowOf(dummyRoomList).map { results ->
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