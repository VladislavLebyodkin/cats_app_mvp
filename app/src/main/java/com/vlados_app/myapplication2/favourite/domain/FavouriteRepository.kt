package com.vlados_app.myapplication2.favourite.domain

import io.reactivex.Completable
import io.reactivex.subjects.BehaviorSubject

interface FavouriteRepository {
    val favouriteCatModelListSubject: BehaviorSubject<List<FavouriteCatModel>>

    fun loadMoreCats(): Completable

    fun downloadImage(url: String)

    fun update()
}