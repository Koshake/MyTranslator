package com.koshake1.mytranslator.viewmodel

import com.koshake1.mytranslator.model.data.AppState
import com.koshake1.mytranslator.model.data.DataModel
import io.reactivex.Observable
import retrofit2.http.GET

interface Interactor<T> {

    fun getData(word : String, fromRemoteSource : Boolean): Observable<AppState>
}