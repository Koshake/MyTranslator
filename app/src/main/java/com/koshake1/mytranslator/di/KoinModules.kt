package com.koshake1.mytranslator.di

import com.koshake1.mytranslator.model.data.DataModel
import com.koshake1.mytranslator.model.datasource.RetrofitDataSourceImpl
import com.koshake1.mytranslator.model.datasource.RoomDataSourceImpl
import com.koshake1.mytranslator.model.repository.Repository
import com.koshake1.mytranslator.model.repository.RepositoryImpl
import com.koshake1.mytranslator.view.main.MainInteractor
import com.koshake1.mytranslator.viewmodel.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {

    single<Repository<List<DataModel>>>(named(NAME_REMOTE)) {
        RepositoryImpl(RetrofitDataSourceImpl())
    }

    single<Repository<List<DataModel>>>(named(NAME_LOCAL)) {
        RepositoryImpl(RoomDataSourceImpl())
    }
}

val mainScreen = module {

    factory {
        MainInteractor(get(named(NAME_REMOTE)), get(named(NAME_LOCAL)))
    }
    viewModel {
        MainViewModel(get())
    }
}