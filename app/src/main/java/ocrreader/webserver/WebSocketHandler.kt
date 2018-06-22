package ocrreader.webserver

import android.util.Log
import com.koushikdutta.async.callback.CompletedCallback
import com.koushikdutta.async.http.WebSocket
import com.koushikdutta.async.http.server.AsyncHttpServer
import com.koushikdutta.async.http.server.AsyncHttpServerRequest

class WebSocketHandler : AsyncHttpServer.WebSocketRequestCallback {
    private val sockets = ArrayList<WebSocket>()

    override fun onConnected(webSocket: WebSocket, request: AsyncHttpServerRequest?) {
        sockets.add(webSocket)
        //Use this to clean up any references to your webSocket
        webSocket.closedCallback = CompletedCallback { ex ->
            try {
                if (ex != null)
                    Log.e("WebSocket", "Error")
            } finally {
                sockets.remove(webSocket)
            }
        }
        webSocket.stringCallback = WebSocket.StringCallback { string ->
            Log.i(TAG, "Client sent:$string")
            webSocket.send("Welcome Client!")
        }
    }

    fun broadcast(message: String) {
        for (socket in sockets) {
            socket.send(message)
        }
    }

    companion object {
        private const val TAG = "WebSocketHandler"
    }
}
