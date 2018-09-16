package ocrreader.webserver

import android.util.Log
import com.koushikdutta.async.http.server.AsyncHttpServer
import java.io.IOException


class GameServer(private val gameServer: HttpGameServer, private val webSocketHandler: WebSocketHandler) {
    private lateinit var hostName: String
    private lateinit var topicName: String
    private var listenPort: Int = 0

    var server = AsyncHttpServer()
    val webAddress: String
        get() = computeWebAddress()
    val webSocketAddress: String
        get() = "ws://$hostName:$listenPort/$topicName"

    @Throws(IOException::class)
    fun start(hostName: String, listenPort: Int, topicName: String) {
        this.hostName = hostName
        this.listenPort = listenPort
        this.topicName = topicName

        server.websocket("/$topicName", webSocketHandler)
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
        Log.d(TAG, "Stopping game server")
        server.stop()
    }

    companion object {
        private const val TAG = "WebServer"
        private const val DEFAULT_HTTP_PORT: Int = 80
    }
}
