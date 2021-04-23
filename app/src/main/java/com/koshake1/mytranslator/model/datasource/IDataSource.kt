package com.koshake1.mytranslator.model.datasource

import io.reactivex.Observable

interface IDataSource<T> {

    suspend fun getData(word : String) : T
}