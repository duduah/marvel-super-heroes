package com.costular.marvelheroes.data.repository

import android.text.BoringLayout
import com.costular.marvelheroes.data.repository.datasource.LocalMarvelHeroesDataSource
import com.costular.marvelheroes.data.repository.datasource.RemoteMarvelHeroesDataSource
import com.costular.marvelheroes.domain.model.MarvelHeroEntity
import io.reactivex.Observable


class MarvelHeroesRepositoryImpl(private val localMarvelHeroesDataSource: LocalMarvelHeroesDataSource,
                                 private val remoteMarvelHeroesDataSource: RemoteMarvelHeroesDataSource)
    : MarvelHeroesRepository {

    override fun updateMarvelHeroFavourite(marvelHero: MarvelHeroEntity): Observable<Int> =
        localMarvelHeroesDataSource.updateMarvelHeroFavourite(marvelHero)

    override fun getMarvelHeroesList(): Observable<List<MarvelHeroEntity>> =
            getMarvelHeroesFromDb()
                    .concatWith(getMarvelHeroesFromApi())

    private fun getMarvelHeroesFromDb() : Observable<List<MarvelHeroEntity>> =
            localMarvelHeroesDataSource.getMarvelHeroesList()

    private fun getMarvelHeroesFromApi() : Observable<List<MarvelHeroEntity>> =
        remoteMarvelHeroesDataSource.getMarvelHeroesList()
                .doOnNext { localMarvelHeroesDataSource.saveMarvelHeroesList(it) }

}