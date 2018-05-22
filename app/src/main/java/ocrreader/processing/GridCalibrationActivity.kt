package ocrreader.processing


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log

import com.google.android.gms.common.api.CommonStatusCodes

import java.util.HashSet

import ocrreader.R
import ocrreader.graphcis.CalibratedOcrGraphic
import ocrreader.graphcis.OcrGraphic
import ocrreader.ui.camera.OcrGraphicOverlay

import ocrreader.processing.OcrCaptureActivity.AutoFocus
import ocrreader.processing.OcrCaptureActivity.UseFlash

class GridCalibrationActivity : FragmentActivity(), OcrCaptureFragment.OcrSelectionListener {
    internal var calibratedGridText = HashSet<String>()


    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.calibrate_acitivity)

        loadFragment()
    }

    private fun loadFragment() {
        // read parameters from the intent used to launch the activity.
        val autoFocus = intent.getBooleanExtra(AutoFocus, false)
        val useFlash = intent.getBooleanExtra(UseFlash, false)
        val captureFragment = OcrCaptureFragment.newInstance(autoFocus, useFlash)

        supportFragmentManager.beginTransaction()
                .add(R.id.calibrate_ocr_capture, captureFragment).commit()
    }

    internal fun onDone() {
        val gridData = Intent()
        gridData.putExtra(GridElements, calibratedGridText)
        setResult(CommonStatusCodes.SUCCESS, gridData)

        finish()
    }

    internal fun onRecalibrateRequest() {
        calibratedGridText.clear()
    }

    override fun onOcrGraphicTap(graphic: OcrGraphic, graphicOverlay: OcrGraphicOverlay<OcrGraphic>): Boolean {
        graphicOverlay.remove(graphic)
        val text = graphic.value.toLowerCase()


        Log.d(TAG, "Calibrating:$text")
        graphicOverlay.add(CalibratedOcrGraphic(graphicOverlay, graphic.textBlock))
        calibratedGridText.add(text)


        if (calibratedGridText.size == GRID_SIZE) {
            onDone()
        }

        return true
    }

    companion object {
        protected val TAG = "CalibrationActivity"
        val GridElements = "GridElements"
        private val GRID_SIZE = 1
    }
}
