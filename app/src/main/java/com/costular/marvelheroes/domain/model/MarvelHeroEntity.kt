package com.costular.marvelheroes.domain.model

import android.annotation.SuppressLint
import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by costular on 17/03/2018.
 */
@Entity(tableName = "superheroes")
@SuppressLint("ParcelCreator")
@Parcelize
data class MarvelHeroEntity(
        @PrimaryKey
        val name: String,

        @ColumnInfo(name = "photo_url")
        val photoUrl: String,

        @ColumnInfo(name = "real_name")
        val realName: String,
        val height: String,
        val power: String,
        val abilities: String,
        val groups: String,
        var favourite: Boolean
) : Parcelable