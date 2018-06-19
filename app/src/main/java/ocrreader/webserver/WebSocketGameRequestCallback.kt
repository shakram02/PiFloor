package ocrreader.webserver

import android.util.Log
import com.koushikdutta.async.callback.CompletedCallback
import com.koushikdutta.async.http.WebSocket
import com.koushikdutta.async.http.server.AsyncHttpServer
import com.koushikdutta.async.http.server.AsyncHttpServerRequest

class WebSocketGameRequestCallback : AsyncHttpServer.WebSocketRequestCallback {
    private val sockets = ArrayList<WebSocket>()

    override fun onConnected(webSocket: WebSocket, request: AsyncHttpServerRequest?) {
        sockets.add(webSocket)
        Log.i(TAG, "A client connected ${request!!.method}")

        //Use this to clean up any references to your webSocket
        webSocket.closedCallback = CompletedCallback { ex ->
            try {
                if (ex != null)
                    Log.e("WebSocket", "Error")
            } finally {
                sockets.remove(webSocket)
            }
        }
        webSocket.stringCallback = WebSocket.StringCallback { s ->
            webSocket.send("Welcome Client!")
        }
    }

    companion object {
        private val TAG = this::class.java.canonicalName
    }
}
