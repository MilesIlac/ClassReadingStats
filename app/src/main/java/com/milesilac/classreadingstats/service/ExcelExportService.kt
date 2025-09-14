package com.milesilac.classreadingstats.service

import android.content.ContentResolver
import android.content.ContentValues
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.milesilac.classreadingstats.model.ClassSheet
import com.milesilac.classreadingstats.model.test.GroupScreeningTest
import com.milesilac.classreadingstats.model.level.LearnerLevel
import com.milesilac.classreadingstats.model.StudentList
import com.milesilac.classreadingstats.model.level.calculateGrandOverallReadingProfile
import com.milesilac.classreadingstats.model.level.calculateLearnerOverallReadingProfile
import com.milesilac.classreadingstats.model.toSectionString
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
        wrapText = true
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
        var lastRowIndex = 0

        val classSheet = newBook.createSheet(it.classSection.toSectionString(isSectionNameUpperCased = false))

        classSheet.createInfoTable(
            rowStartIndex = lastRowIndex,
            maleOrFemale = "MALE",
            cellStyle = cellStyle,
        )
        classSheet.createGradeLabels(
            rowStartIndex = lastRowIndex,
            maleOrFemale = "MALE",
            cellStyle = cellStyle
        )
        lastRowIndex += 4

        for (studentEntry in it.maleStudents) {
            if (studentEntry is StudentList.StudentDetails) {
                val student = studentEntry.student
                addStudentNameAndNo(
                    classSheet = classSheet,
                    rowIndex = lastRowIndex,
                    cellStyle = cellStyle,
                    studentOrderId = student.orderId,
                    studentName = student.name
                )
                addStudentGrades(
                    classSheet = classSheet,
                    rowIndex = lastRowIndex,
                    cellStyle = cellStyle,
                    orPercentCellStyle = orPercentCellStyle,
                    rcPercentCellStyle = rcPercentCellStyle,
                    studentGST = student.gst,
                    studentPreORNumberOfMiscues = student.preTest.oralReading.numberOfMiscues,
                    studentPreORTotalNumberOfWords = student.preTest.oralReading.totalNumberOfWordsInSelection,
                    studentPreORPercentage = student.preTest.oralReading.percentage,
                    studentPreORLevel = student.preTest.oralReading.level,
                    studentPreRCPercentage = student.preTest.readingComprehension.inputPercentage,
                    studentPreRCLevel = student.preTest.readingComprehension.level,
                    studentPostORNumberOfMiscues = student.postTest?.oralReading?.numberOfMiscues ?: -1.0,
                    studentPostORTotalNumberOfWords = student.postTest?.oralReading?.totalNumberOfWordsInSelection ?: -1.0,
                    studentPostORPercentage = student.postTest?.oralReading?.percentage ?: -1.0,
                    studentPostORLevel = student.postTest?.oralReading?.level ?: LearnerLevel.ERROR,
                    studentPostRCPercentage = student.postTest?.readingComprehension?.inputPercentage ?: -1.0,
                    studentPostRCLevel = student.postTest?.readingComprehension?.level ?: LearnerLevel.ERROR
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
            cellStyle = cellStyle
        )
        lastRowIndex += 4

        for (studentEntry in it.femaleStudents) {
            if (studentEntry is StudentList.StudentDetails) {
                val student = studentEntry.student
                addStudentNameAndNo(
                    classSheet = classSheet,
                    rowIndex = lastRowIndex,
                    cellStyle = cellStyle,
                    studentOrderId = student.orderId,
                    studentName = student.name
                )
                addStudentGrades(
                    classSheet = classSheet,
                    rowIndex = lastRowIndex,
                    cellStyle = cellStyle,
                    orPercentCellStyle = orPercentCellStyle,
                    rcPercentCellStyle = rcPercentCellStyle,
                    studentGST = student.gst,
                    studentPreORNumberOfMiscues = student.preTest.oralReading.numberOfMiscues,
                    studentPreORTotalNumberOfWords = student.preTest.oralReading.totalNumberOfWordsInSelection,
                    studentPreORPercentage = student.preTest.oralReading.percentage,
                    studentPreORLevel = student.preTest.oralReading.level,
                    studentPreRCPercentage = student.preTest.readingComprehension.inputPercentage,
                    studentPreRCLevel = student.preTest.readingComprehension.level,
                    studentPostORNumberOfMiscues = student.postTest?.oralReading?.numberOfMiscues ?: -1.0,
                    studentPostORTotalNumberOfWords = student.postTest?.oralReading?.totalNumberOfWordsInSelection ?: -1.0,
                    studentPostORPercentage = student.postTest?.oralReading?.percentage ?: -1.0,
                    studentPostORLevel = student.postTest?.oralReading?.level ?: LearnerLevel.ERROR,
                    studentPostRCPercentage = student.postTest?.readingComprehension?.inputPercentage ?: -1.0,
                    studentPostRCLevel = student.postTest?.readingComprehension?.level ?: LearnerLevel.ERROR
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
    cellStyle: CellStyle
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
    zeroRow.createCell(20).apply {
        setCellValue("Pre-Test Reading Profile".uppercase())
        setCellStyle(cellStyle)
    }
    zeroRow.createCell(21).apply {
        setCellValue("Post-Test".uppercase())
        setCellStyle(cellStyle)
    }
    zeroRow.createCell(27).apply {
        setCellValue("Post-Test Reading Profile".uppercase())
        setCellStyle(cellStyle)
    }
    zeroRow.createCell(28).apply {
        setCellValue("Overall Reading Profile".uppercase())
        setCellStyle(cellStyle)
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
    oneRow.createCell(21).apply {
        setCellValue("Graded Passage".uppercase())
        setCellStyle(cellStyle)
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
    twoRow.createCell(21).apply {
        setCellValue("Oral Reading".uppercase())
        setCellStyle(cellStyle)
    }
    twoRow.createCell(26).apply {
        setCellValue("Reading Comprehension".uppercase())
        setCellStyle(cellStyle)
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
    threeRow.createCell(21).apply {
        setCellValue("Number of Miscues".uppercase())
        setCellStyle(cellStyle)
    }
    threeRow.createCell(22).apply {
        setCellValue("Total Number of Words".uppercase())
        setCellStyle(cellStyle)
    }
    threeRow.createCell(23).apply {
        setCellValue("Percentage".uppercase())
        setCellStyle(cellStyle)
    }
    threeRow.createCell(24).apply {
        setCellValue("Reading Level".uppercase())
        setCellStyle(cellStyle)
    }
    threeRow.createCell(25).apply {
        setCellValue("Percentage".uppercase())
        setCellStyle(cellStyle)
    }
    threeRow.createCell(26).apply {
        setCellValue("Reading Level".uppercase())
        setCellStyle(cellStyle)
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
    classSheet.addMergedRegion(
        CellRangeAddress(rowStartIndex,rowStartIndex + 3,20,20)
    ) // Pre-Test Reading Profile merge
    classSheet.addMergedRegion(
        CellRangeAddress(rowStartIndex,rowStartIndex,21,26)
    ) // Post-Test merge
    classSheet.addMergedRegion(
        CellRangeAddress(rowStartIndex + 1,rowStartIndex + 1,21,26)
    ) // Post-Test Graded Passage merge
    classSheet.addMergedRegion(
        CellRangeAddress(rowStartIndex + 2,rowStartIndex + 2,21,24)
    ) // Post-Test Oral Reading merge
    classSheet.addMergedRegion(
        CellRangeAddress(rowStartIndex + 2,rowStartIndex + 2,25,26)
    ) // Post-Test Reading Comprehension merge
    classSheet.addMergedRegion(
        CellRangeAddress(rowStartIndex,rowStartIndex + 3,27,27)
    ) // Post-Test Reading Profile merge
    classSheet.addMergedRegion(
        CellRangeAddress(rowStartIndex,rowStartIndex + 3,28,28)
    ) // Grand Overall Reading Profile merge

    if (rowStartIndex == 0) { //the following logic should now be called once when rowStartIndex == initialRowStartIndex
        for (columnIndex in 10..40) {
            when (columnIndex) {
                10 -> classSheet.setColumnWidth(columnIndex, (5*265))
                11 -> classSheet.setColumnWidth(columnIndex, (40*265))
                13,14,15,21,22,23 -> classSheet.setColumnWidth(columnIndex, (30*265))
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
    studentGST: GroupScreeningTest,
    studentPreORNumberOfMiscues: Double,
    studentPreORTotalNumberOfWords: Double,
    studentPreORPercentage: Double,
    studentPreORLevel: LearnerLevel,
    studentPreRCPercentage: Double,
    studentPreRCLevel: LearnerLevel,
    studentPostORNumberOfMiscues: Double,
    studentPostORTotalNumberOfWords: Double,
    studentPostORPercentage: Double,
    studentPostORLevel: LearnerLevel,
    studentPostRCPercentage: Double,
    studentPostRCLevel: LearnerLevel,
) {
    val studentGSTScore = studentGST.score
    val studentGSTComprehensionLevel = studentGST.comprehensionLevel.level
    val studentIsGSTPassed = studentGST.shouldGradePassage().not()

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
        if (studentPreORNumberOfMiscues > -1) {
            setCellValue(studentPreORNumberOfMiscues)
        } else setCellValue("")
        setCellStyle(cellStyle)
    }
    studentRow.createCell(15).apply {
        if (studentPreORTotalNumberOfWords > -1) {
            setCellValue(studentPreORTotalNumberOfWords)
        } else setCellValue("")
        setCellStyle(cellStyle)
    }
    studentRow.createCell(16).apply {
        if (studentPreORPercentage != -0.01) {
            setCellValue(studentPreORPercentage)
        } else setCellValue("")
        setCellStyle(orPercentCellStyle)
    }
    studentRow.createCell(17).apply {
        setCellValue(studentPreORLevel.level)
        setCellStyle(cellStyle)
    }
    studentRow.createCell(18).apply {
        if (studentPreRCPercentage > -1) {
            setCellValue(studentPreRCPercentage)
        } else setCellValue("")
        setCellStyle(rcPercentCellStyle)
    }
    studentRow.createCell(19).apply {
        setCellValue(studentPreRCLevel.level)
        setCellStyle(cellStyle)
    }
    val studentPreTestOverallReadingProfile = calculateLearnerOverallReadingProfile(
        isGSTPassed = studentIsGSTPassed,
        orLevel = studentPreORLevel,
        rcLevel = studentPreRCLevel
    )
    studentRow.createCell(20).apply {
        setCellValue(studentPreTestOverallReadingProfile.level)
        setCellStyle(cellStyle)
    }
    studentRow.createCell(21).apply {
        if (studentPostORNumberOfMiscues > -1) {
            setCellValue(studentPostORNumberOfMiscues)
        } else setCellValue("")
        setCellStyle(cellStyle)
    }
    studentRow.createCell(22).apply {
        if (studentPostORTotalNumberOfWords > -1) {
            setCellValue(studentPostORTotalNumberOfWords)
        } else setCellValue("")
        setCellStyle(cellStyle)
    }
    studentRow.createCell(23).apply {
        if (studentPostORPercentage != -0.01) {
            setCellValue(studentPostORPercentage)
        } else setCellValue("")
        setCellStyle(orPercentCellStyle)
    }
    studentRow.createCell(24).apply {
        setCellValue(studentPostORLevel.level)
        setCellStyle(cellStyle)
    }
    studentRow.createCell(25).apply {
        if (studentPostRCPercentage > -1) {
            setCellValue(studentPostRCPercentage)
        } else setCellValue("")
        setCellStyle(rcPercentCellStyle)
    }
    studentRow.createCell(26).apply {
        setCellValue(studentPostRCLevel.level)
        setCellStyle(cellStyle)
    }
    val studentPostTestOverallReadingProfile = calculateLearnerOverallReadingProfile(
        orLevel = studentPostORLevel,
        rcLevel = studentPostRCLevel
    )
    studentRow.createCell(27).apply {
        setCellValue(studentPostTestOverallReadingProfile.level)
        setCellStyle(cellStyle)
    }
    val studentGrandOverallReadingProfile = calculateGrandOverallReadingProfile(
        isPreTestOnly = studentPostTestOverallReadingProfile.level.isEmpty(),
        overallPreTest = studentPreTestOverallReadingProfile,
        overallPostTest = studentPostTestOverallReadingProfile
    )
    studentRow.createCell(28).apply {
        setCellValue(studentGrandOverallReadingProfile.level)
        setCellStyle(cellStyle)
    }
}