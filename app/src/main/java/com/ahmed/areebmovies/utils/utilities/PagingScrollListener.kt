package com.ahmed.areebmovies.utils.utilities

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PagingScrollListener(
    private val linearLayoutManager: LinearLayoutManager,
    private val pagingScrollListenerInteractions: PagingScrollListenerInteractions?
) : RecyclerView.OnScrollListener() {

    interface PagingScrollListenerInteractions {
        fun onScroll()
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = linearLayoutManager.childCount

        val totalItemCount = linearLayoutManager.itemCount
        val pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition()
        if (dy > 0) {
            if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                pagingScrollListenerInteractions?.onScroll()
            }
        }
    }
}
