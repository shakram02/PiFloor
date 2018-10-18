package pifloor.webserver

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import com.koushikdutta.async.callback.CompletedCallback
import com.koushikdutta.async.http.WebSocket
import com.koushikdutta.async.http.server.AsyncHttpServer
import com.koushikdutta.async.http.server.AsyncHttpServerRequest

class WebSocketHandler : AsyncHttpServer.WebSocketRequestCallback, LifecycleObserver {
    private var clients = ArrayList<WebSocket>()

    private val onCloseCallback = CompletedCallback { ex ->
        try {
            if (ex != null)
                Log.e("WebSocket", "Error ${ex.message}")
        } finally {
            clients.clear()
        }
    }

    override fun onConnected(webSocket: WebSocket, request: AsyncHttpServerRequest?) {
        clients.add(webSocket)
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
        for (client in clients) {
            client.send(message)
        }

        Log.d(TAG, "Sending:$message")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun shutdown() {
        for (client in clients) {
            client.close()
        }

        Log.d(TAG, "Shutting down client")
    }

    companion object {
        private const val TAG = "WebSocketHandler"
    }
}
