package com.sk.contactsapplication.helpers

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetInfo @Inject constructor(private val connectivityManager: ConnectivityManager) {

    fun isInternetOn(): Boolean {
        val result: Boolean
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val activeNetwork =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || activeNetwork.hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR
            ) -> true
            else -> false
        }
        return result
    }
}