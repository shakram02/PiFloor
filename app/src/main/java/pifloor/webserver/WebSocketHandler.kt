package pifloor.webserver

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import android.util.Log
import com.google.android.gms.flags.impl.SharedPreferencesFactory.getSharedPreferences
import com.koushikdutta.async.callback.CompletedCallback
import com.koushikdutta.async.http.WebSocket
import com.koushikdutta.async.http.server.AsyncHttpServer
import com.koushikdutta.async.http.server.AsyncHttpServerRequest
import pifloor.processing.GameModeActivity
import pifloor.processing.GameModeActivity_MembersInjector

class WebSocketHandler : AsyncHttpServer.WebSocketRequestCallback, LifecycleObserver {
    private var clients = ArrayList<WebSocket>()
    private var context : Context? = null

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
            //Todo: Start Saving file
            /*val sharedPreference =  context!!.getSharedPreferences("questions", Context.MODE_PRIVATE)
            var editor = sharedPreference.edit()
            editor.putString("questions",string)
            editor.commit()*/
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

    fun addContext(context: Context) {
        this.context = context
    }

    companion object {
        private const val TAG = "WebSocketHandler"
    }
}
