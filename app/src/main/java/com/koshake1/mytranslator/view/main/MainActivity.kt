package com.koshake1.mytranslator.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.koshake1.mytranslator.R
import com.koshake1.mytranslator.application.TranslatorApp

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "mainTag"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "MainActivity onCreate ")
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment())
                .commitNow()
        }
    }
}