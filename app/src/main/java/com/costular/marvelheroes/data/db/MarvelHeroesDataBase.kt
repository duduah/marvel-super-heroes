package com.costular.marvelheroes.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.costular.marvelheroes.domain.model.MarvelHeroEntity

@Database(entities = [MarvelHeroEntity::class], version = 1)
abstract class MarvelHeroesDataBase : RoomDatabase() {

    abstract fun getMarvelHeroesDao(): MarvelHeroesDao
}