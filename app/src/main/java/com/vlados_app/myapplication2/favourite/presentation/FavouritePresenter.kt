package com.vlados_app.myapplication2.favourite.presentation

import com.vlados_app.myapplication2.favourite.domain.FavouriteRepository
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class FavouritePresenter @Inject constructor(
    private val repository: FavouriteRepository
) : MvpPresenter<FavouriteView>() {


}