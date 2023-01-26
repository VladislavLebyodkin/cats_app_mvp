package com.vlados_app.myapplication2.cat_list.presentation

import com.vlados_app.myapplication2.cat_list.domain.CatModel
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndStrategy::class)
interface CatListView : MvpView {
    fun setCatList(items: List<CatModel>)
    fun showLoading()
    fun hideLoading()

    @StateStrategyType(SkipStrategy::class)
    fun showError()
}