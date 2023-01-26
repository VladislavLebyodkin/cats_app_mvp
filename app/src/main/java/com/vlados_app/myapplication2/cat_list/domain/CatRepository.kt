package com.vlados_app.myapplication2.cat_list.domain

import io.reactivex.Completable
import io.reactivex.subjects.BehaviorSubject

interface CatRepository {
    val catModelListSubject: BehaviorSubject<List<CatModel>>

    fun loadMoreCats(): Completable
    fun addToFavourites(id: String): Completable
    fun downloadImage(url: String)
}