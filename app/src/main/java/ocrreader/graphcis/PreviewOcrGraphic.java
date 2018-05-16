package ocrreader.graphcis;

import android.graphics.Color;
import android.graphics.Paint;

import com.google.android.gms.vision.text.TextBlock;

import ocrreader.ui.camera.GraphicOverlay;

public class PreviewOcrGraphic extends OcrGraphic {
    private static Paint rectPaint;
    private static Paint textPaint;

    public PreviewOcrGraphic(GraphicOverlay overlay, TextBlock text) {
        super(overlay, text);

        if (rectPaint == null) {
            rectPaint = new Paint();
            rectPaint.setColor(Color.WHITE);
            rectPaint.setStyle(Paint.Style.STROKE);
            rectPaint.setStrokeWidth(4.0f);
        }

        if (textPaint == null) {
            textPaint = new Paint();
            textPaint.setColor(Color.WHITE);
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
