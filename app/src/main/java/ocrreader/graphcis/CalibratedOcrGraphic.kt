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
package ocrreader.graphcis

import android.graphics.Color
import android.graphics.Paint

import com.google.android.gms.vision.text.TextBlock

import ocrreader.ui.camera.GraphicOverlay

/**
 * Graphic instance for rendering TextBlock position, size, and ID within an associated graphic
 * overlay view.
 */
class CalibratedOcrGraphic(overlay: GraphicOverlay<*>, text: TextBlock) : OcrGraphic(overlay, text) {

    init {

        if (rectPaint == null) {
            rectPaint = Paint()
            rectPaint!!.color = Color.GREEN
            rectPaint!!.style = Paint.Style.STROKE
            rectPaint!!.strokeWidth = 4.0f
        }

        if (textPaint == null) {
            textPaint = Paint()
            textPaint!!.color = Color.GREEN
            textPaint!!.textSize = 54.0f
        }


    }

    override fun getRectPaint(): Paint? {
        return rectPaint
    }

    override fun getTextPaint(): Paint? {
        return textPaint
    }

    companion object {
        private var rectPaint: Paint? = null
        private var textPaint: Paint? = null
    }
}
