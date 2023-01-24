package com.vlados_app.myapplication2.favourite.presentation

import com.vlados_app.myapplication2.cat_list.data.Cat
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndStrategy::class)
interface FavouriteView : MvpView {
    fun setCatList(items: List<Cat>)
    fun showLoading()
    fun hideLoading()
    @StateStrategyType(SkipStrategy::class)
    fun showError()
}