package com.google.android.gms.samples.vision.ocrreader.webserver;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

public class GameServer extends NanoHTTPD {
    private static final String DEBUG_TAG = "WebServer";

    public GameServer(int port) {
        super(port);
    }

    public GameServer(String hostname, int port) {
        super(hostname, port);
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
}
