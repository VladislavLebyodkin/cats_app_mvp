package com.vlados_app.myapplication2.cat_list.presentation

import android.annotation.SuppressLint
import com.vlados_app.myapplication2.cat_list.data.Cat
import com.vlados_app.myapplication2.cat_list.domain.CatRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@SuppressLint("CheckResult")
@InjectViewState
class CatListPresenter @Inject constructor(
    private val repository: CatRepository
) : MvpPresenter<CatListView>() {

    private val currentPage = BehaviorSubject.createDefault(0)
    private val catList = BehaviorSubject.createDefault<List<Cat>>(emptyList())
    private var isLoading = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        loadCatList()
    }

    init {
        currentPage
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { loadCatList() }
    }

    fun loadCatList() {
        if (isLoading) return

        if (catList.value.isNullOrEmpty()) {
            viewState.showLoading()
        }
        isLoading = true
        repository.loadCats(page = currentPage.value ?: 0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    isLoading = false

                    if (catList.value.isNullOrEmpty()) {
                        viewState.hideLoading()
                    }

                    val oldItems = catList.value ?: emptyList()
                    mutableListOf<Cat>().apply {
                        addAll(oldItems)
                        addAll(it)

                        catList.onNext(this)
                        viewState.setCatList(this)
                    }
                }, {
                    isLoading = false

                    if (catList.value.isNullOrEmpty()) {
                        viewState.hideLoading()
                    }

//                    TODO podumat
//                    val oldValue = currentPage.value ?: 0
//                    currentPage.onNext(oldValue - 1)

                    viewState.showError()
                }
            )
    }

    fun nextPage() {
        if (isLoading) return

        val oldValue = currentPage.value ?: 0
        currentPage.onNext(oldValue + 1)
    }

    fun onAddFavouriteClicked(id: String) {
        repository.addToFavourites(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {

            })
    }
}