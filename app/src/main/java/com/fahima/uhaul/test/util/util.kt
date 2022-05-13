package com.fahima.uhaul.test.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.fahima.uhaul.test.UserApplication

 fun hasInternetConnection(application: UserApplication): Boolean {
    val connectivityManager = application.getSystemService(
        Context.CONNECTIVITY_SERVICE
    ) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetwork ?: return false
    val capabilities =
        connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
    return when {
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}