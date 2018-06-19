package ocrreader.webserver

import android.util.Log
import com.koushikdutta.async.http.server.AsyncHttpServer
import java.io.IOException


class GameServer(private val hostName: String, private val listenPort: Int, private val topicName: String) {
    var server = AsyncHttpServer()
    val address: String
        get() = hostName
    val port: Int
        get() = listenPort

    @Throws(IOException::class)
    fun start() {
        server.websocket("/$topicName", WebSocketGameServer())
        server.get("/.*", HttpGameServer())

        server.listen(listenPort)
        Log.i(TAG, "WebSocket: ws://$hostName:$listenPort/$topicName")
    }

    fun stop() {
        server.stop()
    }

    companion object {
        private const val TAG = "WebServer"
    }
}
