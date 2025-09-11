package com.milesilac.classreadingstats.repository

import com.milesilac.classreadingstats.StudentsDBSource
import com.milesilac.classreadingstats.model.ClassSection
import com.milesilac.classreadingstats.model.ClassSheet
import com.milesilac.classreadingstats.model.Student
import com.milesilac.classreadingstats.persistence.model.SectionEntity
import com.milesilac.classreadingstats.persistence.model.StudentEntity
import com.milesilac.classreadingstats.persistence.model.mapPartitionForStudentList
import com.milesilac.classreadingstats.persistence.model.mapStudentEntity
import com.milesilac.classreadingstats.persistence.model.mapStudentListToEntity
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class StudentsRepositoryImpl(): StudentsRepository {

    init {
        println("classInits ${this::class.simpleName} init")
    }

    private val studentsDBSource = StudentsDBSource()

    override fun getClassSheets() = combine(
        studentsDBSource.getAllSections(),
        studentsDBSource.getAllStudents()
    ) { sections, students ->
        sections.map { section ->
            val (maleStudents, femaleStudents) = students
                .filter { it.sectionRoomId == section.sectionRoomId }
                .mapPartitionForStudentList(
                    classSection = ClassSection(
                        persistenceId = section.sectionRoomId,
                        gradeLevel = section.gradeLevel,
                        sectionName = section.sectionName
                    )
            )
            ClassSheet(
                classSection = ClassSection(
                    persistenceId = section.sectionRoomId,
                    gradeLevel = section.gradeLevel,
                    sectionName = section.sectionName
                ),
                maleStudents = maleStudents,
                femaleStudents = femaleStudents
            )
        }
    }

    override suspend fun saveClassSheet(classSheet: ClassSheet) {
        val sectionPersistenceId = studentsDBSource.saveSection(
            section = SectionEntity(
                sectionRoomId = classSheet.classSection.persistenceId,
                gradeLevel = classSheet.classSection.gradeLevel,
                sectionName = classSheet.classSection.sectionName
            )
        )
        studentsDBSource.updateStudents(
            students = (classSheet.maleStudents + classSheet.femaleStudents).mapStudentListToEntity(sectionId = sectionPersistenceId)
        )
    }

    override fun getStudent(studentId: Long) = studentsDBSource.getStudent(studentId = studentId).map { results ->
        results.student.mapStudentEntity(
            classSection = ClassSection(
                persistenceId = results.section.sectionRoomId,
                gradeLevel = results.section.gradeLevel,
                sectionName = results.section.sectionName
            )
        )
    }

    override suspend fun updateStudent(student: Student) {
        studentsDBSource.updateStudents(
            students = listOf(
                StudentEntity(
                    studentRoomId = student.persistenceId,
                    sectionRoomId = student.section.persistenceId,
                    orderId = student.orderId,
                    studentName = student.name,
                    sex = student.sex,
                    preGSTScore = student.preTest.groupScreeningTest.score,
                    preORTotalNumberOfWords = student.preTest.oralReading?.totalNumberOfWordsInSelection ?: -1.0,
                    preORNumberOfMiscues = student.preTest.oralReading?.numberOfMiscues ?: -1.0,
                    preRCInputPercentage = student.preTest.readingComprehension?.inputPercentage ?: -1.0,
                    postGSTScore = student.postTest?.groupScreeningTest?.score,
                    postORTotalNumberOfWords = student.postTest?.oralReading?.totalNumberOfWordsInSelection,
                    postORNumberOfMiscues = student.postTest?.oralReading?.numberOfMiscues,
                    postRCInputPercentage = student.postTest?.readingComprehension?.inputPercentage
                )
            )
        )
    }

    override suspend fun deleteSections(sectionsWithCount: List<Pair<Long, Boolean>>) {
        sectionsWithCount.forEach { (sectionId, hasStudents) ->
            studentsDBSource.deleteSection(sectionId = sectionId)
            if (hasStudents) {
                studentsDBSource.deleteSectionStudents(sectionId = sectionId)
            }
        }
    }

    override suspend fun deleteStudentsByRoomId(studentIds: List<Long>) {
        studentsDBSource.deleteStudentsByRoomId(studentIds = studentIds)
    }

}