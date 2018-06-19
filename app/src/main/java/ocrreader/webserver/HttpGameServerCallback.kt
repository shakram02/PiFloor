package ocrreader.webserver

import android.util.Log
import com.koushikdutta.async.http.server.AsyncHttpServerRequest
import com.koushikdutta.async.http.server.AsyncHttpServerResponse
import com.koushikdutta.async.http.server.HttpServerRequestCallback

class HttpGameServerCallback : HttpServerRequestCallback {
    override fun onRequest(request: AsyncHttpServerRequest, response: AsyncHttpServerResponse) {
        Log.i(TAG, "HTTP:${request.method}, ${request.path}")
        response.send("Hello!!!")
        val d = 43
    }

    companion object {
        private val TAG = this::class.java.canonicalName
    }
}
