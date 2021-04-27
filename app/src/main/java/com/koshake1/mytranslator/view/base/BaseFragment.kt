package com.koshake1.mytranslator.view.base

import androidx.fragment.app.Fragment
import com.koshake1.mytranslator.model.data.AppState
import com.koshake1.mytranslator.viewmodel.BaseViewModel
import com.koshake1.mytranslator.viewmodel.Interactor

abstract class BaseFragment<T : AppState, I : Interactor<T>> : Fragment(), MainView {

    abstract override fun renderData(appState: AppState)

    abstract val model : BaseViewModel<T>

}