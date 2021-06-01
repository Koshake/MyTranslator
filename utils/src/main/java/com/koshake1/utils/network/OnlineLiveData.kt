package com.koshake1.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.LiveData

private const val TAG  = "1212"

class OnlineLiveData(context: Context) : LiveData<Boolean>() {

    private val availableNetworks = mutableSetOf<Network>()
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val request: NetworkRequest = NetworkRequest.Builder().build()

    private val callback = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            Log.d(TAG, "onAvailable")
            availableNetworks.add(network)
            update(availableNetworks.isNotEmpty())
        }

        override fun onLost(network: Network) {
            Log.d(TAG, "onLost")
            availableNetworks.remove(network)
            update(availableNetworks.isNotEmpty())
        }
    }

    fun isNetworkAvailable() : Boolean {
        return connectivityManager.allNetworks.isNotEmpty()
    }

    override fun onActive() {
        connectivityManager.registerNetworkCallback(request, callback)
    }

    override fun onInactive() {
        connectivityManager.unregisterNetworkCallback(callback)
    }

    private fun update(online: Boolean) {
        Log.d(TAG, "update $online")
        if (online != value) {
            postValue(online)
        }
    }
}