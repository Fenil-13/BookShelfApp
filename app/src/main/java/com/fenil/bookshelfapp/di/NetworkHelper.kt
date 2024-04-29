package com.fenil.bookshelfapp.di

import android.content.Context
import com.fenil.bookshelfapp.ui.utils.NetworkUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NetworkHelper @Inject constructor(@ApplicationContext val context: Context) {

    fun isNetworkConnected() = NetworkUtils.isNetworkConnected(context)
}
