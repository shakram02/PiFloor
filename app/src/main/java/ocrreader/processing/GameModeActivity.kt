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
package ocrreader.processing

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import ocrreader.MainActivity.Companion.AutoFocus
import ocrreader.MainActivity.Companion.UseFlash
import ocrreader.R
import ocrreader.graphcis.OcrGraphic
import ocrreader.ui.camera.OcrGraphicOverlay

/**
 * Activity for the multi-tracker app.  This app detects text and displays the value with the
 * rear facing camera. During detection overlay graphics are drawn to indicate the position,
 * size, and contents of each TextBlock.
 */
class GameModeActivity : FragmentActivity(), OcrCaptureFragment.OcrSelectionListener {
    /**
     * Initializes the UI and creates the detector pipeline.
     */
    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        // This is an extremely lousy hack so I'm able to use the view twice be inheriting this class
        // and changing the viewId value to the desired layout. TODO resolve this cleanly!
        setContentView(R.layout.activity_game_mode)
        loadFragment()
    }

    private fun loadFragment() {
        // read parameters from the intent used to launch the activity.
        val autoFocus = intent.getBooleanExtra(AutoFocus, false)
        val useFlash = intent.getBooleanExtra(UseFlash, false)
        val captureFragment = OcrCaptureFragment.newInstance(autoFocus, useFlash)

        supportFragmentManager.beginTransaction()
                .add(R.id.container_game_fragment_holder, captureFragment).commit()
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
