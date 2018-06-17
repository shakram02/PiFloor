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

import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.text.TextBlock
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import java.util.*
import kotlin.collections.ArrayList

/**
 * A very simple Processor which receives detected TextBlocks and adds them to the overlay
 * as OcrGraphics.
 */
internal class OcrDetectorProcessor : Detector.Processor<TextBlock>, Publisher<ArrayList<TextBlock>> {
    private val subscribers = HashSet<Subscriber<in ArrayList<TextBlock>>>()

    /**
     * Called by the detector to deliver detection results.
     * If your application called for it, this could be a place to check for
     * equivalent detections by tracking TextBlocks that are similar in location and content from
     * previous frames, or reduce noise by eliminating TextBlocks that have not persisted through
     * multiple detections.
     */
    override fun receiveDetections(detections: Detector.Detections<TextBlock>) {
        val items = detections.detectedItems
        val frameTextBlocks = ArrayList<TextBlock>()

        for (i in 0 until items.size()) {
            if (items.valueAt(i).value == null) continue
            frameTextBlocks.add(items.valueAt(i))
        }

        for (subscriber in subscribers) {
            subscriber.onNext(frameTextBlocks)
        }
    }

    /**
     * Frees the resources associated with this detection processor.
     */
    override fun release() {
        subscribers.map { s -> s.onComplete() }
    }

    override fun subscribe(s: Subscriber<in ArrayList<TextBlock>>?) {
        subscribers.add(s!!)
    }
}
