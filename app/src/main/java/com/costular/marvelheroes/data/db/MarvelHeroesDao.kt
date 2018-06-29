package com.costular.marvelheroes.data.db

import android.arch.persistence.room.*
import com.costular.marvelheroes.domain.model.MarvelHeroEntity
import io.reactivex.Maybe
import io.reactivex.Observable

@Dao
abstract class MarvelHeroesDao {

    // TODO: review why Observable raise error: "Not sure how to convert a Cursor to this method's return type"
    @Query("SELECT * FROM superheroes")
    abstract fun getAllSuperheroes(): Maybe<List<MarvelHeroEntity>>

    // TODO: review why Observable raise error: "Not sure how to convert a Cursor to this method's return type"
    @Query("SELECT * FROM superheroes WHERE name = :heroName")
    abstract fun getSuperhero(heroName: String): Maybe<MarvelHeroEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(SuperheroesList: List<MarvelHeroEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun updateHero(hero: MarvelHeroEntity): Int

    // TODO: implements update to set favorite item for superheroe
}