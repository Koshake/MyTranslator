package com.koshake1.historyscreen.history

import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import com.koshake1.historyscreen.R

private val TAG = "111"

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "History activity onCreate view ")
        setContentView(R.layout.activity_history)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_history, HistoryFragment())
                .commit()
        }
    }
}