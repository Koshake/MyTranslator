package com.koshake1.mytranslator.model.repository

import io.reactivex.Observable

interface Repository<T> {

    suspend fun getData(text : String) : T
}