package ocrreader

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import ocrreader.injection.EdGridApplication
import ocrreader.webserver.ConnectionUtils
import ocrreader.webserver.GameServer
import java.io.IOException
import javax.inject.Inject

class ServerActivity : Activity() {
    @BindView(R.id.txt_server_hostname)
    lateinit var hostNameTxt: TextView

    @Inject
    lateinit var server: GameServer

    @Inject
    lateinit var connectionUtils: ConnectionUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server)
        (application as EdGridApplication).component.inject(this)
        ButterKnife.bind(this)
    }

    @OnClick(R.id.btn_server_startserver)
    fun startServer() {
        // Run on localhost if no WiFi is present
        if (!connectionUtils.isConnectedToWifi()) {
            server.start(LOCAL_HOST, PORT_NUMBER, TOPIC_NAME)
        } else {
            server.start(connectionUtils.getIpAddress(), PORT_NUMBER, TOPIC_NAME)
        }

        val addresses = "${server.webAddress}\n${server.webSocketAddress}"
        this.hostNameTxt.text = addresses
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
