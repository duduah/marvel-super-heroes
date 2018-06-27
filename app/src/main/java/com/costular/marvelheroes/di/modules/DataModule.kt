package com.costular.marvelheroes.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import com.costular.marvelheroes.data.db.MarvelHeroesDataBase
import com.costular.marvelheroes.data.model.mapper.MarvelHeroMapper
import com.costular.marvelheroes.data.net.MarvelHeroesService
import com.costular.marvelheroes.data.repository.MarvelHeroesRepositoryImpl
import com.costular.marvelheroes.data.repository.datasource.LocalMarvelHeroesDataSource
import com.costular.marvelheroes.data.repository.datasource.RemoteMarvelHeroesDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by costular on 17/03/2018.
 */
@Module
class DataModule {

    @Provides
    @Singleton
    fun provideMarvelHeroMapper(): MarvelHeroMapper = MarvelHeroMapper()

    @Provides
    @Singleton
    fun provideRemoteMarvelHeroesDataSource(marvelHeroesService: MarvelHeroesService,
                                            marvelHeroMapper: MarvelHeroMapper)
            : RemoteMarvelHeroesDataSource =
            RemoteMarvelHeroesDataSource(marvelHeroesService, marvelHeroMapper)

    @Provides
    @Singleton
    fun provideDatabase(context: Context) : MarvelHeroesDataBase =
            Room.databaseBuilder(context, MarvelHeroesDataBase::class.java, "marvelheroes.db").build()

    @Provides
    @Singleton
    fun provideLocalMarvelHeroesDatasource(dataBase: MarvelHeroesDataBase) : LocalMarvelHeroesDataSource =
            LocalMarvelHeroesDataSource(dataBase)

    @Provides
    @Singleton
    fun provideMarvelHeroesRepository(localMarvelHeroesDataSource: LocalMarvelHeroesDataSource,
                                      marvelRemoteMarvelHeroesDataSource: RemoteMarvelHeroesDataSource)
            : MarvelHeroesRepositoryImpl =
            MarvelHeroesRepositoryImpl(localMarvelHeroesDataSource,
                    marvelRemoteMarvelHeroesDataSource)

}