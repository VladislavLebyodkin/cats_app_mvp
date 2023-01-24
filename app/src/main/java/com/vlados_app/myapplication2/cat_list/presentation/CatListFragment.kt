package com.vlados_app.myapplication2.cat_list.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.vlados_app.myapplication2.CatApp
import com.vlados_app.myapplication2.R
import com.vlados_app.myapplication2.cat_list.data.Cat
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class CatListFragment : MvpAppCompatFragment(), CatListView {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cat_list, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = CatListFragment()
    }

    @Inject
    lateinit var presenterProvider: Provider<CatListPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    private val adapter = CatListAdapter {
        presenter.onAddFavouriteClicked(it)
    }

//    TODO добавить сообщение о том, что список пуст
    private var loadingView: View? = null

    private val PAGINATION_MARGIN = 3
    private val PAGE_SIZE = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        CatApp.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingView = view.findViewById(R.id.loading)
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler)

        swipeRefreshLayout.setOnRefreshListener {
            presenter.loadCatList()
        }

        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = recyclerView.layoutManager!!.childCount
                val totalItemCount = recyclerView.layoutManager!!.itemCount
                val firstVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount - PAGINATION_MARGIN &&
                    firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE
                ) {
                    presenter.nextPage()
                }
            }
        })
    }

    override fun setCatList(items: List<Cat>) {
        adapter.submitList(items)
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
}