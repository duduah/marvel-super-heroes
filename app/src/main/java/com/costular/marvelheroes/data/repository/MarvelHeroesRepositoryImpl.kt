package com.costular.marvelheroes.data.repository

import com.costular.marvelheroes.data.repository.datasource.LocalMarvelHeroesDataSource
import com.costular.marvelheroes.data.repository.datasource.RemoteMarvelHeroesDataSource
import com.costular.marvelheroes.domain.model.MarvelHeroEntity
import io.reactivex.Observable


class MarvelHeroesRepositoryImpl(private val localMarvelHeroesDataSource: LocalMarvelHeroesDataSource,
                                 private val remoteMarvelHeroesDataSource: RemoteMarvelHeroesDataSource)
    : MarvelHeroesRepository {

    override fun getMarvelHeroesList(): Observable<List<MarvelHeroEntity>> =
            getMarvelHeroesFromApi()
                    .mergeWith(getMarvelHeroesFromDb())

    override fun getMarvelHero(marvelHeroId: String): Observable<MarvelHeroEntity> =
            getMarvelHeroFromDb(marvelHeroId)
                    .switchIfEmpty(getMarvelHeroFromApi(marvelHeroId))

    override fun updateMarvelHero(marvelHeroEntity: MarvelHeroEntity): Observable<Int> =
            localMarvelHeroesDataSource.updateMarvelHeroFavourite(marvelHeroEntity)



    private fun getMarvelHeroesFromDb() : Observable<List<MarvelHeroEntity>> =
            localMarvelHeroesDataSource.getMarvelHeroesList()

    private fun getMarvelHeroesFromApi() : Observable<List<MarvelHeroEntity>> =
        remoteMarvelHeroesDataSource.getMarvelHeroesList()
                .doOnNext { localMarvelHeroesDataSource.saveMarvelHeroesList(it) }

    private fun getMarvelHeroFromDb(marvelHeroName: String) : Observable<MarvelHeroEntity> =
            localMarvelHeroesDataSource.getMarvelHero(marvelHeroName)

    private fun getMarvelHeroFromApi(marvelHeroName: String) : Observable<MarvelHeroEntity> =
            remoteMarvelHeroesDataSource.getMarvelHero(marvelHeroName)



}