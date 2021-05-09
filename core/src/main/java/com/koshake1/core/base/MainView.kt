package com.koshake1.core.base

import com.koshake1.model.data.AppState

interface MainView {
    fun renderData(appState: AppState)
}