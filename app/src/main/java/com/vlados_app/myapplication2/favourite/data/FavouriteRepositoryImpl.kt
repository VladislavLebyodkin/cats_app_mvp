package com.vlados_app.myapplication2.favourite.data

import com.vlados_app.myapplication2.favourite.domain.FavouriteCatModel
import com.vlados_app.myapplication2.favourite.domain.FavouriteRepository
import com.vlados_app.myapplication2.common.network.CatService
import com.vlados_app.myapplication2.common.util.DownloadHelper
import io.reactivex.Completable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class FavouriteRepositoryImpl @Inject constructor(
    private val service: CatService,
    private val favouriteCatListMapper: FavouriteCatListMapper,
    private val downloadHelper: DownloadHelper,
) : FavouriteRepository {

    override val favouriteCatModelListSubject = BehaviorSubject.create<List<FavouriteCatModel>>()
    private var currentPage: Int = 0
    private var itemsCount: Int = 0
    var isLoading: Boolean = false

    override fun loadMoreCats(): Completable {
        if (isLoading) return Completable.complete()

        return service.loadFavouriteCatList(page = currentPage)
            .map { catList -> catList.map(favouriteCatListMapper::map) }
            .doOnSuccess { newList ->
                val oldValue = favouriteCatModelListSubject.value.orEmpty()
                val resultList = mutableListOf<FavouriteCatModel>().apply {
                    addAll(oldValue)
                    addAll(newList)
                }
                favouriteCatModelListSubject.onNext(resultList.distinct())
                itemsCount += newList.size
                currentPage = itemsCount / 10
            }
            .doOnSubscribe { isLoading = true }
            .doFinally { isLoading = false }
            .ignoreElement()
    }

    override fun downloadImage(url: String) {
        downloadHelper.download(url)
    }
}