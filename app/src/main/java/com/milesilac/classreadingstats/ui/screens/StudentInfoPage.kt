package com.milesilac.classreadingstats.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun StudentInfoPage() {
    Column(
        modifier = Modifier
            .background(color= Color.Green)
            .fillMaxSize()
    ) {}
}

@Preview
@Composable
fun StudentInfoPagePreview() {
    StudentInfoPage()
}