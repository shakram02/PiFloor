package ocrreader.processing;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.common.api.CommonStatusCodes;

import java.util.HashSet;

import ocrreader.R;
import ocrreader.graphcis.CalibratedOcrGraphic;
import ocrreader.graphcis.OcrGraphic;
import ocrreader.ui.camera.OcrGraphicOverlay;

import static ocrreader.processing.OcrCaptureActivity.AutoFocus;
import static ocrreader.processing.OcrCaptureActivity.UseFlash;

public class GridCalibrationActivity extends FragmentActivity implements OcrCaptureFragment.OcrSelectionListener {
    protected static final String TAG = "CalibrationActivity";
    HashSet<String> calibratedGridText = new HashSet<>();
    public static final String GridElements = "GridElements";
    private static final int GRID_SIZE = 1;


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.calibrate_acitivity);

        loadFragment();
    }

    private void loadFragment() {
        // read parameters from the intent used to launch the activity.
        boolean autoFocus = getIntent().getBooleanExtra(AutoFocus, false);
        boolean useFlash = getIntent().getBooleanExtra(UseFlash, false);
        OcrCaptureFragment captureFragment = OcrCaptureFragment.newInstance(autoFocus, useFlash);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.calibrate_ocr_capture, captureFragment).commit();
    }

    void onDone() {
        Intent gridData = new Intent();
        gridData.putExtra(GridElements, calibratedGridText);
        setResult(CommonStatusCodes.SUCCESS, gridData);

        finish();
    }

    void onRecalibrateRequest() {
        calibratedGridText.clear();
    }

    @Override
    public boolean onOcrGraphicTap(OcrGraphic graphic, OcrGraphicOverlay<OcrGraphic> graphicOverlay) {
        graphicOverlay.remove(graphic);
        String text = graphic.getValue().toLowerCase();


        Log.d(TAG, "Calibrating:" + text);
        graphicOverlay.add(new CalibratedOcrGraphic(graphicOverlay, graphic.getTextBlock()));
        calibratedGridText.add(text);


        if (calibratedGridText.size() == GRID_SIZE) {
            onDone();
        }

        return true;
    }
}
