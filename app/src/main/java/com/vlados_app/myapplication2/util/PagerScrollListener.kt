package com.vlados_app.myapplication2.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PagerScrollListener(
    private val onScrollToNextPage: () -> Unit
) : RecyclerView.OnScrollListener() {

    companion object {
        private const val PAGINATION_MARGIN = 4
        private const val PAGE_SIZE = 10
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val visibleItemCount = recyclerView.layoutManager!!.childCount
        val totalItemCount = recyclerView.layoutManager!!.itemCount
        val firstVisibleItemPosition =
            (recyclerView.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()

        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount - PAGINATION_MARGIN &&
            firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE
        ) {
            onScrollToNextPage()
        }
    }
}