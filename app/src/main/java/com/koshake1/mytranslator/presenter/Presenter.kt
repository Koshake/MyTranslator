package com.koshake1.mytranslator.presenter

import com.koshake1.mytranslator.model.data.AppState
import com.koshake1.mytranslator.view.base.MainView

interface Presenter<T : AppState, V : MainView> {

    fun attachView(view: V)

    fun detachView(view: V)

    fun getData(word: String, isOnline: Boolean)
}