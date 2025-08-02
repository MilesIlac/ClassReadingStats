package com.milesilac.classreadingstats.ui

import com.milesilac.classreadingstats.model.ClassSection
import com.milesilac.classreadingstats.model.ClassSheet
import com.milesilac.classreadingstats.model.ComprehensionLevel
import com.milesilac.classreadingstats.model.GradeLevel
import com.milesilac.classreadingstats.model.GroupScreeningTest
import com.milesilac.classreadingstats.model.LearnerLevel
import com.milesilac.classreadingstats.model.OralReading
import com.milesilac.classreadingstats.model.ReadingComprehension
import com.milesilac.classreadingstats.model.Student
import com.milesilac.classreadingstats.model.StudentList
import com.milesilac.classreadingstats.model.StudentSexOrient

val dummySections = listOf(
    ClassSection(gradeLevel = GradeLevel.EIGHT, sectionName = "Amethyst"),
    ClassSection(gradeLevel = GradeLevel.EIGHT, sectionName = "Diamond")
)

val dummyStudentListsEightAmethyst = ClassSheet(
    classSection = ClassSection(gradeLevel = GradeLevel.EIGHT, sectionName = "Amethyst"),
    students = listOf(
        StudentList.Header(sex = StudentSexOrient.MALE),
        StudentList.StudentDetails(
            student = Student(
                orderId = 1,
                name = "Alao, John Nathan",
                section = "8-AMETHYST",
                sex = "M",
                groupScreeningTest = GroupScreeningTest(
                    score = 13,
                    comprehensionLevel = ComprehensionLevel.FIVE
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 3,
                    percentage = 97.17F,
                    level = LearnerLevel.INDEPENDENT
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 86F,
                    level = LearnerLevel.INDEPENDENT
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 2,
                name = "Alendro, Frellian",
                section = "8-AMETHYST",
                sex = "M",
                groupScreeningTest = GroupScreeningTest(
                    score = 13,
                    comprehensionLevel = ComprehensionLevel.FIVE
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 8,
                    percentage = 92.45F,
                    level = LearnerLevel.INSTRUCTIONAL
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 63F,
                    level = LearnerLevel.INSTRUCTIONAL
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 3,
                name = "Bandong, John Paul Mariano",
                section = "8-AMETHYST",
                sex = "M",
                groupScreeningTest = GroupScreeningTest(
                    score = 15,
                    comprehensionLevel = ComprehensionLevel.FIVE
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 3,
                    percentage = 97.17F,
                    level = LearnerLevel.INDEPENDENT
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 86F,
                    level = LearnerLevel.INDEPENDENT
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 4,
                name = "Bauca, Romel Ustare",
                section = "8-AMETHYST",
                sex = "M",
                groupScreeningTest = GroupScreeningTest(
                    score = 12,
                    comprehensionLevel = ComprehensionLevel.FIVE
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 9,
                    percentage = 91.51F,
                    level = LearnerLevel.INSTRUCTIONAL
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 71F,
                    level = LearnerLevel.INSTRUCTIONAL
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 5,
                name = "Buscayno, Mark Deniel",
                section = "8-AMETHYST",
                sex = "M",
                groupScreeningTest = GroupScreeningTest(
                    score = 11,
                    comprehensionLevel = ComprehensionLevel.FIVE
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 10,
                    percentage = 90.52F,
                    level = LearnerLevel.INSTRUCTIONAL
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 71F,
                    level = LearnerLevel.INSTRUCTIONAL
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 6,
                name = "Cabuquit, Raymart Sotto",
                section = "8-AMETHYST",
                sex = "M",
                groupScreeningTest = GroupScreeningTest(
                    score = 16,
                    comprehensionLevel = ComprehensionLevel.SIX
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 10,
                    percentage = 90.52F,
                    level = LearnerLevel.INSTRUCTIONAL
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 63F,
                    level = LearnerLevel.INSTRUCTIONAL
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 7,
                name = "Cuyno, Jhullian Jade",
                section = "8-AMETHYST",
                sex = "M",
                groupScreeningTest = GroupScreeningTest(
                    score = 7,
                    comprehensionLevel = ComprehensionLevel.FIVE
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 10,
                    percentage = 90.52F,
                    level = LearnerLevel.INSTRUCTIONAL
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 63F,
                    level = LearnerLevel.INSTRUCTIONAL
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 8,
                name = "Daganato, Jerome Soon Jin Riῆos",
                section = "8-AMETHYST",
                sex = "M",
                groupScreeningTest = GroupScreeningTest(
                    score = 14,
                    comprehensionLevel = ComprehensionLevel.FIVE
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 2,
                    percentage = 98.11F,
                    level = LearnerLevel.INDEPENDENT
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 86F,
                    level = LearnerLevel.INDEPENDENT
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 9,
                name = "Dela Cruz, Charlie",
                section = "8-AMETHYST",
                sex = "M",
                groupScreeningTest = GroupScreeningTest(
                    score = 16,
                    comprehensionLevel = ComprehensionLevel.SIX
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 8,
                    percentage = 92.45F,
                    level = LearnerLevel.INSTRUCTIONAL
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 63F,
                    level = LearnerLevel.INSTRUCTIONAL
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 10,
                name = "Dela cruz, Kurt Yuri Ignacio",
                section = "8-AMETHYST",
                sex = "M",
                groupScreeningTest = GroupScreeningTest(
                    score = 16,
                    comprehensionLevel = ComprehensionLevel.SIX
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 9,
                    percentage = 91.51F,
                    level = LearnerLevel.INSTRUCTIONAL
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 63F,
                    level = LearnerLevel.INSTRUCTIONAL
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 11,
                name = "De Otoy, Edwin Cagadas",
                section = "8-AMETHYST",
                sex = "M",
                groupScreeningTest = GroupScreeningTest(
                    score = 8,
                    comprehensionLevel = ComprehensionLevel.FIVE
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 17,
                    percentage = 80.23F,
                    level = LearnerLevel.FRUSTRATION
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 43F,
                    level = LearnerLevel.FRUSTRATION
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 12,
                name = "Enano, Andrew John Apas",
                section = "8-AMETHYST",
                sex = "M",
                groupScreeningTest = GroupScreeningTest(
                    score = 14,
                    comprehensionLevel = ComprehensionLevel.FIVE
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 9,
                    percentage = 91.51F,
                    level = LearnerLevel.INSTRUCTIONAL
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 71F,
                    level = LearnerLevel.INSTRUCTIONAL
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 13,
                name = "Estrella, Tristan Andrei Sulam",
                section = "8-AMETHYST",
                sex = "M",
                groupScreeningTest = GroupScreeningTest(
                    score = 19,
                    comprehensionLevel = ComprehensionLevel.FIVE
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 9,
                    percentage = 91.51F,
                    level = LearnerLevel.INSTRUCTIONAL
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 63F,
                    level = LearnerLevel.INSTRUCTIONAL
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 14,
                name = "Galang, Gian Jaren Tamayo",
                section = "8-AMETHYST",
                sex = "M",
                groupScreeningTest = GroupScreeningTest(
                    score = 15,
                    comprehensionLevel = ComprehensionLevel.FIVE
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 10,
                    percentage = 90.52F,
                    level = LearnerLevel.INSTRUCTIONAL
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 71F,
                    level = LearnerLevel.INSTRUCTIONAL
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 15,
                name = "Grijaldo, Sebastian Allen Quian Gutierrez",
                section = "8-AMETHYST",
                sex = "M",
                groupScreeningTest = GroupScreeningTest(
                    score = 18,
                    comprehensionLevel = ComprehensionLevel.SIX
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 10,
                    percentage = 90.52F,
                    level = LearnerLevel.INSTRUCTIONAL
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 63F,
                    level = LearnerLevel.INSTRUCTIONAL
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 16,
                name = "Ibaῆez, Prince Nathan",
                section = "8-AMETHYST",
                sex = "M",
                groupScreeningTest = GroupScreeningTest(
                    score = 13,
                    comprehensionLevel = ComprehensionLevel.FIVE
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 8,
                    percentage = 92.45F,
                    level = LearnerLevel.INSTRUCTIONAL
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 71F,
                    level = LearnerLevel.INSTRUCTIONAL
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 17,
                name = "Lacson, Angel Mendoza",
                section = "8-AMETHYST",
                sex = "M",
                groupScreeningTest = GroupScreeningTest(
                    score = 14,
                    comprehensionLevel = ComprehensionLevel.FIVE
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 8,
                    percentage = 92.45F,
                    level = LearnerLevel.INSTRUCTIONAL
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 100F,
                    level = LearnerLevel.INDEPENDENT
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 18,
                name = "Nocom, Leeiel Briones",
                section = "8-AMETHYST",
                sex = "M",
                groupScreeningTest = GroupScreeningTest(
                    score = 12,
                    comprehensionLevel = ComprehensionLevel.FIVE
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 8,
                    percentage = 92.45F,
                    level = LearnerLevel.INSTRUCTIONAL
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 71F,
                    level = LearnerLevel.INSTRUCTIONAL
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 19,
                name = "Ocampo, Brahne Niven Pandaan ",
                section = "8-AMETHYST",
                sex = "M",
                groupScreeningTest = GroupScreeningTest(
                    score = 17,
                    comprehensionLevel = ComprehensionLevel.SIX
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 3,
                    percentage = 97.17F,
                    level = LearnerLevel.INDEPENDENT
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 88F,
                    level = LearnerLevel.INDEPENDENT
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 20,
                name = "Pamintuan, Fervinson Massif Rosadeno",
                section = "8-AMETHYST",
                sex = "M",
                groupScreeningTest = GroupScreeningTest(
                    score = 6,
                    comprehensionLevel = ComprehensionLevel.SIX
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 2,
                    percentage = 98.11F,
                    level = LearnerLevel.INDEPENDENT
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 86F,
                    level = LearnerLevel.INDEPENDENT
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 21,
                name = "Pepito, Francis Johan Balaguer",
                section = "8-AMETHYST",
                sex = "M",
                groupScreeningTest = GroupScreeningTest(
                    score = 13,
                    comprehensionLevel = ComprehensionLevel.FIVE
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 2,
                    percentage = 98.11F,
                    level = LearnerLevel.INDEPENDENT
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 86F,
                    level = LearnerLevel.INDEPENDENT
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 22,
                name = "Sabbun, Aries Leyva",
                section = "8-AMETHYST",
                sex = "M",
                groupScreeningTest = GroupScreeningTest(
                    score = 12,
                    comprehensionLevel = ComprehensionLevel.FIVE
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 19,
                    percentage = 77.91F,
                    level = LearnerLevel.FRUSTRATION
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 43F,
                    level = LearnerLevel.FRUSTRATION
                )
            )
        ),
        StudentList.Header(sex = StudentSexOrient.FEMALE),
        StudentList.StudentDetails(
            student = Student(
                orderId = 23,
                name = "Baldonado, Ameerah Faith Balisay",
                section = "8-DIAMOND",
                sex = "F",
                groupScreeningTest = GroupScreeningTest(
                    score = 15,
                    comprehensionLevel = ComprehensionLevel.FIVE
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 3,
                    percentage = 97.17F,
                    level = LearnerLevel.INDEPENDENT
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 100F,
                    level = LearnerLevel.INDEPENDENT
                )
            )
        ),
    )
)

val dummyStudentListsEightDiamond = ClassSheet(
    classSection = ClassSection(gradeLevel = GradeLevel.EIGHT, sectionName = "Diamond"),
    students = listOf(
        StudentList.Header(sex = StudentSexOrient.MALE),
        StudentList.Header(sex = StudentSexOrient.FEMALE),
        StudentList.StudentDetails(
            student = Student(
                orderId = 1,
                name = "Baldonado, Ameerah Faith Balisay",
                section = "8-DIAMOND",
                sex = "F",
                groupScreeningTest = GroupScreeningTest(
                    score = 15,
                    comprehensionLevel = ComprehensionLevel.FIVE
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 3,
                    percentage = 97.17F,
                    level = LearnerLevel.INDEPENDENT
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 100F,
                    level = LearnerLevel.INDEPENDENT
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 2,
                name = "Barcinas, Maria Angela Galang",
                section = "8-DIAMOND",
                sex = "F",
                groupScreeningTest = GroupScreeningTest(
                    score = 10,
                    comprehensionLevel = ComprehensionLevel.FIVE
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 7,
                    percentage = 93.4F,
                    level = LearnerLevel.INSTRUCTIONAL
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 100F,
                    level = LearnerLevel.INDEPENDENT
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 3,
                name = "Dayan- Dayan, Akiko Reign",
                section = "8-DIAMOND",
                sex = "F",
                groupScreeningTest = GroupScreeningTest(
                    score = 5,
                    comprehensionLevel = ComprehensionLevel.FIVE
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 7,
                    percentage = 93.4F,
                    level = LearnerLevel.INSTRUCTIONAL
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 100F,
                    level = LearnerLevel.INDEPENDENT
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 4,
                name = "Guiao, Cristine Bumatay",
                section = "8-DIAMOND",
                sex = "F",
                groupScreeningTest = GroupScreeningTest(
                    score = 12,
                    comprehensionLevel = ComprehensionLevel.FIVE
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 2,
                    percentage = 98.11F,
                    level = LearnerLevel.INDEPENDENT
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 86F,
                    level = LearnerLevel.INDEPENDENT
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 5,
                name = "Loremas, Jermaine Fernando",
                section = "8-DIAMOND",
                sex = "F",
                groupScreeningTest = GroupScreeningTest(
                    score = 9,
                    comprehensionLevel = ComprehensionLevel.FIVE
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 9,
                    percentage = 91.51F,
                    level = LearnerLevel.INSTRUCTIONAL
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 71F,
                    level = LearnerLevel.INSTRUCTIONAL
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 6,
                name = "Lugue, Rhian Joyce Payumo",
                section = "8-DIAMOND",
                sex = "F",
                groupScreeningTest = GroupScreeningTest(
                    score = 12,
                    comprehensionLevel = ComprehensionLevel.FIVE
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 2,
                    percentage = 98.11F,
                    level = LearnerLevel.INDEPENDENT
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 71F,
                    level = LearnerLevel.INSTRUCTIONAL
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 7,
                name = "Marzan, Kristina Joy Fernandez",
                section = "8-DIAMOND",
                sex = "F",
                groupScreeningTest = GroupScreeningTest(
                    score = 15,
                    comprehensionLevel = ComprehensionLevel.FIVE
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 3,
                    percentage = 97.17F,
                    level = LearnerLevel.INDEPENDENT
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 100F,
                    level = LearnerLevel.INDEPENDENT
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 8,
                name = "Perez, Prynzes Nicole Tiglao",
                section = "8-DIAMOND",
                sex = "F",
                groupScreeningTest = GroupScreeningTest(
                    score = 5,
                    comprehensionLevel = ComprehensionLevel.FIVE
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 2,
                    percentage = 98.11F,
                    level = LearnerLevel.INDEPENDENT
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 100F,
                    level = LearnerLevel.INDEPENDENT
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 9,
                name = "Recto, Rachelle Ann",
                section = "8-DIAMOND",
                sex = "F",
                groupScreeningTest = GroupScreeningTest(
                    score = 10,
                    comprehensionLevel = ComprehensionLevel.FIVE
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 10,
                    percentage = 90.56F,
                    level = LearnerLevel.INSTRUCTIONAL
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 71F,
                    level = LearnerLevel.INSTRUCTIONAL
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 10,
                name = "Reyes, Amanda Faith Quiboloy",
                section = "8-DIAMOND",
                sex = "F",
                groupScreeningTest = GroupScreeningTest(
                    score = 10,
                    comprehensionLevel = ComprehensionLevel.FIVE
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 2,
                    percentage = 98.11F,
                    level = LearnerLevel.INSTRUCTIONAL
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 63F,
                    level = LearnerLevel.INSTRUCTIONAL
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 11,
                name = "Reyes, Fiona Briel Bondoc",
                section = "8-DIAMOND",
                sex = "F",
                groupScreeningTest = GroupScreeningTest(
                    score = 7,
                    comprehensionLevel = ComprehensionLevel.FIVE
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 2,
                    percentage = 98.11F,
                    level = LearnerLevel.INDEPENDENT
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 71F,
                    level = LearnerLevel.INSTRUCTIONAL
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 12,
                name = "Rivamonte, Mary Juliet Barrientos",
                section = "8-DIAMOND",
                sex = "F",
                groupScreeningTest = GroupScreeningTest(
                    score = 13,
                    comprehensionLevel = ComprehensionLevel.FIVE
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 9,
                    percentage = 91.51F,
                    level = LearnerLevel.INSTRUCTIONAL
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 71F,
                    level = LearnerLevel.INSTRUCTIONAL
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 13,
                name = "Sagad, Keona Zoe Wage",
                section = "8-DIAMOND",
                sex = "F",
                groupScreeningTest = GroupScreeningTest(
                    score = 9,
                    comprehensionLevel = ComprehensionLevel.FIVE
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 2,
                    percentage = 98.11F,
                    level = LearnerLevel.INDEPENDENT
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 86F,
                    level = LearnerLevel.INDEPENDENT
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 14,
                name = "Saldivar, Leyarra Janel Basco",
                section = "8-DIAMOND",
                sex = "F",
                groupScreeningTest = GroupScreeningTest(
                    score = 13,
                    comprehensionLevel = ComprehensionLevel.FIVE
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 3,
                    percentage = 97.17F,
                    level = LearnerLevel.INDEPENDENT
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 63F,
                    level = LearnerLevel.INSTRUCTIONAL
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 15,
                name = "Sampana, Aira Joy Soliman",
                section = "8-DIAMOND",
                sex = "F",
                groupScreeningTest = GroupScreeningTest(
                    score = 10,
                    comprehensionLevel = ComprehensionLevel.FIVE
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 9,
                    percentage = 91.51F,
                    level = LearnerLevel.INSTRUCTIONAL
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 63F,
                    level = LearnerLevel.INSTRUCTIONAL
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 16,
                name = "Tiamzon, Ruslyn Ghin Abahas",
                section = "8-DIAMOND",
                sex = "F",
                groupScreeningTest = GroupScreeningTest(
                    score = 18,
                    comprehensionLevel = ComprehensionLevel.SIX
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 3,
                    percentage = 97.17F,
                    level = LearnerLevel.INDEPENDENT
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 63F,
                    level = LearnerLevel.INSTRUCTIONAL
                )
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 17,
                name = "Villapana, Shanelle Cruz",
                section = "8-DIAMOND",
                sex = "F",
                groupScreeningTest = GroupScreeningTest(
                    score = 8,
                    comprehensionLevel = ComprehensionLevel.FIVE
                ),
                isGradingPassage = true,
                oralReading = OralReading(
                    totalNumberOfWordsInSelection = 106,
                    numberOfMiscues = 3,
                    percentage = 97.17F,
                    level = LearnerLevel.INDEPENDENT
                ),
                readingComprehension = ReadingComprehension(
                    inputPercentage = 86F,
                    level = LearnerLevel.INDEPENDENT
                )
            )
        )
    )
)