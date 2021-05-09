package com.koshake1.historyscreen.history

import com.koshake1.mytranslator.di.NAME_LOCAL
import com.koshake1.mytranslator.di.NAME_REMOTE
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun injectHistoryDependencies() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(listOf(historyScreen))
}

val historyScreen = module {
    factory { HistoryViewModel(get()) }
    factory { HistoryInteractor(get(named(NAME_LOCAL)), get(named(NAME_REMOTE))) }
}