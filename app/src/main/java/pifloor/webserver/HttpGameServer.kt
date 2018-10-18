package pifloor.webserver

import android.app.Application
import android.util.Log
import android.webkit.MimeTypeMap
import com.koushikdutta.async.http.server.AsyncHttpServerRequest
import com.koushikdutta.async.http.server.AsyncHttpServerResponse
import com.koushikdutta.async.http.server.HttpServerRequestCallback
import java.io.InputStreamReader
import java.lang.IllegalStateException
import java.net.URLConnection


class HttpGameServer(private val context: Application) : HttpServerRequestCallback {
    private val fileNameMap = URLConnection.getFileNameMap()

    override fun onRequest(request: AsyncHttpServerRequest, response: AsyncHttpServerResponse) {
        val assetManager = context.assets
        val requestedFile: String = sanitizeRequestedPath(request.path)
        Log.d(TAG, "HTTP:${request.method}, $requestedFile")

        try {
            if (requestedFile.matches(Regex(EMPTY_FILE_NAME_REGEX))) {
                response.end()
                return
            }

            if (!assetManager.list("").contains(requestedFile)) {
                response.end()
                return
            }

            val reader = InputStreamReader(assetManager.open(requestedFile))
            val extension = MimeTypeMap.getFileExtensionFromUrl(requestedFile)

            if (extension == null) {
                response.end()
                throw IllegalStateException("The file $requestedFile doesn't have proper extension")
            }

            // HACK for some reason, javascript file isn't identified
            val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)?: "application/javascript"
            Log.d(TAG, "Requested file: $requestedFile, Type:$mimeType")

            response.send(mimeType, reader.readText())

        } catch (e: Exception) {
            Log.e(TAG, "Failed to load [$requestedFile]", e)
        }
    }

    private fun sanitizeRequestedPath(path: String): String {
        val withoutSlash = path.replace("/", "")
        return if (withoutSlash.matches(Regex(FILE_NAME_WITH_EXTENSION_REGEX))) {
            withoutSlash
        } else {
            if (withoutSlash.isEmpty()) {
                // Root was requested
                INDEX_HTML_FILE_NAME
            } else {
                "$withoutSlash.html"
            }
        }
    }

    companion object {
        private const val TAG = "HttpGameServer"
        private const val EMPTY_FILE_NAME_REGEX = "\\..*"
        private const val FILE_NAME_WITH_EXTENSION_REGEX = ".*\\..*"
        private const val INDEX_HTML_FILE_NAME = "index.html"
    }
}
