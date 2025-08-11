package com.milesilac.classreadingstats.ui

import com.milesilac.classreadingstats.model.ClassSection
import com.milesilac.classreadingstats.model.ClassSheet
import com.milesilac.classreadingstats.model.ComprehensionLevel
import com.milesilac.classreadingstats.model.GradeLevel
import com.milesilac.classreadingstats.model.GroupScreeningTest
import com.milesilac.classreadingstats.model.LearnerLevel
import com.milesilac.classreadingstats.model.OralReading
import com.milesilac.classreadingstats.model.ReadingComprehension
import com.milesilac.classreadingstats.model.ReadingTest
import com.milesilac.classreadingstats.model.Student
import com.milesilac.classreadingstats.model.StudentList
import com.milesilac.classreadingstats.model.StudentSexOrient

val dummySections = listOf(
    ClassSection(gradeLevel = GradeLevel.EIGHT, sectionName = "Amethyst"),
    ClassSection(gradeLevel = GradeLevel.EIGHT, sectionName = "Diamond")
)

val dummyStudentListsEightAmethyst = ClassSheet(
    classSection = ClassSection(gradeLevel = GradeLevel.EIGHT, sectionName = "Amethyst"),
    maleStudents = listOf(
        StudentList.Header(sex = StudentSexOrient.MALE),
        StudentList.StudentDetails(
            student = Student(
                orderId = 1.0,
                name = "Alao, John Nathan",
                section = "8-AMETHYST",
                sex = "M",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 13.0,
                        comprehensionLevel = ComprehensionLevel.FIVE
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 3.0,
                        percentage = 97.17,
                        level = LearnerLevel.INDEPENDENT
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 86.0,
                        level = LearnerLevel.INDEPENDENT
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 2.0,
                name = "Alendro, Frellian",
                section = "8-AMETHYST",
                sex = "M",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 13.0,
                        comprehensionLevel = ComprehensionLevel.FIVE
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 8.0,
                        percentage = 92.45,
                        level = LearnerLevel.INSTRUCTIONAL
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 63.0,
                        level = LearnerLevel.INSTRUCTIONAL
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 3.0,
                name = "Bandong, John Paul Mariano",
                section = "8-AMETHYST",
                sex = "M",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 15.0,
                        comprehensionLevel = ComprehensionLevel.FIVE
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 3.0,
                        percentage = 97.17,
                        level = LearnerLevel.INDEPENDENT
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 86.0,
                        level = LearnerLevel.INDEPENDENT
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 4.0,
                name = "Bauca, Romel Ustare",
                section = "8-AMETHYST",
                sex = "M",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 12.0,
                        comprehensionLevel = ComprehensionLevel.FIVE
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 9.0,
                        percentage = 91.51,
                        level = LearnerLevel.INSTRUCTIONAL
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 71.0,
                        level = LearnerLevel.INSTRUCTIONAL
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 5.0,
                name = "Buscayno, Mark Deniel",
                section = "8-AMETHYST",
                sex = "M",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 11.0,
                        comprehensionLevel = ComprehensionLevel.FIVE
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 10.0,
                        percentage = 90.52,
                        level = LearnerLevel.INSTRUCTIONAL
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 71.0,
                        level = LearnerLevel.INSTRUCTIONAL
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 6.0,
                name = "Cabuquit, Raymart Sotto",
                section = "8-AMETHYST",
                sex = "M",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 16.0,
                        comprehensionLevel = ComprehensionLevel.SIX
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 10.0,
                        percentage = 90.52,
                        level = LearnerLevel.INSTRUCTIONAL
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 63.0,
                        level = LearnerLevel.INSTRUCTIONAL
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 7.0,
                name = "Cuyno, Jhullian Jade",
                section = "8-AMETHYST",
                sex = "M",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 7.0,
                        comprehensionLevel = ComprehensionLevel.FIVE
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 10.0,
                        percentage = 90.52,
                        level = LearnerLevel.INSTRUCTIONAL
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 63.0,
                        level = LearnerLevel.INSTRUCTIONAL
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 8.0,
                name = "Daganato, Jerome Soon Jin Riῆos",
                section = "8-AMETHYST",
                sex = "M",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 14.0,
                        comprehensionLevel = ComprehensionLevel.FIVE
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 2.0,
                        percentage = 98.11,
                        level = LearnerLevel.INDEPENDENT
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 86.0,
                        level = LearnerLevel.INDEPENDENT
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 9.0,
                name = "Dela Cruz, Charlie",
                section = "8-AMETHYST",
                sex = "M",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 16.0,
                        comprehensionLevel = ComprehensionLevel.SIX
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 8.0,
                        percentage = 92.45,
                        level = LearnerLevel.INSTRUCTIONAL
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 63.0,
                        level = LearnerLevel.INSTRUCTIONAL
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 10.0,
                name = "Dela cruz, Kurt Yuri Ignacio",
                section = "8-AMETHYST",
                sex = "M",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 16.0,
                        comprehensionLevel = ComprehensionLevel.SIX
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 9.0,
                        percentage = 91.51,
                        level = LearnerLevel.INSTRUCTIONAL
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 63.0,
                        level = LearnerLevel.INSTRUCTIONAL
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 11.0,
                name = "De Otoy, Edwin Cagadas",
                section = "8-AMETHYST",
                sex = "M",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 8.0,
                        comprehensionLevel = ComprehensionLevel.FIVE
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 17.0,
                        percentage = 80.23,
                        level = LearnerLevel.FRUSTRATION
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 43.0,
                        level = LearnerLevel.FRUSTRATION
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 12.0,
                name = "Enano, Andrew John Apas",
                section = "8-AMETHYST",
                sex = "M",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 14.0,
                        comprehensionLevel = ComprehensionLevel.FIVE
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 9.0,
                        percentage = 91.51,
                        level = LearnerLevel.INSTRUCTIONAL
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 71.0,
                        level = LearnerLevel.INSTRUCTIONAL
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 13.0,
                name = "Estrella, Tristan Andrei Sulam",
                section = "8-AMETHYST",
                sex = "M",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 19.0,
                        comprehensionLevel = ComprehensionLevel.FIVE
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 9.0,
                        percentage = 91.51,
                        level = LearnerLevel.INSTRUCTIONAL
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 63.0,
                        level = LearnerLevel.INSTRUCTIONAL
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 14.0,
                name = "Galang, Gian Jaren Tamayo",
                section = "8-AMETHYST",
                sex = "M",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 15.0,
                        comprehensionLevel = ComprehensionLevel.FIVE
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 10.0,
                        percentage = 90.52,
                        level = LearnerLevel.INSTRUCTIONAL
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 71.0,
                        level = LearnerLevel.INSTRUCTIONAL
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 15.0,
                name = "Grijaldo, Sebastian Allen Quian Gutierrez",
                section = "8-AMETHYST",
                sex = "M",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 18.0,
                        comprehensionLevel = ComprehensionLevel.SIX
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 10.0,
                        percentage = 90.52,
                        level = LearnerLevel.INSTRUCTIONAL
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 63.0,
                        level = LearnerLevel.INSTRUCTIONAL
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 16.0,
                name = "Ibaῆez, Prince Nathan",
                section = "8-AMETHYST",
                sex = "M",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 13.0,
                        comprehensionLevel = ComprehensionLevel.FIVE
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 8.0,
                        percentage = 92.45,
                        level = LearnerLevel.INSTRUCTIONAL
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 71.0,
                        level = LearnerLevel.INSTRUCTIONAL
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 17.0,
                name = "Lacson, Angel Mendoza",
                section = "8-AMETHYST",
                sex = "M",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 14.0,
                        comprehensionLevel = ComprehensionLevel.FIVE
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 8.0,
                        percentage = 92.45,
                        level = LearnerLevel.INSTRUCTIONAL
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 100.0,
                        level = LearnerLevel.INDEPENDENT
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 18.0,
                name = "Nocom, Leeiel Briones",
                section = "8-AMETHYST",
                sex = "M",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 12.0,
                        comprehensionLevel = ComprehensionLevel.FIVE
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 8.0,
                        percentage = 92.45,
                        level = LearnerLevel.INSTRUCTIONAL
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 71.0,
                        level = LearnerLevel.INSTRUCTIONAL
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 19.0,
                name = "Ocampo, Brahne Niven Pandaan ",
                section = "8-AMETHYST",
                sex = "M",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 17.0,
                        comprehensionLevel = ComprehensionLevel.SIX
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 3.0,
                        percentage = 97.17,
                        level = LearnerLevel.INDEPENDENT
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 88.0,
                        level = LearnerLevel.INDEPENDENT
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 20.0,
                name = "Pamintuan, Fervinson Massif Rosadeno",
                section = "8-AMETHYST",
                sex = "M",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 6.0,
                        comprehensionLevel = ComprehensionLevel.SIX
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 2.0,
                        percentage = 98.11,
                        level = LearnerLevel.INDEPENDENT
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 86.0,
                        level = LearnerLevel.INDEPENDENT
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 21.0,
                name = "Pepito, Francis Johan Balaguer",
                section = "8-AMETHYST",
                sex = "M",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 13.0,
                        comprehensionLevel = ComprehensionLevel.FIVE
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 2.0,
                        percentage = 98.11,
                        level = LearnerLevel.INDEPENDENT
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 86.0,
                        level = LearnerLevel.INDEPENDENT
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 22.0,
                name = "Sabbun, Aries Leyva",
                section = "8-AMETHYST",
                sex = "M",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 12.0,
                        comprehensionLevel = ComprehensionLevel.FIVE
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 19.0,
                        percentage = 77.91,
                        level = LearnerLevel.FRUSTRATION
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 43.0,
                        level = LearnerLevel.FRUSTRATION
                    )
                ),
            )
        ),
    ),
    femaleStudents = listOf(
        StudentList.Header(sex = StudentSexOrient.FEMALE),
        StudentList.StudentDetails(
            student = Student(
                orderId = 23.0,
                name = "Baldonado, Ameerah Faith Balisay",
                section = "8-DIAMOND",
                sex = "F",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 15.0,
                        comprehensionLevel = ComprehensionLevel.FIVE
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 3.0,
                        percentage = 97.17,
                        level = LearnerLevel.INDEPENDENT
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 100.0,
                        level = LearnerLevel.INDEPENDENT
                    )
                ),
            )
        ),
    )
)

val dummyStudentListsEightDiamond = ClassSheet(
    classSection = ClassSection(gradeLevel = GradeLevel.EIGHT, sectionName = "Diamond"),
    femaleStudents = listOf(
        StudentList.Header(sex = StudentSexOrient.FEMALE),
        StudentList.StudentDetails(
            student = Student(
                orderId = 1.0,
                name = "Baldonado, Ameerah Faith Balisay",
                section = "8-DIAMOND",
                sex = "F",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 15.0,
                        comprehensionLevel = ComprehensionLevel.FIVE
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 3.0,
                        percentage = 97.17,
                        level = LearnerLevel.INDEPENDENT
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 100.0,
                        level = LearnerLevel.INDEPENDENT
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 2.0,
                name = "Barcinas, Maria Angela Galang",
                section = "8-DIAMOND",
                sex = "F",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 10.0,
                        comprehensionLevel = ComprehensionLevel.FIVE
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 7.0,
                        percentage = 93.4,
                        level = LearnerLevel.INSTRUCTIONAL
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 100.0,
                        level = LearnerLevel.INDEPENDENT
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 3.0,
                name = "Dayan- Dayan, Akiko Reign",
                section = "8-DIAMOND",
                sex = "F",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 5.0,
                        comprehensionLevel = ComprehensionLevel.FIVE
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 7.0,
                        percentage = 93.4,
                        level = LearnerLevel.INSTRUCTIONAL
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 100.0,
                        level = LearnerLevel.INDEPENDENT
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 4.0,
                name = "Guiao, Cristine Bumatay",
                section = "8-DIAMOND",
                sex = "F",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 12.0,
                        comprehensionLevel = ComprehensionLevel.FIVE
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 2.0,
                        percentage = 98.11,
                        level = LearnerLevel.INDEPENDENT
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 86.0,
                        level = LearnerLevel.INDEPENDENT
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 5.0,
                name = "Loremas, Jermaine Fernando",
                section = "8-DIAMOND",
                sex = "F",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 9.0,
                        comprehensionLevel = ComprehensionLevel.FIVE
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 9.0,
                        percentage = 91.51,
                        level = LearnerLevel.INSTRUCTIONAL
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 71.0,
                        level = LearnerLevel.INSTRUCTIONAL
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 6.0,
                name = "Lugue, Rhian Joyce Payumo",
                section = "8-DIAMOND",
                sex = "F",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 12.0,
                        comprehensionLevel = ComprehensionLevel.FIVE
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 2.0,
                        percentage = 98.11,
                        level = LearnerLevel.INDEPENDENT
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 71.0,
                        level = LearnerLevel.INSTRUCTIONAL
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 7.0,
                name = "Marzan, Kristina Joy Fernandez",
                section = "8-DIAMOND",
                sex = "F",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 15.0,
                        comprehensionLevel = ComprehensionLevel.FIVE
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 3.0,
                        percentage = 97.17,
                        level = LearnerLevel.INDEPENDENT
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 100.0,
                        level = LearnerLevel.INDEPENDENT
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 8.0,
                name = "Perez, Prynzes Nicole Tiglao",
                section = "8-DIAMOND",
                sex = "F",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 5.0,
                        comprehensionLevel = ComprehensionLevel.FIVE
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 2.0,
                        percentage = 98.11,
                        level = LearnerLevel.INDEPENDENT
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 100.0,
                        level = LearnerLevel.INDEPENDENT
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 9.0,
                name = "Recto, Rachelle Ann",
                section = "8-DIAMOND",
                sex = "F",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 10.0,
                        comprehensionLevel = ComprehensionLevel.FIVE
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 10.0,
                        percentage = 90.56,
                        level = LearnerLevel.INSTRUCTIONAL
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 71.0,
                        level = LearnerLevel.INSTRUCTIONAL
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 10.0,
                name = "Reyes, Amanda Faith Quiboloy",
                section = "8-DIAMOND",
                sex = "F",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 10.0,
                        comprehensionLevel = ComprehensionLevel.FIVE
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 2.0,
                        percentage = 98.11,
                        level = LearnerLevel.INSTRUCTIONAL
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 63.0,
                        level = LearnerLevel.INSTRUCTIONAL
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 11.0,
                name = "Reyes, Fiona Briel Bondoc",
                section = "8-DIAMOND",
                sex = "F",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 7.0,
                        comprehensionLevel = ComprehensionLevel.FIVE
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 2.0,
                        percentage = 98.11,
                        level = LearnerLevel.INDEPENDENT
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 71.0,
                        level = LearnerLevel.INSTRUCTIONAL
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 12.0,
                name = "Rivamonte, Mary Juliet Barrientos",
                section = "8-DIAMOND",
                sex = "F",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 13.0,
                        comprehensionLevel = ComprehensionLevel.FIVE
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 9.0,
                        percentage = 91.51,
                        level = LearnerLevel.INSTRUCTIONAL
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 71.0,
                        level = LearnerLevel.INSTRUCTIONAL
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 13.0,
                name = "Sagad, Keona Zoe Wage",
                section = "8-DIAMOND",
                sex = "F",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 9.0,
                        comprehensionLevel = ComprehensionLevel.FIVE
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 2.0,
                        percentage = 98.11,
                        level = LearnerLevel.INDEPENDENT
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 86.0,
                        level = LearnerLevel.INDEPENDENT
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 14.0,
                name = "Saldivar, Leyarra Janel Basco",
                section = "8-DIAMOND",
                sex = "F",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 13.0,
                        comprehensionLevel = ComprehensionLevel.FIVE
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 3.0,
                        percentage = 97.17,
                        level = LearnerLevel.INDEPENDENT
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 63.0,
                        level = LearnerLevel.INSTRUCTIONAL
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 15.0,
                name = "Sampana, Aira Joy Soliman",
                section = "8-DIAMOND",
                sex = "F",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 10.0,
                        comprehensionLevel = ComprehensionLevel.FIVE
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 9.0,
                        percentage = 91.51,
                        level = LearnerLevel.INSTRUCTIONAL
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 63.0,
                        level = LearnerLevel.INSTRUCTIONAL
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 16.0,
                name = "Tiamzon, Ruslyn Ghin Abahas",
                section = "8-DIAMOND",
                sex = "F",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 18.0,
                        comprehensionLevel = ComprehensionLevel.SIX
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 3.0,
                        percentage = 97.17,
                        level = LearnerLevel.INDEPENDENT
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 63.0,
                        level = LearnerLevel.INSTRUCTIONAL
                    )
                ),
            )
        ),
        StudentList.StudentDetails(
            student = Student(
                orderId = 17.0,
                name = "Villapana, Shanelle Cruz",
                section = "8-DIAMOND",
                sex = "F",
                preTest = ReadingTest(
                    groupScreeningTest = GroupScreeningTest(
                        score = 8.0,
                        comprehensionLevel = ComprehensionLevel.FIVE
                    ),
                    isGradingPassage = true,
                    oralReading = OralReading(
                        totalNumberOfWordsInSelection = 106.0,
                        numberOfMiscues = 3.0,
                        percentage = 97.17,
                        level = LearnerLevel.INDEPENDENT
                    ),
                    readingComprehension = ReadingComprehension(
                        inputPercentage = 86.0,
                        level = LearnerLevel.INDEPENDENT
                    )
                ),
            )
        )
    )
)