package com.koshake1.historyscreen.history

import com.koshake1.core.viewmodel.Interactor
import com.koshake1.model.data.AppState
import com.koshake1.model.data.DataModel
import com.koshake1.mytranslator.model.repository.Repository
import com.koshake1.mytranslator.model.repository.RepositoryLocal

class HistoryInteractor(
    private val localRepository: RepositoryLocal<List<DataModel>>,
    private val remoteRepository: Repository<List<DataModel>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(
            if (fromRemoteSource) {
                remoteRepository
            } else {
                localRepository
            }.getData(word)
        )
    }
}