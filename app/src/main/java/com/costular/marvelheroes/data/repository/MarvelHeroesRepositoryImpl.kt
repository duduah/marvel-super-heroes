package com.costular.marvelheroes.data.repository

import com.costular.marvelheroes.data.repository.datasource.LocalMarvelHeroesDataSource
import com.costular.marvelheroes.data.repository.datasource.RemoteMarvelHeroesDataSource
import com.costular.marvelheroes.domain.model.MarvelHeroEntity
import io.reactivex.Observable


class MarvelHeroesRepositoryImpl(private val localMarvelHeroesDataSource: LocalMarvelHeroesDataSource,
                                 private val remoteMarvelHeroesDataSource: RemoteMarvelHeroesDataSource)
    : MarvelHeroesRepository {

    override fun getMarvelHeroesList(): Observable<List<MarvelHeroEntity>> =
            getMarvelHeroesFromDb()
                    .concatWith(getMarvelHeroesFromApi())

    private fun getMarvelHeroesFromDb() : Observable<List<MarvelHeroEntity>> =
            localMarvelHeroesDataSource.getMarvelHeroesList()

    private fun getMarvelHeroesFromApi() : Observable<List<MarvelHeroEntity>> =
        remoteMarvelHeroesDataSource.getMarvelHeroesList()
                .doOnNext { localMarvelHeroesDataSource.saveMavelHeroesList(it) }

}