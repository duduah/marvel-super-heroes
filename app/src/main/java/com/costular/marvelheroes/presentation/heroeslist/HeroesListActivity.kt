package com.costular.marvelheroes.presentation.heroeslist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.Toast
import com.costular.marvelheroes.R
import com.costular.marvelheroes.domain.model.MarvelHeroEntity
import com.costular.marvelheroes.presentation.MainApp
import com.costular.marvelheroes.presentation.util.Navigator
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_main.*

class HeroesListActivity : AppCompatActivity() {

    lateinit var heroListViewModel: HeroesListViewModel

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var adapter: HeroesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpRecycler()
        setUpViewModel()
    }

    override fun onResume() {
        super.onResume()

        heroListViewModel.loadHeroesList()
    }


    fun inject() {
        (application as MainApp).component.inject(this)
    }

    private fun setUpViewModel() {
        heroListViewModel = ViewModelProviders.of(this, viewModelFactory).get(HeroesListViewModel::class.java)
        bindEvents()
    }

    private fun bindEvents() {

        heroListViewModel.isLoadingState.observe(this, Observer {
            it?.let {
                showLoading(it)
            }
        })

        heroListViewModel.heroesListState.observe(this, Observer {
            it?.let {
                showHeroesList(it)
            }
        })
    }

    private fun setUpRecycler() {
        adapter = HeroesListAdapter (
                { hero, image ->
                    goToHeroDetail(hero.id, image)
                },
                { hero ->
                    updateFavouriteHero(hero)
                })
        heroesListRecycler.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        heroesListRecycler.itemAnimator = DefaultItemAnimator()
        heroesListRecycler.adapter = adapter
    }

    private fun updateFavouriteHero(hero: MarvelHeroEntity) {
        bindEvents()
        heroListViewModel.updateFavourite(hero)
    }

    private fun goToHeroDetail(heroName: String, image: View) {
        navigator.goToHeroDetail(this, heroName, image)
    }

    fun showLoading(isLoading: Boolean) {
        heroesListLoading.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

    fun showHeroesList(heroes: List<MarvelHeroEntity>) {
        adapter.swapData(heroes)
    }

    fun showError(messageRes: Int) {
        Toast.makeText(this, messageRes, Toast.LENGTH_LONG).show()
    }

}
