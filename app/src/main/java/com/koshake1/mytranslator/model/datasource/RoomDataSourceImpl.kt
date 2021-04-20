package com.koshake1.mytranslator.model.datasource

import com.koshake1.mytranslator.model.data.DataModel
import io.reactivex.Observable

class RoomDataSourceImpl : IDataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> {
        TODO("Not yet implemented")
    }
}