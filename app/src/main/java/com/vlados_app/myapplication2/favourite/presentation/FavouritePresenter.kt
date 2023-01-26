package com.vlados_app.myapplication2.favourite.presentation

import android.util.Log
import com.vlados_app.myapplication2.favourite.domain.FavouriteRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class FavouritePresenter @Inject constructor(
    private val repository: FavouriteRepository
) : MvpPresenter<FavouriteView>() {
    private val disposables = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.showLoading()
        subscribeCatList()
        loadMoreCats()
    }

    private fun subscribeCatList() {
        repository.favouriteCatModelListSubject
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { viewState.showError() }
            .subscribe {
                viewState.hideLoading()
                viewState.setCatList(it)
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

    fun onDownloadClicked(url: String) {
        repository.downloadImage(url)
    }

    override fun onDestroy() {
        super.onDestroy()

        disposables.dispose()
    }
}