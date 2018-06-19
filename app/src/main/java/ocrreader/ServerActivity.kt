package ocrreader

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import ocrreader.webserver.ConnectionUtils
import ocrreader.webserver.GameServer
import ocrreader.webserver.HttpGameServer
import java.io.IOException
import javax.inject.Inject

class ServerActivity : Activity() {
    lateinit var server: GameServer
    private lateinit var connectionUtils: ConnectionUtils
    @BindView(R.id.txt_server_hostname)
    lateinit var hostNameTxt: TextView
    @Inject
    lateinit var httpServer: HttpGameServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server)
        (application as EdGridApplication).component.inject(this)
        ButterKnife.bind(this)

        connectionUtils = ConnectionUtils(applicationContext)
        // Run on localhost if no WiFi is present
        server = if (!connectionUtils.isConnectedToWifi()) {
            GameServer(LOCAL_HOST, PORT_NUMBER, TOPIC_NAME, httpServer)
        } else {
            GameServer(connectionUtils.getIpAddress(), PORT_NUMBER, TOPIC_NAME, httpServer)
        }
    }

    @OnClick(R.id.btn_server_startserver)
    fun startServer() {
        try {
            val addresses = "${server.webAddress}\n${server.webSocketAddress}"
            this.hostNameTxt.text = addresses

            this.server.start()
        } catch (e: IOException) {
            Log.e(TAG, "Failed to start server", e)
            throw RuntimeException(e)
        }
    }

    @OnClick(R.id.btn_server_stopserver)
    fun stopServer() {
        try {
            this.server.stop()
            this.hostNameTxt.text = "Stopped"
        } catch (e: IOException) {
            // TODO: display a useful message
            throw RuntimeException(e)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            this.server.stop()
        } catch (e: UninitializedPropertyAccessException) {
            Log.e(TAG, e::class.java.canonicalName, e)
        }
    }

    companion object {
        private const val PORT_NUMBER = 5444   // TODO make this configurable
        private const val TOPIC_NAME = "game"   // TODO make this configurable
        private const val LOCAL_HOST = "127.0.0.1"
        private val TAG = this::class.java.canonicalName.toString()
    }
}
