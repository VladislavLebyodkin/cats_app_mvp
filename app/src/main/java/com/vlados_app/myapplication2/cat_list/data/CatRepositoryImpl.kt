package com.vlados_app.myapplication2.cat_list.data

import com.vlados_app.myapplication2.cat_list.data.model.FavouriteCatRequest
import com.vlados_app.myapplication2.cat_list.domain.CatModel
import com.vlados_app.myapplication2.cat_list.domain.CatRepository
import com.vlados_app.myapplication2.common.network.CatService
import com.vlados_app.myapplication2.common.sqlite.SqliteHelper
import com.vlados_app.myapplication2.common.util.DownloadHelper
import io.reactivex.Completable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class CatRepositoryImpl @Inject constructor(
    private val service: CatService,
    private val catMapper: CatMapper,
    private val sqliteHelper: SqliteHelper,
    private val downloadHelper: DownloadHelper,
) : CatRepository {

    override val catModelListSubject = BehaviorSubject.create<List<CatModel>>()
    private var currentPage: Int = 0
    var isLoading: Boolean = false

    override fun loadMoreCats(): Completable {
        if (isLoading) return Completable.complete()

        return service.loadCatList(page = currentPage)
            .map { catList ->
                catList.map {
                    val isFavourite = sqliteHelper.isCatFavourite(it.id)
                    catMapper.map(it, isFavourite)
                }
            }
            .doOnSuccess { newList ->
                catModelListSubject.onNext(catModelListSubject.value.orEmpty() + newList)
                currentPage++
            }
            .doOnSubscribe { isLoading = true }
            .doFinally { isLoading = false }
            .ignoreElement()
    }

    override fun addToFavourites(id: String): Completable {
        val favouriteCatRequest = FavouriteCatRequest(imageId = id)
        return service.addToFavourites(favouriteCatRequest)
            .doOnSuccess { sqliteHelper.addCatId(it.id) }
            .ignoreElement()
    }

    override fun downloadImage(url: String) {
        downloadHelper.download(url)
    }
}