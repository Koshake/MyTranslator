package com.koshake1.mytranslator.application

import android.app.Application
import com.koshake1.mytranslator.di.application
import com.koshake1.mytranslator.di.historyScreen
import com.koshake1.mytranslator.di.mainScreen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TranslatorApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(listOf(application, mainScreen, historyScreen))
        }
    }
}