package com.koshake1.mytranslator.viewmodel

interface Interactor<T> {

    suspend fun getData(word : String, fromRemoteSource : Boolean): T
}