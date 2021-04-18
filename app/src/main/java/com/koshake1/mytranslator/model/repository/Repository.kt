package com.koshake1.mytranslator.model.repository

import io.reactivex.Observable

interface Repository<T> {

    fun getData(text : String) : Observable<T>
}