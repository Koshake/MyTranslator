package com.koshake1.mytranslator.model.datasource

import io.reactivex.Observable

interface IDataSource<T> {

    fun getData(word : String) : Observable<T>
}