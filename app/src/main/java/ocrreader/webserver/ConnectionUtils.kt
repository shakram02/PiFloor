package ocrreader.webserver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.text.format.Formatter


import nanohttpd.protocols.http.NanoHTTPD

import android.content.Context.WIFI_SERVICE

class ConnectionUtils(private val applicationContext: Context) {
    private var broadcastReceiverNetworkState: BroadcastReceiver? = null


    val isConnectedInWifi: Boolean
        get() {
            val wifiManager = applicationContext
                    .applicationContext
                    .getSystemService(WIFI_SERVICE) as WifiManager
                    ?: throw RuntimeException("Wifi Manager is null")

            val networkInfo = (applicationContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo

            return (networkInfo != null && networkInfo.isAvailable && networkInfo.isConnected
                    && wifiManager.isWifiEnabled && networkInfo.typeName == "WIFI")
        }

    // TODO handle IPv6
    val ipAddress: String
        get() {
            val wifiManager = applicationContext
                    .applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
                    ?: throw RuntimeException("Invalid connection")

            return Formatter.formatIpAddress(wifiManager.connectionInfo.ipAddress)
        }

    fun getHostAddress(server: NanoHTTPD): String {
        return String.format("http://%s:%s", server.getHostname(), server.listeningPort)
    }

    private fun initBroadcastReceiverNetworkStateChanged(stateChangeHandler: Runnable) {
        // source
        // https://github.com/lopspower/AndroidWebServer/blob/master/app/src/main/java/com/mikhaellopez/androidwebserver/MainActivity.java#L76

        val filters = IntentFilter()
        filters.addAction("android.net.wifi.WIFI_STATE_CHANGED")
        filters.addAction("android.net.wifi.STATE_CHANGE")
        broadcastReceiverNetworkState = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                stateChangeHandler.run()
            }
        }
        applicationContext.registerReceiver(broadcastReceiverNetworkState, filters)
    }

}
