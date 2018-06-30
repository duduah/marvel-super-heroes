package com.costular.marvelheroes.presentation.heroedetail

import android.arch.lifecycle.MutableLiveData
import com.costular.marvelheroes.data.repository.MarvelHeroesRepository
import com.costular.marvelheroes.domain.model.MarvelHeroEntity
import com.costular.marvelheroes.util.mvvm.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MarvelHeroDetailViewModel @Inject constructor(val marvelHeroesRepository: MarvelHeroesRepository)
    : BaseViewModel() {

    val heroState: MutableLiveData<MarvelHeroEntity> = MutableLiveData()
    val isLoadingState: MutableLiveData<Boolean> = MutableLiveData()
    val heroUpdateState: MutableLiveData<Boolean> = MutableLiveData()

    fun loadHero(marvelHeroName: String){
        marvelHeroesRepository
                .getMarvelHero(marvelHeroName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isLoadingState.postValue(true) }
                .doOnTerminate { isLoadingState.postValue(false) }
                .subscribeBy(
                        onNext = {
                            heroState.value = it
                        },
                        onError = {}
                )
                .addTo(compositeDisposable)
    }

    fun updateHero(marvelHeroEntity: MarvelHeroEntity) {
        marvelHeroesRepository
                .updateMarvelHero(marvelHeroEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isLoadingState.postValue(true) }
                .doOnTerminate { isLoadingState.postValue(false) }
                .subscribeBy(
                        onNext = {
                            heroState.value = marvelHeroEntity
                            heroUpdateState.value = true
                        },
                        onError = {
                            heroUpdateState.value = false
                        }
                )
                .addTo(compositeDisposable)
    }
}