package ocrreader

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
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
    @BindView(R.id.btn_server_startserver)
    lateinit var startServerBtn: Button
    @BindView(R.id.btn_server_stopserver)
    lateinit var stopServerBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server)
        ButterKnife.bind(this)

        connectionUtils = ConnectionUtils(applicationContext)
        // TODO clean this up later, maybe run the server locally if no wifi
        if (!connectionUtils.isConnectedInWifi) {
            Toast.makeText(this, "Not connected to WIFI, Server won't work", Toast.LENGTH_LONG).show()
            startServerBtn.isEnabled = false
            stopServerBtn.isEnabled = false
            return
        }

        server = GameServer(connectionUtils.ipAddress, PORT_NUMBER, TOPIC_NAME)

    }


    @OnClick(R.id.btn_server_startserver)
    fun startServer() {
        try {
            val hostName = server.serverAddress
            this.hostNameTxt.text = hostName
            this.server.start()
            Log.i(TAG, "Address: " + connectionUtils.getHostAddress(hostName, server.port))
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
        private val TAG = this::class.java.canonicalName.toString()
    }
}
