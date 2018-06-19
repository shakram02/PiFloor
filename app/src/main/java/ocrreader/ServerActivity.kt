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
import java.io.IOException

class ServerActivity : Activity() {
    lateinit var server: GameServer
    private lateinit var connectionUtils: ConnectionUtils
    @BindView(R.id.txt_server_hostname)
    lateinit var hostNameTxt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server)
        ButterKnife.bind(this)

        connectionUtils = ConnectionUtils(applicationContext)
        // Run on localhost if no WiFi is present
        server = if (!connectionUtils.isConnectedToWifi()) {
            GameServer(LOCAL_HOST, PORT_NUMBER, TOPIC_NAME)
        } else {
            GameServer(connectionUtils.getIpAddress(), PORT_NUMBER, TOPIC_NAME)
        }
    }

    @OnClick(R.id.btn_server_startserver)
    fun startServer() {
        try {
            this.hostNameTxt.text = server.address
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
