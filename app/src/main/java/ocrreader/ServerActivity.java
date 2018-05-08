package ocrreader;

import android.app.Activity;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ocrreader.webserver.ConnectionUtils;
import ocrreader.webserver.GameServer;

import java.io.IOException;

public class ServerActivity extends Activity {
    GameServer server;
    private Button startServerBtn;
    private Button stopServerBtn;
    private TextView hostNameTxt;
    private static final int PORT_NUMBER = 5444;   // TODO make this configurable
    private ConnectionUtils connectionUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        connectionUtils = new ConnectionUtils(getApplicationContext());

        // TODO clean this up later
        if (!connectionUtils.isConnectedInWifi()) {
            Toast.makeText(this, "Not connected to WIFI, App won't work", Toast.LENGTH_LONG).show();
            return;
        }

        server = new GameServer(connectionUtils.getIpAddress(), PORT_NUMBER);
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

        Log.i(DEBUG_TAG, "Address: " + connectionUtils.getHostAddress(server));
    }

    private void stopServer() {
        this.server.stop();
        this.hostNameTxt.setText("Stopped");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.server != null) {
            this.server.stop();
        }
    }
}
