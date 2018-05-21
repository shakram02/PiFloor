package ocrreader.webserver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;


import nanohttpd.protocols.http.NanoHTTPD;

import static android.content.Context.WIFI_SERVICE;

public class ConnectionUtils {
    private Context applicationContext;
    private BroadcastReceiver broadcastReceiverNetworkState;

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

    private void initBroadcastReceiverNetworkStateChanged(final Runnable stateChangeHandler) {
        // source
        // https://github.com/lopspower/AndroidWebServer/blob/master/app/src/main/java/com/mikhaellopez/androidwebserver/MainActivity.java#L76

        final IntentFilter filters = new IntentFilter();
        filters.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filters.addAction("android.net.wifi.STATE_CHANGE");
        broadcastReceiverNetworkState = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                stateChangeHandler.run();
            }
        };
        applicationContext.registerReceiver(broadcastReceiverNetworkState, filters);
    }

}
