package com.koshake1.mytranslator.viewmodel

import com.koshake1.core.viewmodel.BaseViewModel
import com.koshake1.model.data.AppState
import com.koshake1.mytranslator.view.history.HistoryInteractor
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val interactor: HistoryInteractor
) : BaseViewModel<AppState>() {

    override fun onCleared() {
        cancelJob()
        super.onCleared()
    }

    override fun getData(word: String, isOnline: Boolean) {
        liveDataForViewToObserve.postValue(AppState.Loading(null))
        cancelJob()
        viewModelCoroutineScope.launch {
            startInteractor(word, isOnline)
        }
    }

    override fun handleError(error: Throwable) {
        liveDataForViewToObserve.postValue(AppState.Error(error))
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) {
        liveDataForViewToObserve.postValue(interactor.getData(word, isOnline))
    }
}