package com.costular.marvelheroes.presentation.heroedetail

import android.arch.lifecycle.MutableLiveData
import com.costular.marvelheroes.data.repository.MarvelHeroesRepository
import com.costular.marvelheroes.domain.model.MarvelHeroEntity
import com.costular.marvelheroes.util.mvvm.BaseViewModel

class MarvelHeroDetailViewModel(val marvelHeroesRepository: MarvelHeroesRepository) : BaseViewModel() {

    val heroState: MutableLiveData<MarvelHeroEntity> = MutableLiveData()

    loadHero(){

    }
}