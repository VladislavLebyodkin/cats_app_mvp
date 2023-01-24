package com.vlados_app.myapplication2.favourite.data

import com.vlados_app.myapplication2.favourite.domain.FavouriteRepository
import com.vlados_app.myapplication2.network.CatService
import io.reactivex.Single
import javax.inject.Inject

class FavouriteRepositoryImpl @Inject constructor(
    private val service: CatService
) : FavouriteRepository {
    override fun loadCats(page: Int): Single<List<FavouriteCat>> {
        return service.loadFavouriteCatList(page = page)
    }
}