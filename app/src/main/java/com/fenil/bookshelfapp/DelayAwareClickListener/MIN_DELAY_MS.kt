package com.fenil.bookshelfapp.DelayAwareClickListener

import android.view.View

private const val MIN_DELAY_MS = 500L

class DelayAwareClickListener @JvmOverloads constructor(
    private val minDiffBetweenClicks: Long = MIN_DELAY_MS,
    private val clickListener: View.OnClickListener
) : View.OnClickListener {

    private var lastClickTime = 0L

    override fun onClick(view: View?) {
        val lastClickTime = lastClickTime
        val now = System.currentTimeMillis()
        this.lastClickTime = now
        if (now - lastClickTime >= minDiffBetweenClicks) {
            // Register the click
            clickListener.onClick(view)
        }
    }
}
