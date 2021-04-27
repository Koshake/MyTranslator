package com.koshake1.mytranslator.application

import android.app.Application
import com.koshake1.mytranslator.di.AppComponent
import com.koshake1.mytranslator.di.DaggerAppComponent

class TranslatorApp : Application() {

    override fun onCreate() {
        super.onCreate()
        val component = DaggerAppComponent.builder().appComponent(this).build()
        TranslatorApp.component = component
    }

    companion object {
        lateinit var  component : AppComponent
    }
}