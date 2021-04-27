package com.koshake1.mytranslator.di

import com.koshake1.mytranslator.model.data.DataModel
import com.koshake1.mytranslator.model.datasource.IDataSource
import com.koshake1.mytranslator.model.datasource.RetrofitDataSourceImpl
import com.koshake1.mytranslator.model.datasource.RoomDataSourceImpl
import com.koshake1.mytranslator.model.repository.Repository
import com.koshake1.mytranslator.model.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideRepositoryRemote(
        @Named(NAME_REMOTE) dataSourceRemote : IDataSource<List<DataModel>>
    ) : Repository<List<DataModel>> = RepositoryImpl(dataSourceRemote)

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideRepositoryLocal(
        @Named(NAME_LOCAL) dataSourceRemote : IDataSource<List<DataModel>>
    ) : Repository<List<DataModel>> = RepositoryImpl(dataSourceRemote)

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideDataSourceRemote(
    ) : IDataSource<List<DataModel>> = RetrofitDataSourceImpl()

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideDataSourceLocal(
    ) : IDataSource<List<DataModel>> = RoomDataSourceImpl()
}