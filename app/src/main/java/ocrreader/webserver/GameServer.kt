package ocrreader.webserver

import android.util.Log
import com.koushikdutta.async.http.server.AsyncHttpServer
import java.io.IOException


class GameServer(private val hostName: String, private val listenPort: Int, private val topicName: String, private val gameServer: HttpGameServer) {
    var server = AsyncHttpServer()
    val webAddress: String
        get() = computeWebAddress()
    val webSocketAddress: String
        get() = "ws://$hostName:$listenPort/$topicName"

    @Throws(IOException::class)
    fun start() {
        server.websocket("/$topicName", WebSocketGameServer())
        server.get("/.*", gameServer) // Match all routes
        server.listen(listenPort)

        Log.i(TAG, "HTTP: $webAddress")
        Log.i(TAG, "WebSocket: $webSocketAddress")
    }

    private fun computeWebAddress(): String {
        return when (listenPort) {
            DEFAULT_HTTP_PORT -> "http://$hostName"
            else -> "http://$hostName:$listenPort"
        }
    }


    fun stop() {
        server.stop()
    }

    companion object {
        private const val TAG = "WebServer"
        private const val DEFAULT_HTTP_PORT: Int = 80
    }
}
