package ocrreader.processing


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.google.android.gms.common.api.CommonStatusCodes
import ocrreader.MainActivity.Companion.AutoFocus
import ocrreader.MainActivity.Companion.UseFlash
import ocrreader.R
import ocrreader.graphcis.CalibratedOcrGraphic
import ocrreader.graphcis.OcrGraphic
import ocrreader.ui.camera.OcrGraphicOverlay
import java.util.*


class CalibrationModeActivity : FragmentActivity(), OcrCaptureFragment.OcrSelectionListener {
    private var calibratedGridText = HashSet<String>()


    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.activity_calibrate_mode)

        loadFragment()
    }

    private fun loadFragment() {
        // read parameters from the intent used to launch the activity.
        val autoFocus = intent.getBooleanExtra(AutoFocus, false)
        val useFlash = intent.getBooleanExtra(UseFlash, false)
        val captureFragment = OcrCaptureFragment.newInstance(autoFocus, useFlash)

        supportFragmentManager.beginTransaction()
                .add(R.id.container_calibrate_fragment_holder, captureFragment).commit()
    }

    private fun onDone() {
        val gridData = Intent()
        gridData.putExtra(GridElements, calibratedGridText)
        setResult(CommonStatusCodes.SUCCESS, gridData)

        finish()
    }

    internal fun onRecalibrateRequest() {
        calibratedGridText.clear()
    }

    override fun onOcrGraphicTap(ocrGraphic: OcrGraphic, graphicOverlay: OcrGraphicOverlay<OcrGraphic>): Boolean {
        graphicOverlay.remove(ocrGraphic)
        val text = ocrGraphic.value.toLowerCase()


        Log.d(TAG, "Calibrating:$text")
        graphicOverlay.add(CalibratedOcrGraphic(graphicOverlay, ocrGraphic.textBlock!!))
        calibratedGridText.add(text)


        if (calibratedGridText.size == GRID_SIZE) {
            onDone()
        }

        return true
    }

    companion object {
        private const val TAG = "CalibrationActivity"
        const val GridElements = "GridElements"
        private const val GRID_SIZE = 1
    }
}
