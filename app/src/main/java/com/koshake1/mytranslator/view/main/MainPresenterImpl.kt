package com.koshake1.mytranslator.view.main

import com.koshake1.mytranslator.model.data.AppState
import com.koshake1.mytranslator.model.datasource.RetrofitDataSourceImpl
import com.koshake1.mytranslator.model.datasource.RoomDataSourceImpl
import com.koshake1.mytranslator.model.repository.RepositoryImpl
import com.koshake1.mytranslator.presenter.Presenter
import com.koshake1.mytranslator.rx.SchedulerProvider
import com.koshake1.mytranslator.view.base.MainView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class MainPresenterImpl<T : AppState, V : MainView>(
    private val interactor: MainInteractor = MainInteractor(
        RepositoryImpl(RetrofitDataSourceImpl()),
        RepositoryImpl(RoomDataSourceImpl())
    ),
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    protected val schedulerProvider: SchedulerProvider = SchedulerProvider()
) : Presenter<T, V> {

    private var currentView: V? = null

    override fun attachView(view: V) {
        if (view != currentView) {
            currentView = view
        }
    }

    override fun detachView(view: V) {
        compositeDisposable.clear()
        if (view == currentView) {
            currentView = null
        }
    }

    override fun getData(word: String, isOnline: Boolean) {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { currentView?.renderData(AppState.Loading(null)) }
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {

            override fun onNext(appState: AppState) {
                currentView?.renderData(appState)
            }

            override fun onError(e: Throwable) {
                currentView?.renderData(AppState.Error(e))
            }

            override fun onComplete() {
            }
        }
    }
}