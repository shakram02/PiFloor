package ocrreader.processing


import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import ocrreader.EdGridApplication
import ocrreader.GridItemHolder
import ocrreader.MainActivity.Companion.AutoFocus
import ocrreader.MainActivity.Companion.UseFlash
import ocrreader.R
import ocrreader.graphcis.CalibratedOcrGraphic
import ocrreader.graphcis.OcrGraphic
import ocrreader.ui.camera.OcrGraphicOverlay
import javax.inject.Inject


class CalibrationModeActivity : FragmentActivity(), OcrCaptureFragment.OcrSelectionListener {
    @Inject
    lateinit var gridItemHolder: GridItemHolder

    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.activity_calibrate_mode)
        (application as EdGridApplication).component.inject(this)
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

    internal fun onRecalibrateRequest() {
        TODO()
    }

    override fun onOcrGraphicTap(ocrGraphic: OcrGraphic, graphicOverlay: OcrGraphicOverlay<OcrGraphic>): Boolean {
        graphicOverlay.remove(ocrGraphic)
        val text = ocrGraphic.value.toLowerCase()


        Log.d(TAG, "Calibrating:$text")
        graphicOverlay.add(CalibratedOcrGraphic(graphicOverlay, ocrGraphic.textBlock!!))
        gridItemHolder.addGridItem(ocrGraphic.value)


        if (gridItemHolder.size == GRID_SIZE) {
            finish()
        }

        return true
    }

    companion object {
        private const val TAG = "CalibrationActivity"
        private const val GRID_SIZE = 1 // TODO: fix this later
    }
}
