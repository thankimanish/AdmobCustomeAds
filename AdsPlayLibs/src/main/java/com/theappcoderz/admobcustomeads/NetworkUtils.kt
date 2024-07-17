package com.theappcoderz.admobcustomeads

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import java.net.NetworkInterface

object NetworkUtils {

    enum class NetworkType {
        NONE,
        WIFI,
        CELLULAR,
        VPN
    }

    fun getNetworkType(context: Context): NetworkType {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // Check for VPN connection
        try {
            val networkInterfaces = NetworkInterface.getNetworkInterfaces()
            while (networkInterfaces.hasMoreElements()) {
                val intf = networkInterfaces.nextElement()
                if (intf.name.contains("tun") || intf.name.contains("ppp")) {
                    if (intf.isUp) {
                        return NetworkType.VPN
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // For Android API level 23 and above
        val network = connectivityManager.activeNetwork ?: return NetworkType.NONE
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return NetworkType.NONE

        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> NetworkType.WIFI
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> NetworkType.CELLULAR
            else -> NetworkType.NONE
        }
    }
}
