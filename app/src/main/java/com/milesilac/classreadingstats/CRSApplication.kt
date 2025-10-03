package com.milesilac.classreadingstats

import android.app.Application
import android.content.Context
import org.opencv.android.OpenCVLoader

class CRSApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        OpenCVLoader.initLocal()
        instance = this
    }

    companion object {
        private lateinit var instance: CRSApplication

        val context: Context
            get() = instance.applicationContext
    }
}