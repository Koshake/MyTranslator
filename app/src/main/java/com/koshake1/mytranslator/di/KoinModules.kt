package com.koshake1.mytranslator.di

import androidx.room.Room
import com.koshake1.model.data.DataModel
import com.koshake1.mytranslator.model.datasource.RetrofitDataSourceImpl
import com.koshake1.mytranslator.model.datasource.RoomDataSourceImpl
import com.koshake1.mytranslator.model.repository.Repository
import com.koshake1.mytranslator.model.repository.RepositoryImpl
import com.koshake1.mytranslator.model.repository.RepositoryLocal
import com.koshake1.mytranslator.model.repository.RepositoryLocalImpl
import com.koshake1.mytranslator.room.HistoryDatabase
import com.koshake1.mytranslator.view.main.MainFragment
import com.koshake1.mytranslator.view.main.MainInteractor
import com.koshake1.mytranslator.viewmodel.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun injectDependencies() = loadModules

private val loadModules by lazy {
    loadKoinModules(listOf(application, mainScreen))
}

val application = module {

    single {
        Room.databaseBuilder(get(), HistoryDatabase::class.java, "HistoryDB").build()
    }

    single {
        get<HistoryDatabase>().historyDao()
    }

    single<Repository<List<DataModel>>>(named(NAME_REMOTE)) {
        RepositoryImpl(RetrofitDataSourceImpl())
    }

    single<RepositoryLocal<List<DataModel>>>(named(NAME_LOCAL)) {
        RepositoryLocalImpl(RoomDataSourceImpl(get()))
    }
}

val mainScreen = module {
    scope(named<MainFragment>()) {
        scoped {
            MainInteractor(get(named(NAME_REMOTE)), get(named(NAME_LOCAL)))
        }
        viewModel {
            MainViewModel(get())
        }
    }
}