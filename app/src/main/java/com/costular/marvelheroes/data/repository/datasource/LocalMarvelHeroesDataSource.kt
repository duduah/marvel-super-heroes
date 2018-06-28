package com.costular.marvelheroes.data.repository.datasource

import com.costular.marvelheroes.data.db.MarvelHeroesDataBase
import com.costular.marvelheroes.domain.model.MarvelHeroEntity
import io.reactivex.Observable

class LocalMarvelHeroesDataSource(private val marvelHeroesDataBase: MarvelHeroesDataBase) : MarvelHeroesDataSource {

    override fun getMarvelHeroesList(): Observable<List<MarvelHeroEntity>> =
            marvelHeroesDataBase
                    .getMarvelHeroesDao()
                    .getAllSuperheroes()
                    .toObservable()


    fun saveMarvelHeroesList(marvelHeroes: List<MarvelHeroEntity>) {
        marvelHeroesDataBase
                .getMarvelHeroesDao()
                .insertAll(marvelHeroes)
    }

    fun updateMarvelHeroFavourite(marvelHero: MarvelHeroEntity): Observable<Int> =
            Observable.create {
                marvelHeroesDataBase
                        .getMarvelHeroesDao()
                        .updateHero(marvelHero)
            }
}