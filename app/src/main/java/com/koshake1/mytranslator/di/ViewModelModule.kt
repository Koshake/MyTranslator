package com.koshake1.mytranslator.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.koshake1.mytranslator.viewmodel.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    internal  abstract  fun bindViewModelFactory(factory: ViewModelFactory) : ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    protected  abstract  fun mainViewModel(mainViewModel: MainViewModel) : ViewModel

}