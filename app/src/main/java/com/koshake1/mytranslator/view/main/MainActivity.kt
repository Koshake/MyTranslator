package com.koshake1.mytranslator.view.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.koshake1.mytranslator.R

private const val REQUEST_CODE = 42

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "mainTag"
    }

    private lateinit var appUpdateManager: AppUpdateManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "MainActivity onCreate ")
        setContentView(R.layout.activity_main)
        checkForUpdates()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, MainFragment())
                .commit()
        }
    }

    override fun onResume() {
        super.onResume()
        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    popupSnackbarForCompleteUpdate()
                }
                if (appUpdateInfo.updateAvailability()
                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
                ) {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        IMMEDIATE,
                        this,
                        REQUEST_CODE
                    )
                }
            }
    }

    private val stateUpdatedListener: InstallStateUpdatedListener =
        InstallStateUpdatedListener { state ->
            state?.let {
                if (it.installStatus() == InstallStatus.DOWNLOADED) {
                    popupSnackbarForCompleteUpdate()
                }
            }
        }

    private fun checkForUpdates() {
        appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateIntent ->
            if (appUpdateIntent.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateIntent.isUpdateTypeAllowed(IMMEDIATE)
            ) {
                appUpdateManager.registerListener(stateUpdatedListener)
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateIntent,
                    IMMEDIATE,
                    this,
                    REQUEST_CODE
                )
            }
        }
    }

    private fun popupSnackbarForCompleteUpdate() {
        Snackbar.make(
            findViewById(R.id.container),
            "An update has just been downloaded.",
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("RESTART") { appUpdateManager.completeUpdate() }
            show()
        }
    }
}