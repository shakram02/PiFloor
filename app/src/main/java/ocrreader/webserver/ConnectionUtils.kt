package ocrreader.webserver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.WIFI_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.text.format.Formatter

class ConnectionUtils(private val applicationContext: Context) {
    private var broadcastReceiverNetworkState: BroadcastReceiver? = null

    fun isConnectedToWifi(): Boolean {
        val wifiManager = applicationContext
                .applicationContext
                .getSystemService(WIFI_SERVICE) as WifiManager?
                ?: throw RuntimeException("Wifi Manager is null")
        val networkInfo = (applicationContext
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo

        return (networkInfo != null && networkInfo.isAvailable && networkInfo.isConnected
                && wifiManager.isWifiEnabled && networkInfo.typeName == "WIFI")
    }

    fun getIpAddress(): String {
        // TODO handle IPv6
        val wifiManager = applicationContext
                .applicationContext.getSystemService(WIFI_SERVICE) as WifiManager?
                ?: throw RuntimeException("Invalid connection")

        return Formatter.formatIpAddress(wifiManager.connectionInfo.ipAddress)
    }


    fun getHostAddress(hostName: String, port: Int): String {
        return String.format("http://%s:%s", hostName, port)
    }

    private fun initBroadcastReceiverNetworkStateChanged(stateChangeHandler: Runnable) {
        // source [Monitor wifi state]
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
