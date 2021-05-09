package com.koshake1.mytranslator.model.repository

interface Repository<T> {

    suspend fun getData(text : String) : T
}