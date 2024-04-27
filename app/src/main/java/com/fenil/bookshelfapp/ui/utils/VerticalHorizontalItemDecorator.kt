package com.fenil.bookshelfapp.ui.utils

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class VerticalHorizontalItemDecorator(context: Context, verticalOffSet: Int) : RecyclerView.ItemDecoration() {

    private val verticalMargin = context.resources.getDimensionPixelSize(verticalOffSet)
    private var horizontalMargin: Int = 0
    private var isAddMarginToLastItem = false
    private var isAddMarginToTopItem = false


    constructor(context: Context, @DimenRes verticalOffSet: Int, @DimenRes horizontalOffSet: Int) : this(context, verticalOffSet) {
        horizontalMargin = context.resources.getDimensionPixelSize(horizontalOffSet)
    }

    constructor(context: Context, @DimenRes verticalOffSet: Int, @DimenRes horizontalOffSet: Int, isAddMarginToLastItem: Boolean) : this(context, verticalOffSet) {
        horizontalMargin = context.resources.getDimensionPixelSize(horizontalOffSet)
        this.isAddMarginToLastItem = isAddMarginToLastItem
    }

    constructor(context: Context, @DimenRes verticalOffSet: Int, @DimenRes horizontalOffSet: Int, isAddMarginToLastItem: Boolean, isAddMarginToTopItem:Boolean) : this(context, verticalOffSet) {
        horizontalMargin = context.resources.getDimensionPixelSize(horizontalOffSet)
        this.isAddMarginToLastItem = isAddMarginToLastItem
        this.isAddMarginToTopItem = isAddMarginToTopItem
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val linearLayoutManager = parent.layoutManager as LinearLayoutManager?
        val lastPosition = if (linearLayoutManager == null) -1 else linearLayoutManager.itemCount - 1

        val position = parent.getChildAdapterPosition(view)

        // Set default margin values
        var topMargin = verticalMargin / 2
        var bottomMargin = verticalMargin / 2

        if (position == 0 && position == lastPosition) { //lastPosition == 0 // only one item in the list
            topMargin = if (isAddMarginToTopItem) verticalMargin / 2 else 0
            bottomMargin = if (isAddMarginToLastItem) verticalMargin / 2 else 0
        }else if (position == 0) {
            topMargin = if (isAddMarginToTopItem) verticalMargin / 2 else 0
        }else if (position == lastPosition && lastPosition != -1) {
            // Last item in the list
            bottomMargin = if (isAddMarginToLastItem) verticalMargin / 2 else 0
        }
        outRect.set(horizontalMargin, topMargin, horizontalMargin, bottomMargin)
    }

}