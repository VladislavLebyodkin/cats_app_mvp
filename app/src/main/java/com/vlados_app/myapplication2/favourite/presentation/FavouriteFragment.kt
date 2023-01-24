package com.vlados_app.myapplication2.favourite.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.vlados_app.myapplication2.CatApp
import com.vlados_app.myapplication2.R
import com.vlados_app.myapplication2.cat_list.data.Cat
import moxy.MvpFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class FavouriteFragment : MvpFragment(), FavouriteView {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavouriteFragment()
    }

    private var loadingView: View? = null

    @Inject
    lateinit var presenterProvider: Provider<FavouritePresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    override fun onCreate(savedInstanceState: Bundle?) {
        CatApp.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingView = view.findViewById(R.id.loading)
    }

    override fun showLoading() {
        loadingView?.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loadingView?.visibility = View.GONE
    }

    override fun showError() {
        Toast.makeText(activity, R.string.common_error, Toast.LENGTH_SHORT).show()
    }

    override fun setCatList(items: List<Cat>) {

    }

}