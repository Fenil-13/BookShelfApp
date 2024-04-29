package com.fenil.bookshelfapp.ui.utils

import android.content.Context
import android.net.ConnectivityManager

class NetworkUtils {

    companion object{
        fun isNetworkConnected(context: Context?): Boolean {
            if (context == null) {
                return false
            }

            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            return manager?.run {
                val isMobile = getNetworkInfo(ConnectivityManager.TYPE_MOBILE)?.isConnectedOrConnecting == true
                val isWifi = getNetworkInfo(ConnectivityManager.TYPE_WIFI)?.isConnectedOrConnecting == true
                isMobile || isWifi
            } ?: false
        }
    }
}