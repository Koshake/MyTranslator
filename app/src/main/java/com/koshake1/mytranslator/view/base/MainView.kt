package com.koshake1.mytranslator.view.base

import com.koshake1.mytranslator.model.data.AppState

interface MainView {
    fun renderData(appState: AppState)
}