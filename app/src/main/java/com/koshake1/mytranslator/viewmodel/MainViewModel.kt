package com.koshake1.mytranslator.viewmodel

import com.koshake1.core.viewmodel.BaseViewModel
import com.koshake1.model.data.AppState
import com.koshake1.mytranslator.view.main.MainInteractor
import kotlinx.coroutines.*

class MainViewModel(
    private val interactor: MainInteractor
) : BaseViewModel<AppState>() {

    override fun getData(word: String, isOnline: Boolean) {
        liveDataForViewToObserve.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch {
            startIneractor(word, isOnline)
        }
    }

    private suspend fun startIneractor(word: String, isOnline: Boolean) {
        withContext(Dispatchers.IO) {
            liveDataForViewToObserve.postValue(interactor.getData(word, isOnline))
        }
    }

    override fun onCleared() {
        cancelJob()
        super.onCleared()
    }

    override fun handleError(error: Throwable) {
        liveDataForViewToObserve.postValue(AppState.Error(error))
    }
}