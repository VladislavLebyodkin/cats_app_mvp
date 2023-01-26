package com.vlados_app.myapplication2.favourite.data

import com.vlados_app.myapplication2.favourite.domain.FavouriteCatModel
import com.vlados_app.myapplication2.favourite.domain.FavouriteRepository
import com.vlados_app.myapplication2.network.CatService
import com.vlados_app.myapplication2.util.DownloadHelper
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
    var isLoading: Boolean = false

    override fun loadMoreCats(): Completable {
        if (isLoading) return Completable.complete()

        return service.loadFavouriteCatList(page = currentPage)
            .map { catList -> catList.map(favouriteCatListMapper::map) }
            .doOnSuccess { newList ->
//                TODO проблема при swipeToRefresh, элементов приходит 3, но страница++, поэтому след запрос не корректный
                favouriteCatModelListSubject.onNext(favouriteCatModelListSubject.value.orEmpty() + newList)
                currentPage++
            }
            .doOnSubscribe { isLoading = true }
            .doFinally { isLoading = false }
            .ignoreElement()
    }

    override fun downloadImage(url: String) {
        downloadHelper.download(url)
    }

    override fun update() {
        favouriteCatModelListSubject.onNext(emptyList())
        currentPage = 0
        loadMoreCats()
    }
}