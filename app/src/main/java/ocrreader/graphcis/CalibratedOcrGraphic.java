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
package ocrreader.graphcis;

import android.graphics.Color;
import android.graphics.Paint;

import com.google.android.gms.vision.text.TextBlock;

import ocrreader.ui.camera.GraphicOverlay;

/**
 * Graphic instance for rendering TextBlock position, size, and ID within an associated graphic
 * overlay view.
 */
public class CalibratedOcrGraphic extends OcrGraphic {
    private static Paint rectPaint;
    private static Paint textPaint;

    public CalibratedOcrGraphic(GraphicOverlay overlay, TextBlock text) {
        super(overlay, text);

        if (rectPaint == null) {
            rectPaint = new Paint();
            rectPaint.setColor(Color.GREEN);
            rectPaint.setStyle(Paint.Style.STROKE);
            rectPaint.setStrokeWidth(4.0f);
        }

        if (textPaint == null) {
            textPaint = new Paint();
            textPaint.setColor(Color.GREEN);
            textPaint.setTextSize(54.0f);
        }


    }

    @Override
    protected Paint getRectPaint() {
        return rectPaint;
    }

    @Override
    protected Paint getTextPaint() {
        return textPaint;
    }
}
