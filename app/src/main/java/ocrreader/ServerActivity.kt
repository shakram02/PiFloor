package ocrreader

import android.app.Activity
import android.net.wifi.WifiManager
import android.os.Bundle
import android.text.format.Formatter
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import ocrreader.webserver.ConnectionUtils
import ocrreader.webserver.GameServer

import java.io.IOException

class ServerActivity : Activity() {
    internal var server: GameServer? = null
    private var startServerBtn: Button? = null
    private var stopServerBtn: Button? = null
    private var hostNameTxt: TextView? = null
    private var connectionUtils: ConnectionUtils? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server)
        connectionUtils = ConnectionUtils(applicationContext)

        // TODO clean this up later
        if (!connectionUtils!!.isConnectedInWifi) {
            Toast.makeText(this, "Not connected to WIFI, App won't work", Toast.LENGTH_LONG).show()
            return
        }

        server = GameServer(connectionUtils!!.ipAddress, PORT_NUMBER)
        startServerBtn = findViewById(R.id.btnStartServer) as Button
        stopServerBtn = findViewById(R.id.btnStopServer) as Button
        hostNameTxt = findViewById(R.id.txtHostName) as TextView

        startServerBtn!!.setOnClickListener {
            try {
                runServer()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        stopServerBtn!!.setOnClickListener { stopServer() }

    }

    @Throws(IOException::class)
    private fun runServer() {
        val hostName = server!!.getHostname()
        this.hostNameTxt!!.text = hostName
        this.server!!.start()

        Log.i(DEBUG_TAG, "Address: " + connectionUtils!!.getHostAddress(server))
    }

    private fun stopServer() {
        this.server!!.stop()
        this.hostNameTxt!!.text = "Stopped"
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this.server != null) {
            this.server!!.stop()
        }
    }

    companion object {
        private val PORT_NUMBER = 5444   // TODO make this configurable

        private val DEBUG_TAG = "SERVER_ACTIVITY"
    }
}
