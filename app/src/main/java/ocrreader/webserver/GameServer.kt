package ocrreader.webserver

import android.util.Log
import com.koushikdutta.async.http.server.AsyncHttpServer
import nanohttpd.protocols.http.IHTTPSession
import nanohttpd.protocols.http.response.Response
import nanohttpd.protocols.http.response.Response.newFixedLengthResponse
import java.io.IOException


class GameServer(private val hostName: String, private val listenPort: Int, private val topicName: String) {
    var server = AsyncHttpServer()
    val serverAddress: String
        get() = hostName
    val port: Int
        get() = listenPort

    @Throws(IOException::class)
    fun start() {
        server.websocket("/$topicName", GameRequestCallback())

        server.listen(listenPort)
        Log.i(TAG, "WebSocket: ws://$hostName:$listenPort/$topicName")
    }

    fun stop() {
        server.stop()
    }

    private fun serve(session: IHTTPSession): Response {
        val method = session.method
        val uri = session.uri
        Log.i(TAG, String.format("Method:%s Uri:%s", method, uri))
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

    companion object {
        private val TAG = "WebServer"
    }
}
