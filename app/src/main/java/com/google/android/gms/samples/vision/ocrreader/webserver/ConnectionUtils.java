package com.google.android.gms.samples.vision.ocrreader.webserver;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import fi.iki.elonen.NanoHTTPD;

import static android.content.Context.WIFI_SERVICE;

public class ConnectionUtils {
    private Context applicationContext;

    public ConnectionUtils(final Context applicationContext) {
        this.applicationContext = applicationContext;
    }


    public boolean isConnectedInWifi() {
        WifiManager wifiManager = (WifiManager) applicationContext
                .getApplicationContext()
                .getSystemService(WIFI_SERVICE);

        if (wifiManager == null) {
            throw new RuntimeException("Wifi Manager is null");
        }

        NetworkInfo networkInfo = ((ConnectivityManager) applicationContext
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()
                && wifiManager.isWifiEnabled() && networkInfo.getTypeName().equals("WIFI");
    }

    public String getIpAddress() {
        WifiManager wifiManager = (WifiManager) applicationContext
                .getApplicationContext().getSystemService(WIFI_SERVICE);

        if (wifiManager == null) {
            throw new RuntimeException("Invalid connection");
        }

        // TODO handle IPv6
        return Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
    }

    public String getHostAddress(NanoHTTPD server) {
        return String.format("http://%s:%s", server.getHostname(), server.getListeningPort());
    }
}
