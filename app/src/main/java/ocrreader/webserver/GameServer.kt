package ocrreader.webserver

import android.util.Log
import nanohttpd.protocols.http.IHTTPSession
import nanohttpd.protocols.http.NanoHTTPD
import nanohttpd.protocols.http.response.Response
import nanohttpd.protocols.http.response.Response.newFixedLengthResponse
import nanohttpd.protocols.websockets.NanoWSD
import nanohttpd.protocols.websockets.WebSocketServer
import java.io.IOException


class GameServer(hostname: String, port: Int) : NanoHTTPD(hostname, port) {
    private val webSocket: NanoWSD

    init {
        val websocketPort = port + 5
        webSocket = WebSocketServer(websocketPort, true)
    }


    @Throws(IOException::class)
    override fun start() {
        super.start()
        webSocket.start()
        Log.i(DEBUG_TAG, "WebSocket: " + String.format("ws://%s:%s", hostname, webSocket.myPort))
    }

    public override fun serve(session: IHTTPSession): Response {
        val method = session.method
        val uri = session.uri
        Log.i(DEBUG_TAG, String.format("Method:%s Uri:%s", method, uri))
        var msg = "<html>"

        msg += "<body><h1>Hello server</h1>\n"
        msg += "<script src=\"myscripts.js\"></script>"
        val parms = session.parms
        if (parms["username"] == null) {
            msg += "<form action='?' method='get'>\n"
            msg += "<p>Your name: <input type='text' name='username'></p>\n"
            msg += "</form>\n"
        } else {
            msg += "<p>Hello, " + parms["username"] + "!</p>"
        }
        return newFixedLengthResponse("$msg</body></html>\n")
    }

    override fun stop() {
        webSocket.stop()
        super.stop()
    }

    companion object {
        private val DEBUG_TAG = "WebServer"
    }
}
