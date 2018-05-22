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

import android.util.SparseArray

import ocrreader.graphcis.CalibratedOcrGraphic
import ocrreader.graphcis.OcrGraphic
import ocrreader.ui.camera.OcrGraphicOverlay
import ocrreader.graphcis.PreviewOcrGraphic

import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.text.TextBlock
import com.google.common.base.Optional

import java.util.ArrayList
import java.util.HashSet

/**
 * A very simple Processor which receives detected TextBlocks and adds them to the overlay
 * as OcrGraphics.
 */
internal class OcrDetectorProcessor(private val mGraphicOverlay: OcrGraphicOverlay<OcrGraphic>) : Detector.Processor<TextBlock> {
    private val gridText = HashSet<String>()

    /**
     * Called by the detector to deliver detection results.
     * If your application called for it, this could be a place to check for
     * equivalent detections by tracking TextBlocks that are similar in location and content from
     * previous frames, or reduce noise by eliminating TextBlocks that have not persisted through
     * multiple detections.
     */
    override fun receiveDetections(detections: Detector.Detections<TextBlock>) {
        val items = detections.detectedItems
        val newFrameGraphics = ArrayList<OcrGraphic>()

        for (i in 0 until items.size()) {
            val item = items.valueAt(i)
            val maybeGraphic = mGraphicOverlay.getByContent(item.value)
            val text = item.value.toLowerCase()

            // Check if the text was detected before.
            // If it was detected: update location
            // If it was calibrated then went out of view: Add calibrated
            if (maybeGraphic.isPresent) {
                // Update location and after
                // checking the graphic's type Preview/Calibrated
                val graphic = maybeGraphic.get()

                if (graphic is PreviewOcrGraphic) {
                    newFrameGraphics.add(PreviewOcrGraphic(mGraphicOverlay, item))
                } else {
                    newFrameGraphics.add(CalibratedOcrGraphic(mGraphicOverlay, item))
                    gridText.add(text)
                }

            } else if (gridText.contains(text)) {
                newFrameGraphics.add(CalibratedOcrGraphic(mGraphicOverlay, item))
            } else {
                newFrameGraphics.add(PreviewOcrGraphic(mGraphicOverlay, item))
            }
        }

        mGraphicOverlay.clear()
        for (g in newFrameGraphics) {
            mGraphicOverlay.add(g)
        }
    }

    /**
     * Frees the resources associated with this detection processor.
     */
    override fun release() {
        mGraphicOverlay.clear()
    }
}
