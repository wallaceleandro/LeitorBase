package com.leitorbase

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object Internet {

    fun temInternet(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val actNetwork = cm.getNetworkCapabilities(network) ?: return false

        return actNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
