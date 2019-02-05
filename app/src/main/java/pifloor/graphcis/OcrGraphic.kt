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
package pifloor.graphcis

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.Log
import com.google.android.gms.vision.text.TextBlock
import pifloor.ui.camera.GraphicOverlay
import java.util.*

/**
 * Graphic instance for rendering TextBlock position, size, and ID within an associated graphic
 * overlay view.
 */
abstract class OcrGraphic internal constructor(overlay: GraphicOverlay<*>, val textBlock: TextBlock) : GraphicOverlay.Graphic(overlay) {
    abstract fun getRectPaint(): Paint
    abstract fun getTextPaint(): Paint

    init {
        if (!stringHashes.containsKey(textBlock.value)) {
            stringHashes[textBlock.value] = stringHashes.size
        }
        // Redraw the overlay, as this graphic has been added.
        postInvalidate()
    }

    /**
     * Checks whether a point is within the bounding box of this graphic.
     * The provided point should be relative to this graphic's containing overlay.
     *
     * @param x An x parameter in the relative context of the canvas.
     * @param y A y parameter in the relative context of the canvas.
     * @return True if the provided point is contained within this graphic's bounding box.
     */
    override fun contains(x: Float, y: Float): Boolean {
        val text = this.textBlock ?: return false
        val rect = RectF(text.boundingBox)
        rect.left = translateX(rect.left)
        rect.top = translateY(rect.top)
        rect.right = translateX(rect.right)
        rect.bottom = translateY(rect.bottom)
        return rect.left < x && rect.right > x && rect.top < y && rect.bottom > y
    }

    override fun getValue(): String {
        return textBlock.value
    }

    /**
     * Draws the text block annotations for position, size, and raw value on the supplied canvas.
     */
    override fun draw(canvas: Canvas) {
        val text = this.textBlock ?: return
        // Draws the bounding box around the TextBlock.
        val rect = RectF(text.boundingBox)
        rect.left = translateX(rect.left)
        rect.top = translateY(rect.top)
        rect.right = translateX(rect.right)
        rect.bottom = translateY(rect.bottom)
        val height = rect.bottom - rect.top
        val width = rect.right - rect.left
        if(height < 50) {
            //rect.top = translateY(rect.top - (50 - height) / 2)
            rect.top = translateY(rect.top - 20F)
            //rect.bottom = translateY(rect.bottom + (50 - height) / 2)
            rect.bottom = translateY(rect.bottom + 20F)
        }

        if(width < 100) {
            //rect.left = translateX(rect.left - (100 - width) / 2)
            rect.left = translateX(rect.left - 20F)
            //rect.right = translateX(rect.right + (100 - width) / 2)
            rect.right = translateX(rect.right + 20F)
        }
        //Log.i("dodo",(rect.bottom - rect.top).toString() + " H ")
        //Log.i("dodo",(rect.right - rect.left).toString() + " V ")
        //canvas.drawRect(rect, getRectPaint())
        // Break the text into multiple lines and draw each one according to its own bounding box.
        val textComponents = text.components
        for (currentText in textComponents) {
            val left = translateX(currentText.boundingBox.left.toFloat())
            val bottom = translateY(currentText.boundingBox.bottom.toFloat())
            canvas.drawText(currentText.value, left, bottom, getTextPaint())
        }
    }

    companion object {
        private val stringHashes = HashMap<String, Int>()
    }
}
