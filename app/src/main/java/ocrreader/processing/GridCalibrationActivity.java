package ocrreader.processing;

import android.util.Log;

import com.google.android.gms.vision.text.TextBlock;

import java.util.HashSet;

import ocrreader.graphcis.CalibratedOcrGraphic;
import ocrreader.graphcis.OcrGraphic;

public class GridCalibrationActivity extends OcrCaptureActivity {
    HashSet<String> calibratedGridText = new HashSet<>();

    @Override
    boolean onTap(float rawX, float rawY) {
        OcrGraphic graphic = (OcrGraphic) mGraphicOverlay.getGraphicAtLocation(rawX, rawY);


        if (graphic != null) {
            mGraphicOverlay.remove(graphic);
            String text = graphic.getValue().toLowerCase();


            Log.d(TAG, "Calibrating:" + text);
            mGraphicOverlay.add(new CalibratedOcrGraphic(mGraphicOverlay, graphic.getTextBlock()));
            calibratedGridText.add(text);

        } else {
            Log.d(TAG, "no text detected");
        }

        return true;
    }
}
