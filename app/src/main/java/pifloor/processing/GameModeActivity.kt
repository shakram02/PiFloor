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

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import com.google.android.gms.vision.text.TextBlock
import pifloor.MainActivity.Companion.AutoFocus
import pifloor.MainActivity.Companion.UseFlash
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

    /**
     * Initializes the UI and creates the detector pipeline.
     */
    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.activity_game_mode)
        setSupportActionBar(findViewById(R.id.toolbar_game_activity) as Toolbar)

        (application as PiFloorApplication).component.inject(this)
        loadFragment()
    }

    private fun loadFragment() {
        // read parameters from the intent used to launch the activity.
        // TODO: fix parameter resolution (it's not working)
        val autoFocus = intent.getBooleanExtra(AutoFocus, true)
        val useFlash = intent.getBooleanExtra(UseFlash, false)
        captureFragment = OcrCaptureFragment.newInstance(autoFocus, useFlash)

        supportFragmentManager.beginTransaction()
                .add(R.id.container_game_fragment_holder, captureFragment).runOnCommit {
                    captureFragment.subscribe(this)
                }.commit()

        val serverFragment = ServerFragment()

        supportFragmentManager.beginTransaction()
                .add(R.id.container_game_fragment_holder, serverFragment)
                .commit()
    }

    /**
     * Invoked after calling [Publisher.subscribe].
     *
     *
     * No data will start flowing until [Subscription.request] is invoked.
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

        if (NUMERIC_NUMBER.contains(choice)) {
            webSocketHandler.broadcast(NUMERIC_NUMBER[choice]!!)
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

        // TODO: the user should configure the indexes in a separate activity
        // those indexes will create this hash map
        private val NUMERIC_NUMBER = hashMapOf(
                Pair("one", "1"),
                Pair("two", "2"),
                Pair("three", "3"),
                Pair("four", "4"),
                Pair("five", "5"),
                Pair("six", "6"),
                Pair("seven", "7"),
                Pair("eight", "8"),
                Pair("nine", "9")
        )
    }
}
