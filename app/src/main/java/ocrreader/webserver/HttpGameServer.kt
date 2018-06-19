package ocrreader.webserver

import android.util.Log
import com.koushikdutta.async.http.server.AsyncHttpServerRequest
import com.koushikdutta.async.http.server.AsyncHttpServerResponse
import com.koushikdutta.async.http.server.HttpServerRequestCallback

class HttpGameServer : HttpServerRequestCallback {
    override fun onRequest(request: AsyncHttpServerRequest, response: AsyncHttpServerResponse) {
        Log.i(TAG, "HTTP:${request.method}, ${request.path}")
        // TODO: parse the path and load the requested resource
        var msg = "<html>"
        msg += "<body><h1>Hello server</h1>\n"
        msg += "<script src=\"myscripts.js\"></script>"
        msg += "<p>Hello, " + "!</p></body></html>"

        response.send(msg)
    }

    companion object {
        private const val TAG = "HttpGameServer"
    }
}
