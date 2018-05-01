package com.google.android.gms.samples.vision.ocrreader;

import android.app.Activity;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.samples.vision.ocrreader.webserver.GameServer;

import java.io.IOException;

public class ServerActivity extends Activity {
    GameServer server;
    private Button startServerBtn;
    private Button stopServerBtn;
    private TextView hostNameTxt;
    private static final int PORT_NUMBER = 5444;   // TODO make this configurable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        server = new GameServer(getIpAddress(), PORT_NUMBER);
        startServerBtn = (Button) findViewById(R.id.btnStartServer);
        stopServerBtn = (Button) findViewById(R.id.btnStopServer);
        hostNameTxt = (TextView) findViewById(R.id.txtHostName);

        startServerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    runServer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        stopServerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopServer();
            }
        });

    }

    private static final String DEBUG_TAG = "SERVER_ACTIVITY";

    private void runServer() throws IOException {
        String hostName = server.getHostname();
        this.hostNameTxt.setText(hostName);
        this.server.start();

        Log.i(DEBUG_TAG, "Address: " + getHostAddress());
    }

    private void stopServer() {
        this.server.stop();
    }

    private String getIpAddress() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

        if (wifiManager == null) {
            throw new RuntimeException("Invalid connection");
        }

        // TODO handle IPv6
        return Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
    }

    private String getHostAddress() {
        return String.format("http://%s:%s", server.getHostname(), server.getListeningPort());
    }
}
