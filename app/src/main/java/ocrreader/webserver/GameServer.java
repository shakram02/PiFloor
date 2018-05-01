package ocrreader.webserver;

import android.util.Log;


import java.io.IOException;
import java.util.Map;

import ocrreader.nanohttpd.protocols.http.IHTTPSession;
import ocrreader.nanohttpd.protocols.http.NanoHTTPD;
import ocrreader.nanohttpd.protocols.http.request.Method;
import ocrreader.nanohttpd.protocols.http.response.Response;
import ocrreader.nanohttpd.protocols.websockets.WebSocketServer;
import ocrreader.nanohttpd.protocols.websockets.NanoWSD;

import static ocrreader.nanohttpd.protocols.http.response.Response.newFixedLengthResponse;


public class GameServer extends NanoHTTPD {
    private static final String DEBUG_TAG = "WebServer";
    private NanoWSD webSocket;

    public GameServer(String hostname, int port) {
        super(hostname, port);
        int websocketPort = port + 5;
        webSocket = new WebSocketServer(websocketPort, true);


    }


    @Override
    public void start() throws IOException {
        super.start();
        webSocket.start();
        Log.i(DEBUG_TAG, "WebSocket: " + String.format("ws://%s:%s", hostname, webSocket.myPort));
    }

    @Override
    public Response serve(IHTTPSession session) {

        Method method = session.getMethod();
        String uri = session.getUri();
        Log.i(DEBUG_TAG, String.format("Method:%s Uri:%s", method, uri));

        String msg = "<html>";

        msg += "<body><h1>Hello server</h1>\n";
        msg += "<script src=\"myscripts.js\"></script>";
        Map<String, String> parms = session.getParms();
        if (parms.get("username") == null) {
            msg += "<form action='?' method='get'>\n";
            msg += "<p>Your name: <input type='text' name='username'></p>\n";
            msg += "</form>\n";
        } else {
            msg += "<p>Hello, " + parms.get("username") + "!</p>";
        }
        return newFixedLengthResponse(msg + "</body></html>\n");
    }

    @Override
    public void stop() {
        webSocket.stop();
        super.stop();
    }
}
