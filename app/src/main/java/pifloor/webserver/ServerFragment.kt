package pifloor.webserver

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import pifloor.R
import pifloor.injection.PiFloorApplication
import java.io.IOException
import javax.inject.Inject

class ServerFragment : Fragment() {
    private lateinit var unbinder: Unbinder

    var hostNameTxt: String? = null

    @Inject
    lateinit var server: GameServer

    @Inject
    lateinit var connectionUtils: ConnectionUtils

    private lateinit var fragmentView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentView = inflater.inflate(R.layout.fragment_server, container, false)
        unbinder = ButterKnife.bind(this, fragmentView)

        return fragmentView
    }

    override fun onAttach(context: Context?) {
        (this.activity!!.application as PiFloorApplication).component.inject(this)
        super.onAttach(context)
    }

    fun startServer() {
        // Run on localhost if no WiFi is present
        if (!connectionUtils.isConnectedToWifi()) {
            server.start(LOCAL_HOST, PORT_NUMBER, TOPIC_NAME)
        } else {
            server.start(connectionUtils.getIpAddress(), PORT_NUMBER, TOPIC_NAME)
        }

        displayAddresses()
    }

    private fun displayAddresses() {
        val addresses = "${server.webAddress}\n${server.webSocketAddress}"
        this.hostNameTxt = addresses
    }

    fun stopServer() {
        try {
            this.server.stop()
            this.hostNameTxt = "Stopped"
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
        // TODO make this configurable?, This is hardcoded in the client code [Change both]
        private const val TOPIC_NAME = "game"
        private const val LOCAL_HOST = "127.0.0.1"
        private val TAG = this::class.java.canonicalName.toString()
    }
}
