/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pifloor.processing

import android.hardware.Camera
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.gms.vision.text.TextBlock
import pifloor.R
import pifloor.graphcis.OcrGraphic
import pifloor.injection.PiFloorApplication
import pifloor.ui.camera.OcrGraphicOverlay
import pifloor.utils.VirtualGrid
import pifloor.webserver.ServerFragment
import pifloor.webserver.WebSocketHandler
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import javax.inject.Inject

/**
 * Activity for the multi-tracker app.  This app detects text and displays the value with the
 * rear facing camera. During detection overlay graphics are drawn to indicate the position,
 * size, and contents of each TextBlock.
 */
class GameModeActivity : AppCompatActivity(), OcrCaptureFragment.OcrSelectionListener, Subscriber<ArrayList<TextBlock>> {
    private lateinit var captureFragment: OcrCaptureFragment

    @Inject
    lateinit var virtualGrid: VirtualGrid

    @Inject
    lateinit var webSocketHandler: WebSocketHandler

    private val choiceMap = hashMapOf<String, String>()

    var mTopToolbar: Toolbar? = null

    private var serverFragment: ServerFragment? = null

    /**
     * Initializes the UI and creates the detector pipeline.
     */
    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.activity_game_mode)
        mTopToolbar = findViewById(R.id.my_toolbar)
        setSupportActionBar(mTopToolbar)

        //webSocketHandler.addContext(this)

        (application as PiFloorApplication).component.inject(this)
        val tiles = intent.getStringArrayListExtra("tiles")

        for (tileIndex in tiles.indices) {
            val tileText = tiles[tileIndex]
            choiceMap[tileText] = tileIndex.toString()
        }
        loadFragment()
    }

    private fun loadFragment() {
        // read parameters from the intent used to launch the activity.
        val autoFocus = intent.getBooleanExtra("AutoFocus", true)
        val useFlash = intent.getBooleanExtra("UseFlash", false)
        captureFragment = OcrCaptureFragment.newInstance(autoFocus, useFlash)

        supportFragmentManager.beginTransaction()
                .add(R.id.container_game_fragment_holder, captureFragment).runOnCommit {
                    captureFragment.subscribe(this)
                }.commit()

        serverFragment = ServerFragment()

        supportFragmentManager.beginTransaction()
                .add(R.id.container_game_fragment_holder, serverFragment)
                .commit()

        //serverFragment!!.startServer()
        //displaySnackbar(serverFragment!!.hostNameTxt.text as String?,null,null)

    }

    private fun displaySnackbar(text: String?, actionName: String?, action: View.OnClickListener?) {
        var snack: Snackbar = Snackbar.make(findViewById(android.R.id.content), text!!, Snackbar.LENGTH_LONG)
                .setAction(actionName, action)

        var v: View = snack.getView()
        v.setBackgroundColor(getResources().getColor(R.color.green))
        snack.show()
    }

    var munItems : Menu? = null

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        munItems = menu
        menuInflater.inflate(R.menu.game_menu, menu)
        return true
    }

    var state : Boolean = false
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()

        if (id == R.id.info) {
            var res = if (serverFragment!!.hostNameTxt != null) serverFragment!!.hostNameTxt else "Not started yet"
            displaySnackbar(res, null, null)
            return true
        } else if (id == R.id.start) {

            if (state) {
                serverFragment!!.stopServer()
                item.setIcon(R.drawable.start_on)
            } else {
                serverFragment!!.startServer()
                item.setIcon(R.drawable.stop_on)
            }
            state = !state
            displaySnackbar(serverFragment!!.hostNameTxt, null, null)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Invoked after calling [Publisher.subscribe].
     *
     *
     * No data will start_off flowing until [Subscription.request] is invoked.
     *
     *
     * It is the responsibility of this [Subscriber] instance to call [Subscription.request] whenever more data is wanted.
     *
     *
     * The [Publisher] will send notifications only in response to [Subscription.request].
     *
     * @param s
     * [Subscription] that allows requesting data via [Subscription.request]
     */
    override fun onSubscribe(s: Subscription?) {}

    /**
     * Data notification sent by the [Publisher] in response to requests to [Subscription.request].
     *
     * @param items the element signaled
     */
    override fun onNext(items: ArrayList<TextBlock>) {
        // Check if the current detections mismatch the ones in gridHolder
        // TODO: Ensure that the server is running
        val choice = virtualGrid.findChoice(items) ?: return
        Log.i(TAG, "Current choice:$choice")
        if (choiceMap.contains(choice)) {
            webSocketHandler.broadcast(choiceMap[choice]!!)
        } else {
            webSocketHandler.broadcast(choice)
        }
    }

    /**
     * Failed terminal state.
     *
     *
     * No further events will be sent even if [Subscription.request] is invoked again.
     *
     * @param t the throwable signaled
     */
    override fun onError(t: Throwable?) {
        throw t!!
    }

    override fun onComplete() {
        Log.d(TAG, "Data stream ended")
    }

    override fun onOcrGraphicTap(ocrGraphic: OcrGraphic,
                                 graphicOverlay: OcrGraphicOverlay<OcrGraphic>): Boolean {
        // Nothing to be done
        return true
    }

    companion object {
        private const val TAG = "GameModeActivity"
    }
}
