package com.koshake1.mytranslator.model.datasource

interface IDataSource<T> {

    suspend fun getData(word : String) : T
}