package com.theappcoderz.admobcustomeads

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object NetworkUtils {

    enum class NetworkType {
        NONE,
        WIFI,
        CELLULAR
    }

    fun getNetworkType(context: Context): NetworkType {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // For Android API level 23 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return NetworkType.NONE
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return NetworkType.NONE

            return when {
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> NetworkType.WIFI
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> NetworkType.CELLULAR
                else -> NetworkType.NONE
            }
        } else {
            // For Android API level below 23
            val activeNetworkInfo = connectivityManager.activeNetworkInfo ?: return NetworkType.NONE

            return when (activeNetworkInfo.type) {
                ConnectivityManager.TYPE_WIFI -> NetworkType.WIFI
                ConnectivityManager.TYPE_MOBILE -> NetworkType.CELLULAR
                else -> NetworkType.NONE
            }
        }
    }
}
