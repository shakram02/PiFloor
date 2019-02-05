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

package pifloor

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Optional
import io.reactivex.annotations.Nullable
import pifloor.injection.PiFloorApplication
import pifloor.processing.CalibrationModeActivity

class MainActivity : Activity() {

    private val prefName = "intro"
    private val value = "opened"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as PiFloorApplication).component.inject(this)
        ButterKnife.bind(this)
        val sharedPref = getSharedPreferences(prefName, Context.MODE_PRIVATE)
        if (!sharedPref.getBoolean(value, false)) {
            // First time to view
            val editor = sharedPref.edit()
            editor.putBoolean(value, true)
            editor.apply()
            startTutorialActivity()
        }
    }

    @Optional
    @OnClick(R.id.button_main_startGame)
    fun startCalibrationModeActivity() {
        val intent = Intent(this, CalibrationModeActivity::class.java)
        startActivity(intent)
    }
    @Optional
    @OnClick(R.id.button_main_startTutorial)
    fun startTutorialActivity() {
        val intent = Intent(this, IntroActivity::class.java)
        startActivity(intent)
    }
    @Optional
    @OnClick(R.id.button_main_upload)
    fun startUpload() {
        val intent = Intent(this, UploadActivity::class.java)
        startActivity(intent)
    }
}
