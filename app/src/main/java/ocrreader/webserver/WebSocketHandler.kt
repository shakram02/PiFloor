package ocrreader.webserver

import android.util.Log
import com.koushikdutta.async.callback.CompletedCallback
import com.koushikdutta.async.http.WebSocket
import com.koushikdutta.async.http.server.AsyncHttpServer
import com.koushikdutta.async.http.server.AsyncHttpServerRequest

class WebSocketHandler : AsyncHttpServer.WebSocketRequestCallback {
    private var client: WebSocket? = null

    private val onCloseCallback = CompletedCallback { ex ->
        try {
            if (ex != null)
                Log.e("WebSocket", "Error")
        } finally {
            client = null
        }
    }

    override fun onConnected(webSocket: WebSocket, request: AsyncHttpServerRequest?) {
        if (client != null) {
            Log.wtf(TAG, "Another client tried to connect to WebSocket server")
            webSocket.close()
        }

        client = webSocket
        Log.d(TAG, "A client connected")

        //Use this to clean up any references to your webSocket
        webSocket.closedCallback = onCloseCallback

        webSocket.stringCallback = WebSocket.StringCallback { string ->
            Log.i(TAG, "Client sent:$string")
            webSocket.send("Welcome Client!")
        }

        webSocket.endCallback = onCloseCallback
    }

    fun broadcast(message: String) {
        if (client == null) return

        client?.send(message)
        Log.d(TAG, "Sending:$message")
    }

    companion object {
        private const val TAG = "WebSocketHandler"
    }
}
