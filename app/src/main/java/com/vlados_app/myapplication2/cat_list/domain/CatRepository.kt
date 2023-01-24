package com.vlados_app.myapplication2.cat_list.domain

import com.vlados_app.myapplication2.cat_list.data.Cat
import io.reactivex.Single

interface CatRepository {
    fun loadCats(page: Int): Single<List<Cat>>
    fun addToFavourites(id: String): Single<List<Cat>>
}