package com.koshake1.mytranslator.model.datasource

import com.koshake1.mytranslator.model.data.DataModel
import io.reactivex.Observable

class RoomDataSourceImpl : IDataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        TODO("Not yet implemented")
    }
}