package com.vlados_app.myapplication2.cat_list.data

import com.vlados_app.myapplication2.cat_list.domain.CatRepository
import com.vlados_app.myapplication2.network.CatService
import io.reactivex.Single
import javax.inject.Inject

class CatRepositoryImpl @Inject constructor(
    private val service: CatService
): CatRepository {
    override fun loadCats(page: Int): Single<List<Cat>> {
        return service.loadCatList(page = page)
    }

    override fun addToFavourites(id: String): Single<List<Cat>> {
        return service.addToFavourites(id)
    }
}