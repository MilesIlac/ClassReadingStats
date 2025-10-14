package com.milesilac.classreadingstats.repository

import com.frosch2010.fuzzywuzzy_kotlin.FuzzySearch
import com.milesilac.classreadingstats.StudentsDBSource
import com.milesilac.classreadingstats.model.ClassSection
import com.milesilac.classreadingstats.model.ClassSheet
import com.milesilac.classreadingstats.model.Student
import com.milesilac.classreadingstats.model.StudentToDeleteBundle
import com.milesilac.classreadingstats.persistence.model.SectionEntity
import com.milesilac.classreadingstats.persistence.model.StudentEntity
import com.milesilac.classreadingstats.persistence.model.addStudentEntityWithOrderId
import com.milesilac.classreadingstats.persistence.model.mapPartitionForStudentList
import com.milesilac.classreadingstats.persistence.model.mapStudentEntity
import com.milesilac.classreadingstats.persistence.model.mapStudentListToEntity
import com.milesilac.classreadingstats.persistence.model.updateRemainingStudentEntitiesOrderId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class StudentsRepositoryImpl(): StudentsRepository {

//    init {
//        println("classInits ${this::class.simpleName} init")
//    }

    private val studentsDBSource = StudentsDBSource()

    private val lookedUpPairs = MutableStateFlow<List<StudentWithScannedGrade>>(listOf())

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

    override suspend fun saveClassSheets(classSheets: List<ClassSheet>) {
        classSheets.forEach { classSheet ->
            val returnSectionId = studentsDBSource.updateSection(
                section = SectionEntity(
                    sectionRoomId = classSheet.classSection.persistenceId,
                    gradeLevel = classSheet.classSection.gradeLevel,
                    sectionName = classSheet.classSection.sectionName
                )
            )
            val sectionPersistenceId = when {
                returnSectionId != -1L -> returnSectionId // -1 = Update successful or Insert error
                else -> classSheet.classSection.persistenceId
            }
            studentsDBSource.updateStudents(
                students = (classSheet.maleStudents.asSequence() + classSheet.femaleStudents.asSequence())
                    .mapStudentListToEntity(sectionId = sectionPersistenceId)
            )
        }
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

    override fun getLookupPairs(): Flow<List<StudentWithScannedGrade>> = lookedUpPairs.asStateFlow()

    override suspend fun lookupStudents(
        sectionPersistenceId: Long,
        studentNamePairs: Set<Pair<String,String>>
    ) {
        val dbStudents = studentsDBSource.getSameListStudents(
            sectionId = sectionPersistenceId
        ).toSet()
        val outputList = mutableListOf<StudentWithScannedGrade>()
        studentNamePairs.forEach { (studentName, studentGrade) ->
            val filteredDBStudents = dbStudents.filter { dbStudent ->
                FuzzySearch.ratio(studentName,dbStudent.studentName) >= 90
            }
            filteredDBStudents.maxByOrNull { dbStudent ->
                FuzzySearch.weightedRatio(studentName,dbStudent.studentName)
            }?.let { studentEntity ->
                outputList.add(
                    StudentWithScannedGrade(
                        studentPersistenceId = studentEntity.studentRoomId,
                        orderId = studentEntity.orderId.toInt(),
                        studentName = studentEntity.studentName,
                        inputGrade = studentGrade,
                        hasPostTest = studentEntity.hasPostTest
                    )
                )
            }
        }
        lookedUpPairs.update { outputList }
    }

    override suspend fun updateStudent(student: Student, hasSortOperation: Boolean) {
        studentsDBSource.updateStudents(
            students = when {
                hasSortOperation -> studentsDBSource.getSameListStudents(
                    sectionId = student.section.persistenceId,
                    sex = student.sex
                ).addStudentEntityWithOrderId(
                    studentEntity = StudentEntity(
                        studentRoomId = student.persistenceId,
                        sectionRoomId = student.section.persistenceId,
                        orderId = student.orderId,
                        studentName = student.name,
                        sex = student.sex,
                        hasPostTest = student.hasPostTest,
                        gstScore = student.gst.score,
                        preORTotalNumberOfWords = student.preTest.oralReading.totalNumberOfWordsInSelection,
                        preORNumberOfMiscues = student.preTest.oralReading.numberOfMiscues,
                        preRCInputPercentage = student.preTest.readingComprehension.inputPercentage,
                        postORTotalNumberOfWords = student.postTest?.oralReading?.totalNumberOfWordsInSelection,
                        postORNumberOfMiscues = student.postTest?.oralReading?.numberOfMiscues,
                        postRCInputPercentage = student.postTest?.readingComprehension?.inputPercentage
                    )
                )
                else -> listOf(
                    StudentEntity(
                        studentRoomId = student.persistenceId,
                        sectionRoomId = student.section.persistenceId,
                        orderId = student.orderId,
                        studentName = student.name,
                        sex = student.sex,
                        hasPostTest = student.hasPostTest,
                        gstScore = student.gst.score,
                        preORTotalNumberOfWords = student.preTest.oralReading.totalNumberOfWordsInSelection,
                        preORNumberOfMiscues = student.preTest.oralReading.numberOfMiscues,
                        preRCInputPercentage = student.preTest.readingComprehension.inputPercentage,
                        postORTotalNumberOfWords = student.postTest?.oralReading?.totalNumberOfWordsInSelection,
                        postORNumberOfMiscues = student.postTest?.oralReading?.numberOfMiscues,
                        postRCInputPercentage = student.postTest?.readingComprehension?.inputPercentage
                    )
                )
            }
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

    override suspend fun deleteStudentsByRoomId(studentBundlesToDelete: List<StudentToDeleteBundle>) {
        studentBundlesToDelete.forEach { studentToDeleteBundle ->
            studentsDBSource.updateStudents(
                students = studentsDBSource.getSameListStudents(
                    sectionId = studentToDeleteBundle.classSection.persistenceId,
                    sex = studentToDeleteBundle.sexOrient
                ).updateRemainingStudentEntitiesOrderId(
                    studentIdsToDelete = studentToDeleteBundle.studentPersistenceIds
                )
            )
            studentsDBSource.deleteStudentsByRoomId(studentIds = studentToDeleteBundle.studentPersistenceIds)
        }
    }

}

data class StudentWithScannedGrade(
    var studentPersistenceId: Long,
    var orderId: Int,
    var studentName: String,
    var inputGrade: String,
    var hasPostTest: Boolean,
)