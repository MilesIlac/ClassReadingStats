package com.milesilac.classreadingstats.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.milesilac.classreadingstats.ui.dummyStudentListsEightAmethyst
import com.milesilac.classreadingstats.ui.screens.HomePage

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var currentSheetList by remember { mutableStateOf(dummyStudentListsEightAmethyst) }

            HomePage(
//                currentSheetList = currentSheetList,
//                onChangeSheet = {
//                    currentSheetList = if (currentSheetList == dummyStudentListsEightAmethyst) {
//                        dummyStudentListsEightDiamond
//                    } else dummyStudentListsEightAmethyst
//                }
            )

        }
    }

}