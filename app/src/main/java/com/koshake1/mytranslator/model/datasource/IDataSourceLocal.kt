package com.koshake1.mytranslator.model.datasource

import com.koshake1.mytranslator.model.data.AppState
import javax.sql.DataSource

interface IDataSourceLocal<T> : IDataSource<T> {

    suspend fun saveToDB(appState: AppState)
}