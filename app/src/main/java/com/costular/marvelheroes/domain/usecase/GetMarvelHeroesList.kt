package com.costular.marvelheroes.domain.usecase

import com.costular.marvelheroes.data.repository.MarvelHeroesRepositoryImpl
import com.costular.marvelheroes.domain.model.MarvelHeroEntity
import io.reactivex.Observable
import javax.inject.Inject


class GetMarvelHeroesList @Inject constructor(val marvelHeroesRepositoryImpl: MarvelHeroesRepositoryImpl)
    : UseCase<List<MarvelHeroEntity>>() {

    override fun buildCase(): Observable<List<MarvelHeroEntity>> =
            marvelHeroesRepositoryImpl.getMarvelHeroesList()

}