package com.vlados_app.myapplication2.favourite.domain

import com.vlados_app.myapplication2.favourite.data.FavouriteCat
import io.reactivex.Single

interface FavouriteRepository {
    fun loadCats(page: Int): Single<List<FavouriteCat>>
}