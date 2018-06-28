package com.costular.marvelheroes.data.repository

import com.costular.marvelheroes.domain.model.MarvelHeroEntity
import io.reactivex.Observable

/**
 * Created by costular on 17/03/2018.
 */
interface MarvelHeroesRepository {

    fun getMarvelHeroesList(): Observable<List<MarvelHeroEntity>>

    fun getMarvelHero(marvelHeroName: String): Observable<MarvelHeroEntity>

    fun updateMarvelHeroFavourite(marvelHeroEntity: MarvelHeroEntity): Observable<Int>
}