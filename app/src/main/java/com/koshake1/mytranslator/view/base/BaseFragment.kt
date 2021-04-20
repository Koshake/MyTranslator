package com.koshake1.mytranslator.view.base

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.koshake1.mytranslator.model.data.AppState
import com.koshake1.mytranslator.presenter.Presenter

abstract class BaseFragment<T : AppState> : Fragment(), MainView {

    companion object {
        const val TAG = "base tag"
    }

    protected lateinit var presenter: Presenter<T, MainView>

    protected abstract fun createPresenter(): Presenter<T, MainView>

    abstract override fun renderData(appState: AppState)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "on Activity created ")
        presenter = createPresenter()
        presenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView(this)
    }
}