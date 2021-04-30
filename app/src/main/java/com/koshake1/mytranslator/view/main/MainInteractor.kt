package com.koshake1.mytranslator.view.main

import com.koshake1.core.viewmodel.Interactor
import com.koshake1.model.data.AppState
import com.koshake1.model.data.DataModel
import com.koshake1.mytranslator.model.repository.Repository
import com.koshake1.mytranslator.model.repository.RepositoryLocal

class MainInteractor(
    private val remoteRepository: Repository<List<DataModel>>,
    private val localRepository: RepositoryLocal<List<DataModel>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        val appState : AppState
            if (fromRemoteSource) {
                appState = AppState.Success(remoteRepository.getData(word))
                localRepository.saveToDB(appState)
            } else {
                appState = AppState.Success(localRepository.getData(word))
            }
        return appState
    }
}