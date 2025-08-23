package com.milesilac.classreadingstats.service

import android.content.ContentResolver
import android.content.ContentValues
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.milesilac.classreadingstats.model.ClassSheet
import com.milesilac.classreadingstats.model.LearnerLevel
import com.milesilac.classreadingstats.model.StudentList
import com.milesilac.classreadingstats.model.calculateLearnerOverallReadingProfile
import com.milesilac.classreadingstats.model.toLearnerLevelString
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.VerticalAlignment
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook


@RequiresApi(Build.VERSION_CODES.Q)
fun exportNewFileToExcel(
    contentResolver: ContentResolver,
    classBook: List<ClassSheet>,
    onToast: (String) -> Unit = {}
) {
    val newBook = XSSFWorkbook()
    val numberDataFormat = newBook.createDataFormat()
    val cellStyle = newBook.createCellStyle().apply {
        alignment = HorizontalAlignment.CENTER
        verticalAlignment = VerticalAlignment.CENTER
    }
    val orPercentCellStyle = newBook.createCellStyle().apply {
        alignment = HorizontalAlignment.CENTER
        verticalAlignment = VerticalAlignment.CENTER
        dataFormat = numberDataFormat.getFormat("0.00")
    }
    val rcPercentCellStyle = newBook.createCellStyle().apply {
        alignment = HorizontalAlignment.CENTER
        verticalAlignment = VerticalAlignment.CENTER
        dataFormat = numberDataFormat.getFormat("0.0")
    }

    classBook.forEach {
        val hasPostTest = false //redo model for this
        var lastRowIndex = 0

        val classSheet = newBook.createSheet("${it.classSection.gradeLevel.grade}-${it.classSection.sectionName}")

        classSheet.createInfoTable(
            rowStartIndex = lastRowIndex,
            maleOrFemale = "MALE",
            cellStyle = cellStyle,
        )
        classSheet.createGradeLabels(
            rowStartIndex = lastRowIndex,
            maleOrFemale = "MALE",
            cellStyle = cellStyle,
            hasPostTest = hasPostTest
        )
        lastRowIndex += 4

        for (student in it.maleStudents) {
            if (student is StudentList.StudentDetails) {
                addStudentNameAndNo(
                    classSheet = classSheet,
                    rowIndex = lastRowIndex,
                    cellStyle = cellStyle,
                    studentOrderId = student.student.orderId,
                    studentName = student.student.name
                )
                val pretestNumberOfMiscues = student.student.preTest.oralReading?.numberOfMiscues ?: -1.0
                val pretestTotalNumberOfWords = student.student.preTest.oralReading?.totalNumberOfWordsInSelection ?: -1.0
                val pretestORPercentage = student.student.preTest.oralReading?.percentage ?: -1.0
                val pretestORLevel = student.student.preTest.oralReading?.level ?: LearnerLevel.ERROR
                val pretestRCPercentage = student.student.preTest.readingComprehension?.inputPercentage ?: -1.0
                val pretestRCLevel = student.student.preTest.readingComprehension?.level ?: LearnerLevel.ERROR
                addStudentGrades(
                    classSheet = classSheet,
                    rowIndex = lastRowIndex,
                    cellStyle = cellStyle,
                    orPercentCellStyle = orPercentCellStyle,
                    rcPercentCellStyle = rcPercentCellStyle,
                    hasPostTest = hasPostTest,
                    studentGSTScore = student.student.preTest.groupScreeningTest.score,
                    studentGSTComprehensionLevel = student.student.preTest.groupScreeningTest.comprehensionLevel.level,
                    studentORNumberOfMiscues = pretestNumberOfMiscues,
                    studentORTotalNumberOfWords = pretestTotalNumberOfWords,
                    studentORPercentage = pretestORPercentage,
                    studentORLevel = pretestORLevel.toLearnerLevelString(),
                    studentRCPercentage = pretestRCPercentage,
                    studentRCLevel = pretestRCLevel.toLearnerLevelString(),
                    studentOverallReadingProfile = calculateLearnerOverallReadingProfile(orLevel = pretestORLevel, rcLevel = pretestRCLevel).toLearnerLevelString()
                )
                lastRowIndex++
            }
        }

        lastRowIndex += 2
        classSheet.createInfoTable(
            rowStartIndex = lastRowIndex,
            maleOrFemale = "FEMALE",
            cellStyle = cellStyle,
        )
        classSheet.createGradeLabels(
            rowStartIndex = lastRowIndex,
            maleOrFemale = "FEMALE",
            cellStyle = cellStyle,
            hasPostTest = hasPostTest
        )
        lastRowIndex += 4

        for (student in it.femaleStudents) {
            if (student is StudentList.StudentDetails) {
                addStudentNameAndNo(
                    classSheet = classSheet,
                    rowIndex = lastRowIndex,
                    cellStyle = cellStyle,
                    studentOrderId = student.student.orderId,
                    studentName = student.student.name
                )
                val pretestNumberOfMiscues = student.student.preTest.oralReading?.numberOfMiscues ?: -1.0
                val pretestTotalNumberOfWords = student.student.preTest.oralReading?.totalNumberOfWordsInSelection ?: -1.0
                val pretestORPercentage = student.student.preTest.oralReading?.percentage ?: -1.0
                val pretestORLevel = student.student.preTest.oralReading?.level ?: LearnerLevel.ERROR
                val pretestRCPercentage = student.student.preTest.readingComprehension?.inputPercentage ?: -1.0
                val pretestRCLevel = student.student.preTest.readingComprehension?.level ?: LearnerLevel.ERROR
                addStudentGrades(
                    classSheet = classSheet,
                    rowIndex = lastRowIndex,
                    cellStyle = cellStyle,
                    orPercentCellStyle = orPercentCellStyle,
                    rcPercentCellStyle = rcPercentCellStyle,
                    hasPostTest = hasPostTest,
                    studentGSTScore = student.student.preTest.groupScreeningTest.score,
                    studentGSTComprehensionLevel = student.student.preTest.groupScreeningTest.comprehensionLevel.level,
                    studentORNumberOfMiscues = pretestNumberOfMiscues,
                    studentORTotalNumberOfWords = pretestTotalNumberOfWords,
                    studentORPercentage = pretestORPercentage,
                    studentORLevel = pretestORLevel.toLearnerLevelString(),
                    studentRCPercentage = pretestRCPercentage,
                    studentRCLevel = pretestRCLevel.toLearnerLevelString(),
                    studentOverallReadingProfile = calculateLearnerOverallReadingProfile(orLevel = pretestORLevel, rcLevel = pretestRCLevel).toLearnerLevelString()
                )
                lastRowIndex++
            }
        }
    }

    val contentValues = ContentValues().apply {
        put(MediaStore.Downloads.DISPLAY_NAME, "workbook.xlsx")
        put(MediaStore.Downloads.MIME_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
        put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        put(MediaStore.Downloads.IS_PENDING, 1)
    }

    val uri = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

    uri?.let { thisUri ->
        contentResolver.openOutputStream(thisUri)?.let { thisOutputStream ->
            thisOutputStream.use {
                newBook.write(it)
            }
        }

        contentValues.clear()
        contentValues.put(MediaStore.Downloads.IS_PENDING, 0)
        contentResolver.update(thisUri, contentValues, null, null)
        onToast("Excel saved to Downloads")
    } ?: run {
        onToast("Failed to save file")
    }

    newBook.close()
}

private fun XSSFSheet.getOrCreateRow(rowIndex: Int): XSSFRow {
    return this.getRow(rowIndex) ?: this.createRow(rowIndex)
}

fun XSSFSheet.createInfoTable(
    rowStartIndex: Int,
    maleOrFemale: String,
    cellStyle: CellStyle,
): XSSFSheet {
    val classSheet = this
    val zeroRow = classSheet.getOrCreateRow(rowStartIndex)
    zeroRow.createCell(0).apply {
        setCellValue("No.".uppercase())
        setCellStyle(cellStyle)
    }
    zeroRow.createCell(1).apply {
        setCellValue("Name".uppercase())
        setCellStyle(cellStyle)
    }
    zeroRow.createCell(2).apply {
        setCellValue("Birthday".uppercase())
        setCellStyle(cellStyle)
    }
    zeroRow.createCell(3).apply {
        setCellValue("Age".uppercase())
        setCellStyle(cellStyle)
    }
    zeroRow.createCell(4).apply {
        setCellValue("Complete Home Address".uppercase())
        setCellStyle(cellStyle)
    }
    zeroRow.createCell(5).apply {
        setCellValue("Last School Attended".uppercase())
        setCellStyle(cellStyle)
    }
    zeroRow.createCell(6).apply {
        setCellValue("Messenger Account".uppercase())
        setCellStyle(cellStyle)
    }
    zeroRow.createCell(7).apply {
        setCellValue("Contact Number".uppercase())
        setCellStyle(cellStyle)
    }


    val twoRow = classSheet.getOrCreateRow(rowStartIndex + 2)
    twoRow.createCell(1).apply {
        setCellValue("(Last Name, First Name, Middle Name)")
        setCellStyle(cellStyle)
    }


    val threeRow = classSheet.getOrCreateRow(rowStartIndex + 3)
    threeRow.createCell(1).apply {
        setCellValue(maleOrFemale.uppercase())
        setCellStyle(cellStyle)
    }
    threeRow.createCell(2).apply {
        setCellValue("(Month, Day, Year)")
        setCellStyle(cellStyle)
    }


    // MERGE LOGIC
    classSheet.addMergedRegion(
        CellRangeAddress(rowStartIndex,rowStartIndex + 3,0,0)
    ) // No. merge
    classSheet.addMergedRegion(
        CellRangeAddress(rowStartIndex,rowStartIndex + 1,1,1)
    ) // Name merge
    classSheet.addMergedRegion(
        CellRangeAddress(rowStartIndex,rowStartIndex + 2,2,2)
    ) // Birthday merge
    classSheet.addMergedRegion(
        CellRangeAddress(rowStartIndex,rowStartIndex + 3,3,3)
    ) // Age merge
    classSheet.addMergedRegion(
        CellRangeAddress(rowStartIndex,rowStartIndex + 3,4,4)
    ) // Complete Home Address merge
    classSheet.addMergedRegion(
        CellRangeAddress(rowStartIndex,rowStartIndex + 3,5,5)
    ) // Last School Attended merge
    classSheet.addMergedRegion(
        CellRangeAddress(rowStartIndex,rowStartIndex + 3,6,6)
    ) // Messenger Account merge
    classSheet.addMergedRegion(
        CellRangeAddress(rowStartIndex,rowStartIndex + 3,7,7)
    ) // Contact Number merge

    if (rowStartIndex == 0) { //the following logic should now be called once when rowStartIndex == initialRowStartIndex
        for (columnIndex in 0..7) {
            when (columnIndex) {
                0 -> classSheet.setColumnWidth(columnIndex, (5*265))
                1,5,6 -> classSheet.setColumnWidth(columnIndex, (40*265))
                3 -> classSheet.setColumnWidth(columnIndex, (10*265))
                4 -> classSheet.setColumnWidth(columnIndex, (80*265))
                else -> classSheet.setColumnWidth(columnIndex, (20*265))
            }
        }
    }

    return classSheet
}

fun XSSFSheet.createGradeLabels(
    rowStartIndex: Int, // theoretically rowStartIndex for male table is 0
    maleOrFemale: String,
    cellStyle: CellStyle,
    hasPostTest: Boolean
): XSSFSheet {
    val classSheet = this
    val zeroRow = classSheet.getOrCreateRow(rowStartIndex)
    zeroRow.createCell(10).apply {
        setCellValue("No.".uppercase())
        setCellStyle(cellStyle)
    }
    zeroRow.createCell(11).apply {
        setCellValue("Name".uppercase())
        setCellStyle(cellStyle)
    }
    zeroRow.createCell(12).apply {
        setCellValue("Pre-Test".uppercase())
        setCellStyle(cellStyle)
    }
    if (hasPostTest) {
        zeroRow.createCell(20).apply {
            setCellValue("Post-Test".uppercase())
            setCellStyle(cellStyle)
        }
        zeroRow.createCell(28).apply {
            setCellValue("Reading Profile".uppercase())
            setCellStyle(cellStyle)
        }
    } else {
        zeroRow.createCell(20).apply {
            setCellValue("Reading Profile".uppercase())
            setCellStyle(cellStyle)
        }
    }


    val oneRow = classSheet.getOrCreateRow(rowStartIndex + 1)
    oneRow.createCell(12).apply {
        setCellValue("Group Screening Test".uppercase())
        setCellStyle(cellStyle)
    }
    oneRow.createCell(14).apply {
        setCellValue("Graded Passage".uppercase())
        setCellStyle(cellStyle)
    }
    if (hasPostTest) {
        oneRow.createCell(20).apply {
            setCellValue("Group Screening Test".uppercase())
            setCellStyle(cellStyle)
        }
        oneRow.createCell(22).apply {
            setCellValue("Graded Passage".uppercase())
            setCellStyle(cellStyle)
        }
    }


    val twoRow = classSheet.getOrCreateRow(rowStartIndex + 2)
    twoRow.createCell(11).apply {
        setCellValue("Last Name, First Name, Middle Name")
        setCellStyle(cellStyle)
    }
    twoRow.createCell(14).apply {
        setCellValue("Oral Reading".uppercase())
        setCellStyle(cellStyle)
    }
    twoRow.createCell(18).apply {
        setCellValue("Reading Comprehension".uppercase())
        setCellStyle(cellStyle)
    }
    if (hasPostTest) {
        twoRow.createCell(22).apply {
            setCellValue("Oral Reading".uppercase())
            setCellStyle(cellStyle)
        }
        twoRow.createCell(26).apply {
            setCellValue("Reading Comprehension".uppercase())
            setCellStyle(cellStyle)
        }
    }


    val threeRow = classSheet.getOrCreateRow(rowStartIndex + 3)
    threeRow.createCell(11).apply {
        setCellValue(maleOrFemale.uppercase())
        setCellStyle(cellStyle)
    }
    threeRow.createCell(12).apply {
        setCellValue("Score".uppercase())
        setCellStyle(cellStyle)
    }
    threeRow.createCell(13).apply {
        setCellValue("Comprehension Level".uppercase())
        setCellStyle(cellStyle)
    }
    threeRow.createCell(14).apply {
        setCellValue("Number of Miscues".uppercase())
        setCellStyle(cellStyle)
    }
    threeRow.createCell(15).apply {
        setCellValue("Total Number of Words".uppercase())
        setCellStyle(cellStyle)
    }
    threeRow.createCell(16).apply {
        setCellValue("Percentage".uppercase())
        setCellStyle(cellStyle)
    }
    threeRow.createCell(17).apply {
        setCellValue("Reading Level".uppercase())
        setCellStyle(cellStyle)
    }
    threeRow.createCell(18).apply {
        setCellValue("Percentage".uppercase())
        setCellStyle(cellStyle)
    }
    threeRow.createCell(19).apply {
        setCellValue("Reading Level".uppercase())
        setCellStyle(cellStyle)
    }
    if (hasPostTest) {
        threeRow.createCell(20).apply {
            setCellValue("Score".uppercase())
            setCellStyle(cellStyle)
        }
        threeRow.createCell(21).apply {
            setCellValue("Comprehension Level".uppercase())
            setCellStyle(cellStyle)
        }
        threeRow.createCell(22).apply {
            setCellValue("Number of Miscues".uppercase())
            setCellStyle(cellStyle)
        }
        threeRow.createCell(23).apply {
            setCellValue("Total Number of Words".uppercase())
            setCellStyle(cellStyle)
        }
        threeRow.createCell(24).apply {
            setCellValue("Percentage".uppercase())
            setCellStyle(cellStyle)
        }
        threeRow.createCell(25).apply {
            setCellValue("Reading Level".uppercase())
            setCellStyle(cellStyle)
        }
        threeRow.createCell(26).apply {
            setCellValue("Percentage".uppercase())
            setCellStyle(cellStyle)
        }
        threeRow.createCell(27).apply {
            setCellValue("Reading Level".uppercase())
            setCellStyle(cellStyle)
        }
    }


    // MERGE LOGIC
    classSheet.addMergedRegion(
        CellRangeAddress(rowStartIndex,rowStartIndex + 3,10,10)
    ) // No. merge
    classSheet.addMergedRegion(
        CellRangeAddress(rowStartIndex,rowStartIndex + 1,11,11)
    ) // Name merge
    classSheet.addMergedRegion(
        CellRangeAddress(rowStartIndex,rowStartIndex,12,19)
    ) // Pre-Test merge
    classSheet.addMergedRegion(
        CellRangeAddress(rowStartIndex + 1,rowStartIndex + 2,12,13)
    ) // Pre-Test Group Screening Test merge
    classSheet.addMergedRegion(
        CellRangeAddress(rowStartIndex + 1,rowStartIndex + 1,14,19)
    ) // Pre-Test Graded Passage merge
    classSheet.addMergedRegion(
        CellRangeAddress(rowStartIndex + 2,rowStartIndex + 2,14,17)
    ) // Pre-Test Oral Reading merge
    classSheet.addMergedRegion(
        CellRangeAddress(rowStartIndex + 2,rowStartIndex + 2,18,19)
    ) // Pre-Test Reading Comprehension merge
    if (hasPostTest) {
        classSheet.addMergedRegion(
            CellRangeAddress(rowStartIndex,rowStartIndex,20,27)
        ) // Post-Test merge
        classSheet.addMergedRegion(
            CellRangeAddress(rowStartIndex,rowStartIndex + 3,28,28)
        ) // Reading Profile merge
        classSheet.addMergedRegion(
            CellRangeAddress(rowStartIndex + 1,rowStartIndex + 2,20,21)
        ) // Post-Test Group Screening Test merge
        classSheet.addMergedRegion(
            CellRangeAddress(rowStartIndex + 1,rowStartIndex + 1,22,27)
        ) // Post-Test Graded Passage merge
        classSheet.addMergedRegion(
            CellRangeAddress(rowStartIndex + 2,rowStartIndex + 2,22,25)
        ) // Post-Test Oral Reading merge
        classSheet.addMergedRegion(
            CellRangeAddress(rowStartIndex + 2,rowStartIndex + 2,26,27)
        ) // Post-Test Reading Comprehension merge
    } else {
        classSheet.addMergedRegion(
            CellRangeAddress(rowStartIndex,rowStartIndex + 3,20,20)
        ) // Reading Profile merge
    }

    if (rowStartIndex == 0) { //the following logic should now be called once when rowStartIndex == initialRowStartIndex
        for (columnIndex in 10..40) {
            when (columnIndex) {
                10 -> classSheet.setColumnWidth(columnIndex, (5*265))
                11 -> classSheet.setColumnWidth(columnIndex, (40*265))
                13,14,15 -> classSheet.setColumnWidth(columnIndex, (30*265))
                else -> classSheet.setColumnWidth(columnIndex, (20*265))
            }
        }
    }

    return classSheet
}

fun addStudentNameAndNo(
    classSheet: XSSFSheet,
    rowIndex: Int,
    cellStyle: CellStyle,
    studentOrderId: Double,
    studentName: String,
) {
    val studentRow = classSheet.getOrCreateRow(rowIndex)
    studentRow.createCell(0).apply {
        setCellValue(studentOrderId)
        setCellStyle(cellStyle)
    }
    studentRow.createCell(1).apply {
        setCellValue(studentName)
        setCellStyle(cellStyle)
    }
    studentRow.createCell(10).apply {
        setCellValue(studentOrderId)
        setCellStyle(cellStyle)
    }
    studentRow.createCell(11).apply {
        setCellValue(studentName)
        setCellStyle(cellStyle)
    }
}

fun addStudentGrades(
    classSheet: XSSFSheet,
    rowIndex: Int,
    cellStyle: CellStyle,
    orPercentCellStyle: CellStyle,
    rcPercentCellStyle: CellStyle,
    hasPostTest: Boolean,
    studentGSTScore: Double,
    studentGSTComprehensionLevel: String,
    studentORNumberOfMiscues: Double,
    studentORTotalNumberOfWords: Double,
    studentORPercentage: Double,
    studentORLevel: String,
    studentRCPercentage: Double,
    studentRCLevel: String,
    studentOverallReadingProfile: String,
) {
    val studentRow = classSheet.getOrCreateRow(rowIndex)
    studentRow.createCell(12).apply {
        setCellValue(studentGSTScore)
        setCellStyle(cellStyle)
    }
    studentRow.createCell(13).apply {
        setCellValue(studentGSTComprehensionLevel)
        setCellStyle(cellStyle)
    }
    studentRow.createCell(14).apply {
        if (studentORNumberOfMiscues > -1) {
            setCellValue(studentORNumberOfMiscues)
        } else setCellValue("")
        setCellStyle(cellStyle)
    }
    studentRow.createCell(15).apply {
        if (studentORTotalNumberOfWords > -1) {
            setCellValue(studentORTotalNumberOfWords)
        } else setCellValue("")
        setCellStyle(cellStyle)
    }
    studentRow.createCell(16).apply {
        if (studentORPercentage != -0.01) {
            setCellValue(studentORPercentage)
        } else setCellValue("")
        setCellStyle(orPercentCellStyle)
    }
    studentRow.createCell(17).apply {
        setCellValue(studentORLevel)
        setCellStyle(cellStyle)
    }
    studentRow.createCell(18).apply {
        if (studentRCPercentage > -1) {
            setCellValue(studentRCPercentage)
        } else setCellValue("")
        setCellStyle(rcPercentCellStyle)
    }
    studentRow.createCell(19).apply {
        setCellValue(studentRCLevel)
        setCellStyle(cellStyle)
    }
    studentRow.createCell(20).apply {
        setCellValue(studentOverallReadingProfile)
        setCellStyle(cellStyle)
    }
}