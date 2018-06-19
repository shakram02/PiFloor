package ocrreader.webserver

import android.app.Application
import android.util.Log
import com.koushikdutta.async.http.server.AsyncHttpServerRequest
import com.koushikdutta.async.http.server.AsyncHttpServerResponse
import com.koushikdutta.async.http.server.HttpServerRequestCallback
import java.io.InputStreamReader

class HttpGameServer(private val context: Application) : HttpServerRequestCallback {
    override fun onRequest(request: AsyncHttpServerRequest, response: AsyncHttpServerResponse) {
        Log.i(TAG, "HTTP:${request.method}, ${request.path}")
        // TODO: parse the path and load the requested resource
        if (request.path == "/") {
            try {
                val assetManager = context.assets

                val reader = InputStreamReader(assetManager.open("index.html"))
                val page = reader.readText()
                Log.i(TAG, "Page Result:$page")
                response.send(page)

            } catch (e: Exception) {
                Log.e(TAG, "Failed to load web page", e)
            }
            return
        }

        response.send("Hello, nothing is here")
    }

    companion object {
        private const val TAG = "HttpGameServer"
    }
}
