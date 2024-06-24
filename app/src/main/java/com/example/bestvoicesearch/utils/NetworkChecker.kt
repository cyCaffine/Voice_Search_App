package com.example.bestvoicesearch.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build

object NetworkChecker {


    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val networkCapabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            } else {
                val info : NetworkInfo? = connectivityManager.activeNetworkInfo
                return info != null && info.isConnectedOrConnecting
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
}