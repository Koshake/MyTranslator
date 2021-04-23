package com.koshake1.mytranslator.model.data

sealed class AppState {
    data class Success(val dataModel: List<DataModel>?) : AppState()
    data class Error(val error: Throwable) : AppState()
    data class Loading(val progress: Int?) : AppState()
}
