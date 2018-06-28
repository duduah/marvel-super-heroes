package com.costular.marvelheroes.data.repository.datasource

import com.costular.marvelheroes.data.model.MarvelHero
import com.costular.marvelheroes.domain.model.MarvelHeroEntity
import io.reactivex.Observable

/**
 * Created by costular on 17/03/2018.
 */
interface MarvelHeroesDataSource {

    fun getMarvelHeroesList(): Observable<List<MarvelHeroEntity>>

    fun getMarvelHero(marvelHeroName: String): Observable<MarvelHeroEntity>

}