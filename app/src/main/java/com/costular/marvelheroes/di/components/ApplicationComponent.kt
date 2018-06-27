package com.costular.marvelheroes.di.components

import android.content.Context
import com.costular.marvelheroes.data.net.MarvelHeroesService
import com.costular.marvelheroes.data.repository.MarvelHeroesRepository
import com.costular.marvelheroes.di.modules.ApplicationModule
import com.costular.marvelheroes.di.modules.DataModule
import com.costular.marvelheroes.di.modules.NetModule
import com.costular.marvelheroes.presentation.heroeslist.HeroesListActivity
import com.costular.marvelheroes.presentation.util.Navigator
import com.costular.marvelheroes.util.mvvm.ViewModelModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [
    ApplicationModule::class,
    NetModule::class,
    DataModule::class,
    ViewModelModule::class
])
interface ApplicationComponent {

    fun getContext(): Context
    fun getRepository(): MarvelHeroesRepository
    fun getHeroService(): MarvelHeroesService
    fun getNavigator(): Navigator

    fun inject(marvelListActivity: HeroesListActivity)

}