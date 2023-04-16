package com.example.mytodoapp.services

import android.content.Context
import android.net.ConnectivityManager

object ConnectionChecker {
    fun checkConnection(context: Context): Boolean {
        var isConnected = false

        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as? ConnectivityManager?

        for (info in manager!!.allNetworkInfo) {
            if (info.typeName.equals("WIFI", ignoreCase = true) ||
                info.typeName.equals("MOBILE", ignoreCase = true)) {

                if (info.isConnected) {
                    isConnected = true
                }
            }
        }

        return isConnected
    }
}