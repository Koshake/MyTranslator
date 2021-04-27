package com.koshake1.mytranslator.di

import android.content.Context
import com.koshake1.mytranslator.view.main.MainFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        InteractorModule::class,
        RepositoryModule::class,
        ViewModelModule::class
    ]
)

interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance

        fun appComponent(context: Context): Builder

        fun build(): AppComponent
    }

    fun inject(fragment: MainFragment)
}