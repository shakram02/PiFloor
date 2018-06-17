package ocrreader.processing


import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.google.android.gms.vision.text.TextBlock
import ocrreader.EdGridApplication
import ocrreader.GridItemHolder
import ocrreader.MainActivity.Companion.AutoFocus
import ocrreader.MainActivity.Companion.UseFlash
import ocrreader.R
import ocrreader.graphcis.CalibratedOcrGraphic
import ocrreader.graphcis.OcrGraphic
import ocrreader.graphcis.PreviewOcrGraphic
import ocrreader.ui.camera.OcrGraphicOverlay
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import javax.inject.Inject


class CalibrationModeActivity : FragmentActivity(), OcrCaptureFragment.OcrSelectionListener, Subscriber<ArrayList<TextBlock>> {
    @Inject
    lateinit var gridItemHolder: GridItemHolder
    private lateinit var captureFragment: OcrCaptureFragment

    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.activity_calibrate_mode)
        (application as EdGridApplication).component.inject(this)
        loadFragment()
    }

    private fun loadFragment() {
        // read parameters from the intent used to launch the activity.
        // TODO: fix parameter resolution (it's not working)
        val autoFocus = intent.getBooleanExtra(AutoFocus, true)
        val useFlash = intent.getBooleanExtra(UseFlash, false)
        captureFragment = OcrCaptureFragment.newInstance(autoFocus, useFlash)

        supportFragmentManager.beginTransaction()
                .add(R.id.container_calibrate_fragment_holder, captureFragment)
                .runOnCommit {
                    captureFragment.subscribe(this)
                }.commit()

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


    /**
     * Invoked after calling [Publisher.subscribe].
     *
     *
     * No data will start flowing until [Subscription.request] is invoked.
     *
     *
     * It is the responsibility of this [Subscriber] instance to call [Subscription.request] whenever more data is wanted.
     *
     *
     * The [Publisher] will send notifications only in response to [Subscription.request].
     *
     * @param s
     * [Subscription] that allows requesting data via [Subscription.request]
     */
    override fun onSubscribe(s: Subscription?) {}

    /**
     * Data notification sent by the [Publisher] in response to requests to [Subscription.request].
     *
     * @param items the element signaled
     */
    override fun onNext(items: ArrayList<TextBlock>) {
        val graphicOverlay = captureFragment.graphicOverlay
        val newFrameGraphics = ArrayList<OcrGraphic>()

        for (i in 0 until items.size) {
            val item = items[i]
            val maybeGraphic = graphicOverlay.getByContent(item.value)
            val text = item.value.toLowerCase()
            // Check if the text was detected before.
            // If it was detected: update location
            // If it was calibrated then went out of view: Add calibrated
            if (maybeGraphic.isPresent) {
                // Update location and after
                // checking the graphic's type Preview/Calibrated
                val graphic = maybeGraphic.get()

                if (graphic is PreviewOcrGraphic) {
                    newFrameGraphics.add(PreviewOcrGraphic(graphicOverlay, item))
                } else {
                    newFrameGraphics.add(CalibratedOcrGraphic(graphicOverlay, item))
                }

            } else if (gridItemHolder.contains(text)) {
                newFrameGraphics.add(CalibratedOcrGraphic(graphicOverlay, item))
            } else {
                newFrameGraphics.add(PreviewOcrGraphic(graphicOverlay, item))
            }
        }

        graphicOverlay.clear()
        for (g in newFrameGraphics) {
            graphicOverlay.add(g)
        }
    }

    /**
     * Failed terminal state.
     *
     *
     * No further events will be sent even if [Subscription.request] is invoked again.
     *
     * @param t the throwable signaled
     */
    override fun onError(t: Throwable?) {
        throw t!!
    }

    override fun onComplete() {
        Log.d(TAG, "Data stream ended")
    }

    companion object {
        private const val TAG = "CalibrationActivity"
        private const val GRID_SIZE = 2 // TODO: fix this later
    }
}
