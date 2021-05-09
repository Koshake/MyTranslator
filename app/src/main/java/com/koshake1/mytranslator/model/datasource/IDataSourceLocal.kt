package com.koshake1.mytranslator.model.datasource

import com.koshake1.model.data.AppState


interface IDataSourceLocal<T> : IDataSource<T> {

    suspend fun saveToDB(appState: AppState)
}