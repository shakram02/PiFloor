package ocrreader.processing;

import android.os.Bundle;
import android.util.Log;

import java.util.HashSet;

import ocrreader.R;
import ocrreader.graphcis.CalibratedOcrGraphic;
import ocrreader.graphcis.OcrGraphic;

public class GridCalibrationActivity extends OcrCaptureActivity {
    HashSet<String> calibratedGridText = new HashSet<>();

    @Override
    public void onCreate(Bundle icicle) {
        viewId = R.layout.grid_calibration;
        super.onCreate(icicle);
    }

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
