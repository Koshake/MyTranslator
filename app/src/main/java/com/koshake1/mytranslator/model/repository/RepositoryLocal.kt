package com.koshake1.mytranslator.model.repository

import com.koshake1.mytranslator.model.data.AppState
interface RepositoryLocal<T> : Repository<T> {

    suspend fun saveToDB(appState: AppState)
}
