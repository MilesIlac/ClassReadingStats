package com.milesilac.classreadingstats.service

import android.content.ContentResolver
import android.content.ContentValues
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.milesilac.classreadingstats.model.ClassSheet
import com.milesilac.classreadingstats.model.toGradeLevelInt
import org.apache.poi.xssf.usermodel.XSSFWorkbook


@RequiresApi(Build.VERSION_CODES.Q)
fun exportNewFileToExcel(
    contentResolver: ContentResolver,
    classBook: List<ClassSheet>,
    onToast: (String) -> Unit = {}
) {

    val newBook = XSSFWorkbook()

    classBook.forEach {
        val classSheet = newBook.createSheet("${it.classSection.gradeLevel.toGradeLevelInt()}-${it.classSection.sectionName}")
        val labelRow = classSheet.createRow(0)
        labelRow.createCell(0).setCellValue("Name")
        labelRow.createCell(1).setCellValue("Pre-Test")
        labelRow.createCell(2).setCellValue("Post-Test")
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