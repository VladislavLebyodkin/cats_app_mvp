package com.vlados_app.myapplication2.cat_list.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.vlados_app.myapplication2.CatApp
import com.vlados_app.myapplication2.R
import com.vlados_app.myapplication2.cat_list.domain.CatModel
import com.vlados_app.myapplication2.util.PagerScrollListener
import moxy.MvpFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider


class CatListFragment : MvpFragment(), CatListView {

    companion object {
        @JvmStatic
        fun newInstance() = CatListFragment()
    }

    @Inject
    lateinit var presenterProvider: Provider<CatListPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    private var adapter: CatListAdapter? = CatListAdapter({
        presenter.onAddFavouriteClicked(it)
    }, {
        presenter.onDownloadClicked(it)
    })

    private var onScrollListener: RecyclerView.OnScrollListener? = null

    /**
     * Failed to find GeneratedAppGlideModule. You should include an annotationProcessor compile dependency on com.github.bumptech.glide:compiler in your application and a @GlideModule annotated AppGlideModule implementation or LibraryGlideModules will be silently ignored
     */
    private var loadingView: View? = null
    private var emptyCatListView: View? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        CatApp.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cat_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emptyCatListView = view.findViewById(R.id.empty_cat_list_view)
        loadingView = view.findViewById(R.id.loading)
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler)

        swipeRefreshLayout?.setOnRefreshListener {
            presenter.loadMoreCats()
        }

        recyclerView.adapter = adapter

        val listener = PagerScrollListener(presenter::loadMoreCats)
        onScrollListener = listener
        recyclerView.addOnScrollListener(listener)
    }

    override fun setCatList(items: List<CatModel>) {
        adapter?.submitList(items)
        swipeRefreshLayout?.isRefreshing = false
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

    override fun showEmptyCatListView() {
        emptyCatListView?.visibility = View.VISIBLE
    }

    override fun hideEmptyCatListView() {
        emptyCatListView?.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()

        adapter = null
        onScrollListener = null
    }
}