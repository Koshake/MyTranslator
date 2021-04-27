package com.koshake1.mytranslator.model.repository

import android.provider.ContactsContract
import com.koshake1.mytranslator.model.data.DataModel
import com.koshake1.mytranslator.model.datasource.IDataSource
import io.reactivex.Observable

class RepositoryImpl(private val dataSource : IDataSource<List<DataModel>>) : Repository<List<DataModel>> {

    override suspend fun getData(text: String): List<DataModel> {
        return dataSource.getData(text)
    }
}