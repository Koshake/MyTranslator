package com.koshake1.core.viewmodel

interface Interactor<T> {

    suspend fun getData(word : String, fromRemoteSource : Boolean): T
}