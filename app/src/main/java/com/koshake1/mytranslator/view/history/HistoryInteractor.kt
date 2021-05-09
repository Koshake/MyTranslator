package com.koshake1.mytranslator.view.history

import com.koshake1.mytranslator.model.data.AppState
import com.koshake1.mytranslator.model.data.DataModel
import com.koshake1.mytranslator.model.repository.Repository
import com.koshake1.mytranslator.model.repository.RepositoryLocal
import com.koshake1.mytranslator.viewmodel.Interactor

class HistoryInteractor(
    private val remoteRepository: Repository<List<DataModel>>,
    private val localRepository: RepositoryLocal<List<DataModel>>
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