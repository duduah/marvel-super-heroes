package com.costular.marvelheroes.presentation.heroedetail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.costular.marvelheroes.R
import com.costular.marvelheroes.domain.model.MarvelHeroEntity
import com.costular.marvelheroes.presentation.MainApp
import kotlinx.android.synthetic.main.activity_hero_detail.*
import javax.inject.Inject


class MarvelHeroeDetailActivity : AppCompatActivity() {

    companion object {
        const val PARAM_HERO = "heroe"
        const val PARAM_HERO_ID = "PARAM_HERO_ID"

        fun intent(context: Context, hero: MarvelHeroEntity): Intent =
                Intent(context, MarvelHeroeDetailActivity::class.java).apply {
                    putExtra(PARAM_HERO, hero)
                }

        fun intent(context: Context, heroId: String): Intent =
                Intent(context, MarvelHeroeDetailActivity::class.java).apply {
                    putExtra(PARAM_HERO_ID, heroId)
                }

    }

    @Inject
    lateinit var heroDetailViewModel: MarvelHeroDetailViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var marvelHero: MarvelHeroEntity

    lateinit var ratingList: List<ImageView>

    var heroRating = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        inject()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_detail)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }
        supportPostponeEnterTransition() // Wait for image load and then draw the animation

        setUpViewModel()
        heroFavouriteIcon.setOnClickListener {
            updateHero(marvelHero.copy(favourite = !marvelHero.favourite))
        }
        setRatingOnClickListener()
    }

    fun inject() {
        (application as MainApp).component.inject(this)
    }

    private fun setUpViewModel() {
        heroDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(MarvelHeroDetailViewModel::class.java)
        bindEvents()
        loadViewModel()
    }

    private fun bindEvents() {
        heroDetailViewModel.heroState.observe(this, Observer { marvelHeroEntity ->
            marvelHeroEntity?.let {
                marvelHero = it
                fillHeroData(marvelHero)
            }
        })
    }

    private fun bindHeroUpdateEvent() {
        bindEvents()
        heroDetailViewModel.heroState.observe(this, Observer { marvelHeroEntity ->
            marvelHeroEntity?.let {
                loadViewModel()
            }
        })

        heroDetailViewModel.heroUpdateState.observe(this, Observer { updatedOK ->
            if (updatedOK != null) {
                if (!updatedOK) {
                    showError(R.string.update_error_message)
                }
            }
        })
    }

    private fun loadViewModel() {

        val heroId: String? = intent?.extras?.getString(PARAM_HERO_ID)
        heroId?.let {
            heroDetailViewModel.loadHero(heroId)
        }

    }

    private fun updateHero(marvelHeroEntity: MarvelHeroEntity) {
        bindHeroUpdateEvent()
        heroDetailViewModel.updateHero(marvelHeroEntity)
    }

    private fun fillHeroData(hero: MarvelHeroEntity) {
        Glide.with(this)
                .load(hero.photoUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }
                })
                .into(heroDetailImage)

        heroDetailName.text = hero.name
        heroDetailRealName.text = hero.realName
        heroDetailHeight.text = hero.height
        heroDetailPower.text = hero.power
        heroDetailAbilities.text = hero.abilities
        setFavouriteIcon(hero.favourite)
        heroRating = hero.rating
        setRatingStars()
    }

    private fun setFavouriteIcon(isFavourite: Boolean) {
        heroFavouriteIcon.setImageResource(when(isFavourite) {
            true -> R.drawable.favourite_on
            false -> R.drawable.favourite_off
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showError(messageRes: Int) {
        Toast.makeText(this, messageRes, Toast.LENGTH_LONG).show()
    }

    private fun setRatingOnClickListener() {
        ratingList = listOf(
                heroFavouriteRating01,
                heroFavouriteRating02,
                heroFavouriteRating03,
                heroFavouriteRating04,
                heroFavouriteRating05)

        ratingList.map {
            it.setOnClickListener {
                val ratingSelected = ratingList.indexOf(it) + 1
                updateHeroRaging(ratingSelected)
            }
        }
    }

    private fun updateHeroRaging(rating: Int) {
        if (rating == heroRating) {
            heroRating = 0
        }
        else {
            heroRating = rating
        }

        updateHero(marvelHero.copy(rating = heroRating))
        setRatingStars()
    }

    private fun setRatingStars() {

        cleanRatingStars()

        for (i in 0..(heroRating - 1)) {
            ratingList.get(i).setImageResource(R.drawable.favourite_on)
        }
    }

    private fun cleanRatingStars() {

        ratingList.map {
            it.setImageResource(R.drawable.favourite_off)
        }
    }

}