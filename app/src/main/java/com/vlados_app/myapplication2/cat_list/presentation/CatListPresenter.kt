package com.vlados_app.myapplication2.cat_list.presentation

import com.vlados_app.myapplication2.cat_list.domain.CatRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class CatListPresenter @Inject constructor(
    private val repository: CatRepository
) : MvpPresenter<CatListView>() {
    private val disposables = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.showLoading()
        subscribeCatList()
        loadMoreCats()
    }

    private fun subscribeCatList() {
        repository.catModelListSubject
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                viewState.hideLoading()
                if (it.isNotEmpty()) {
                    viewState.hideEmptyCatListView()
                    viewState.setCatList(it)
                } else {
                    viewState.showEmptyCatListView()
                }
            }.also {
                disposables.add(it)
            }
    }

    fun loadMoreCats() {
        repository.loadMoreCats()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { viewState.showError() }
            .subscribe()
            .also {
                disposables.add(it)
            }
    }

    fun onAddFavouriteClicked(id: String) {
        repository.addToFavourites(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { viewState.showError() }
            .subscribe()
            .also {
                disposables.add(it)
            }
    }

    fun onDownloadClicked(url: String) {
        repository.downloadImage(url)
    }

    override fun onDestroy() {
        super.onDestroy()

        disposables.dispose()
    }
}