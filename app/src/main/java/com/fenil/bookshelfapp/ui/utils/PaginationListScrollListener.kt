package com.fenil.bookshelfapp.ui.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


open class PaginationListScrollListener(
    private val wrapper: RecyclerView.OnScrollListener? = null,
    private val mayBeFetchNextData: (currentItemCount: Int) -> Unit,
    private val mayBeFetchPreviousData: (currentItemCount: Int) -> Unit
) : RecyclerView.OnScrollListener() {

    private var isScrollUp: Boolean = false
    private var isScrollDown: Boolean = false
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {

            val totalItemCount = recyclerView.layoutManager?.itemCount ?: 0
            var lastVisibleItem = RecyclerView.NO_POSITION
            var firstVisibleItem  = RecyclerView.NO_POSITION

                recyclerView.layoutManager.let {
                    if (it is LinearLayoutManager) {
                        lastVisibleItem = it.findLastCompletelyVisibleItemPosition()
                        firstVisibleItem = it.findFirstCompletelyVisibleItemPosition()
                    }
                }

            if (isScrollDown && lastVisibleItem >= totalItemCount) {
                mayBeFetchNextData(totalItemCount)
            }
            if(isScrollUp && firstVisibleItem <= 0){
                mayBeFetchPreviousData(totalItemCount)
            }
        }
        wrapper?.onScrollStateChanged(recyclerView, newState)
        super.onScrollStateChanged(recyclerView, newState)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        isScrollUp = dy < 0
        isScrollDown = dy > 0
        wrapper?.onScrolled(recyclerView, dx, dy)
        super.onScrolled(recyclerView, dx, dy)
    }
}
