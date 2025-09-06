package com.milesilac.classreadingstats

import android.app.Application
import android.content.Context

class CRSApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private lateinit var instance: CRSApplication

        val context: Context
            get() = instance.applicationContext
    }
}