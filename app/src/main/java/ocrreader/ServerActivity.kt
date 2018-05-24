package ocrreader

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import ocrreader.webserver.ConnectionUtils
import ocrreader.webserver.GameServer
import java.io.IOException

class ServerActivity : Activity() {
    internal lateinit var server: GameServer
    private lateinit var connectionUtils: ConnectionUtils
    @BindView(R.id.txt_server_hostname)
    lateinit var hostNameTxt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server)
        ButterKnife.bind(this)

        connectionUtils = ConnectionUtils(applicationContext)
        // TODO clean this up later
        if (!connectionUtils.isConnectedInWifi) {
            Toast.makeText(this, "Not connected to WIFI, App won't work", Toast.LENGTH_LONG).show()
            return
        }

        server = GameServer(connectionUtils.ipAddress, PORT_NUMBER)

    }


    @OnClick(R.id.btn_server_startserver)
    fun startServer() {
        try {
            val hostName = server.getHostname()
            this.hostNameTxt.text = hostName
            this.server.start()
        } catch (e: IOException) {
            Log.e(TAG, "Failed to start server", e)
            throw RuntimeException(e)
        }
        Log.i(TAG, "Address: " + connectionUtils.getHostAddress(server))
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
        this.server.stop()
    }

    companion object {
        private const val PORT_NUMBER = 5444   // TODO make this configurable
        private val TAG = this::class.java.canonicalName.toString()
    }
}
