package com.koshake1.mytranslator.model.datasource

import com.koshake1.mytranslator.model.data.AppState
import com.koshake1.mytranslator.model.data.DataModel
import com.koshake1.mytranslator.room.HistoryDao
import com.koshake1.mytranslator.room.HistoryEntity

class RoomDataSourceImpl(private val historyDao: HistoryDao) : IDataSourceLocal<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return historyDao.all().map {
            DataModel(it.word, null)
        }
    }

    override suspend fun saveToDB(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                appState.dataModel?.let {
                    historyDao.insert(HistoryEntity(it[0].text!!, null))
                }
            }
            else -> null
        }
    }
}