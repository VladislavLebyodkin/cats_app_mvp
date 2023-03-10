package com.vlados_app.myapplication2.favourite.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.vlados_app.myapplication2.CatApp
import com.vlados_app.myapplication2.R
import com.vlados_app.myapplication2.favourite.domain.FavouriteCatModel
import com.vlados_app.myapplication2.common.util.PagerScrollListener
import moxy.MvpFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class FavouriteFragment : MvpFragment(), FavouriteView {

    companion object {
        @JvmStatic
        fun newInstance() = FavouriteFragment()
    }

    private var onScrollListener: RecyclerView.OnScrollListener? = null

    private var loadingView: View? = null
    private var emptyCatListView: View? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null

    @Inject
    lateinit var presenterProvider: Provider<FavouritePresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    private var adapter: FavouriteAdapter? = FavouriteAdapter {
        presenter.onDownloadClicked(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        CatApp.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emptyCatListView = view.findViewById(R.id.empty_cat_list_view)
        loadingView = view.findViewById(R.id.loading)
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)
        val recycler = view.findViewById<RecyclerView>(R.id.recycler)

        swipeRefreshLayout?.setOnRefreshListener {
            presenter.loadMoreCats()
        }

        recycler.adapter = adapter
        val listener = PagerScrollListener(presenter::loadMoreCats)
        onScrollListener = listener
        recycler.addOnScrollListener(listener)
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

    override fun setCatList(items: List<FavouriteCatModel>) {
        adapter?.submitList(items)
        swipeRefreshLayout?.isRefreshing = false
    }

    override fun onDestroyView() {
        super.onDestroyView()

        adapter = null
        onScrollListener = null
    }
}