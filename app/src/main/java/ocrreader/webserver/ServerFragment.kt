package ocrreader.webserver

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Unbinder
import ocrreader.R
import ocrreader.injection.EdGridApplication
import java.io.IOException
import javax.inject.Inject

class ServerFragment : Fragment() {
    private lateinit var unbinder: Unbinder

    @BindView(R.id.txt_server_hostname)
    lateinit var hostNameTxt: TextView

    @Inject
    lateinit var server: GameServer

    @Inject
    lateinit var connectionUtils: ConnectionUtils

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val fragmentView = inflater!!.inflate(R.layout.fragment_server, container, false)
        unbinder = ButterKnife.bind(this, fragmentView)

        return fragmentView
    }

    override fun onAttach(context: Context?) {
        (this.activity.application as EdGridApplication).component.inject(this)
        super.onAttach(context)
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

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()
    }

    companion object {
        private const val PORT_NUMBER = 5444   // TODO make this configurable
        private const val TOPIC_NAME = "game"   // TODO make this configurable
        private const val LOCAL_HOST = "127.0.0.1"
        private val TAG = this::class.java.canonicalName.toString()
    }
}
