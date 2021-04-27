package com.koshake1.mytranslator.model.repository

import com.koshake1.mytranslator.model.data.AppState
import com.koshake1.mytranslator.model.data.DataModel
import com.koshake1.mytranslator.model.datasource.IDataSourceLocal

class RepositoryLocalImpl(private val dataSource: IDataSourceLocal<List<DataModel>>) : RepositoryLocal<List<DataModel>> {
    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }

    override suspend fun saveToDB(appState: AppState) {
        dataSource.saveToDB(appState)
    }
}