package com.milesilac.classreadingstats.service

import android.content.ContentResolver
import android.content.ContentValues
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.milesilac.classreadingstats.model.ClassSheet
import com.milesilac.classreadingstats.model.toGradeLevelInt
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.VerticalAlignment
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook


@RequiresApi(Build.VERSION_CODES.Q)
fun exportNewFileToExcel(
    contentResolver: ContentResolver,
    classBook: List<ClassSheet>,
    onToast: (String) -> Unit = {}
) {

    val newBook = XSSFWorkbook()
    val cellStyle = newBook.createCellStyle().apply {
        alignment = HorizontalAlignment.CENTER
        verticalAlignment = VerticalAlignment.CENTER
    }

    classBook.forEach {
        val hasPostTest = false //redo model for this
        val classSheet = newBook.createSheet("${it.classSection.gradeLevel.toGradeLevelInt()}-${it.classSection.sectionName}").apply {
            createLabels(
                rowStartIndex = 0,
                cellStyle = cellStyle,
                hasPostTest = hasPostTest
            )
        }

        var lastRowIndex = 3 // ideally rowStartIndex + 3
        //TODO actual data logic; lastRowIndex should be updated with this

        val newLastRowIndex = lastRowIndex + 2
        classSheet.createLabels(
            rowStartIndex = newLastRowIndex,
            cellStyle = cellStyle,
            hasPostTest = hasPostTest
        )
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

fun XSSFSheet.createLabels(
    rowStartIndex: Int, // theoretically rowStartIndex for male table is 0
    cellStyle: CellStyle,
    hasPostTest: Boolean
): XSSFSheet {
    val classSheet = this
    val zeroRow = classSheet.createRow(rowStartIndex)
    zeroRow.createCell(0).apply {
        setCellValue("No.")
        setCellStyle(cellStyle)
    }
    zeroRow.createCell(1).apply {
        setCellValue("Name")
        setCellStyle(cellStyle)
    }
    zeroRow.createCell(2).apply {
        setCellValue("Pre-Test")
        setCellStyle(cellStyle)
    }
    if (hasPostTest) {
        zeroRow.createCell(10).apply {
            setCellValue("Post-Test")
            setCellStyle(cellStyle)
        }
        zeroRow.createCell(18).apply {
            setCellValue("Reading Profile")
            setCellStyle(cellStyle)
        }
    } else {
        zeroRow.createCell(10).apply {
            setCellValue("Reading Profile")
            setCellStyle(cellStyle)
        }
    }


    val oneRow = classSheet.createRow(rowStartIndex + 1)
    oneRow.createCell(2).apply {
        setCellValue("Group Screening Test")
        setCellStyle(cellStyle)
    }
    oneRow.createCell(4).apply {
        setCellValue("Graded Passage")
        setCellStyle(cellStyle)
    }
    if (hasPostTest) {
        oneRow.createCell(10).apply {
            setCellValue("Group Screening Test")
            setCellStyle(cellStyle)
        }
        oneRow.createCell(12).apply {
            setCellValue("Graded Passage")
            setCellStyle(cellStyle)
        }
    }


    val twoRow = classSheet.createRow(rowStartIndex + 2)
    twoRow.createCell(1).apply {
        setCellValue("Last Name, First Name, Middle Name")
        setCellStyle(cellStyle)
    }
    twoRow.createCell(4).apply {
        setCellValue("Oral Reading")
        setCellStyle(cellStyle)
    }
    twoRow.createCell(8).apply {
        setCellValue("Reading Comprehension")
        setCellStyle(cellStyle)
    }
    if (hasPostTest) {
        twoRow.createCell(12).apply {
            setCellValue("Oral Reading")
            setCellStyle(cellStyle)
        }
        twoRow.createCell(16).apply {
            setCellValue("Reading Comprehension")
            setCellStyle(cellStyle)
        }
    }

    val maleOrFemale = when (rowStartIndex) {
        0 -> "Male"
        else -> "Female"
    }

    val threeRow = classSheet.createRow(rowStartIndex + 3)
    threeRow.createCell(1).apply {
        setCellValue(maleOrFemale)
        setCellStyle(cellStyle)
    }
    threeRow.createCell(2).apply {
        setCellValue("Score")
        setCellStyle(cellStyle)
    }
    threeRow.createCell(3).apply {
        setCellValue("Comprehension Level")
        setCellStyle(cellStyle)
    }
    threeRow.createCell(4).apply {
        setCellValue("Number of Miscues")
        setCellStyle(cellStyle)
    }
    threeRow.createCell(5).apply {
        setCellValue("Total Number of Words")
        setCellStyle(cellStyle)
    }
    threeRow.createCell(6).apply {
        setCellValue("Percentage")
        setCellStyle(cellStyle)
    }
    threeRow.createCell(7).apply {
        setCellValue("Reading Level")
        setCellStyle(cellStyle)
    }
    threeRow.createCell(8).apply {
        setCellValue("Percentage")
        setCellStyle(cellStyle)
    }
    threeRow.createCell(9).apply {
        setCellValue("Reading Level")
        setCellStyle(cellStyle)
    }
    if (hasPostTest) {
        threeRow.createCell(10).apply {
            setCellValue("Score")
            setCellStyle(cellStyle)
        }
        threeRow.createCell(11).apply {
            setCellValue("Comprehension Level")
            setCellStyle(cellStyle)
        }
        threeRow.createCell(12).apply {
            setCellValue("Number of Miscues")
            setCellStyle(cellStyle)
        }
        threeRow.createCell(13).apply {
            setCellValue("Total Number of Words")
            setCellStyle(cellStyle)
        }
        threeRow.createCell(14).apply {
            setCellValue("Percentage")
            setCellStyle(cellStyle)
        }
        threeRow.createCell(15).apply {
            setCellValue("Reading Level")
            setCellStyle(cellStyle)
        }
        threeRow.createCell(16).apply {
            setCellValue("Percentage")
            setCellStyle(cellStyle)
        }
        threeRow.createCell(17).apply {
            setCellValue("Reading Level")
            setCellStyle(cellStyle)
        }
    }


    // MERGE LOGIC
    classSheet.addMergedRegion(
        CellRangeAddress(rowStartIndex,rowStartIndex + 3,0,0)
    ) // No. merge
    classSheet.addMergedRegion(
        CellRangeAddress(rowStartIndex,rowStartIndex + 1,1,1)
    ) // Name merge
    classSheet.addMergedRegion(
        CellRangeAddress(rowStartIndex,rowStartIndex,2,9)
    ) // Pre-Test merge
    classSheet.addMergedRegion(
        CellRangeAddress(rowStartIndex + 1,rowStartIndex + 2,2,3)
    ) // Pre-Test Group Screening Test merge
    classSheet.addMergedRegion(
        CellRangeAddress(rowStartIndex + 1,rowStartIndex + 1,4,9)
    ) // Pre-Test Graded Passage merge
    classSheet.addMergedRegion(
        CellRangeAddress(rowStartIndex + 2,rowStartIndex + 2,4,7)
    ) // Pre-Test Oral Reading merge
    classSheet.addMergedRegion(
        CellRangeAddress(rowStartIndex + 2,rowStartIndex + 2,8,9)
    ) // Pre-Test Reading Comprehension merge
    if (hasPostTest) {
        classSheet.addMergedRegion(
            CellRangeAddress(rowStartIndex,rowStartIndex,10,17)
        ) // Post-Test merge
        classSheet.addMergedRegion(
            CellRangeAddress(rowStartIndex,rowStartIndex + 3,18,18)
        ) // Reading Profile merge
        classSheet.addMergedRegion(
            CellRangeAddress(rowStartIndex + 1,rowStartIndex + 2,10,11)
        ) // Post-Test Group Screening Test merge
        classSheet.addMergedRegion(
            CellRangeAddress(rowStartIndex + 1,rowStartIndex + 1,12,17)
        ) // Post-Test Graded Passage merge
        classSheet.addMergedRegion(
            CellRangeAddress(rowStartIndex + 2,rowStartIndex + 2,12,15)
        ) // Post-Test Oral Reading merge
        classSheet.addMergedRegion(
            CellRangeAddress(rowStartIndex + 2,rowStartIndex + 2,16,17)
        ) // Post-Test Reading Comprehension merge
    } else {
        classSheet.addMergedRegion(
            CellRangeAddress(rowStartIndex,rowStartIndex + 3,10,10)
        ) // Reading Profile merge
    }

    if (rowStartIndex == 0) { //the following logic should now be called once when rowStartIndex == initialRowStartIndex
        for (columnIndex in 0..30) {
            when (columnIndex) {
                0 -> classSheet.setColumnWidth(columnIndex, (5*265))
                1 -> classSheet.setColumnWidth(columnIndex, (30*265))
                else -> classSheet.setColumnWidth(columnIndex, (20*265))
            }
        }
    }

    return classSheet
}