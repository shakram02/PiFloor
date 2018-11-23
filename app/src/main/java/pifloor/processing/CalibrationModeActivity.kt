package pifloor.processing


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.widget.Button
import android.widget.CompoundButton
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.android.gms.vision.text.TextBlock
import pifloor.R
import pifloor.graphcis.CalibratedOcrGraphic
import pifloor.graphcis.OcrGraphic
import pifloor.graphcis.PreviewOcrGraphic
import pifloor.injection.PiFloorApplication
import pifloor.ui.camera.OcrGraphicOverlay
import pifloor.utils.VirtualGrid
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import javax.inject.Inject


class CalibrationModeActivity : FragmentActivity(), OcrCaptureFragment.OcrSelectionListener, Subscriber<ArrayList<TextBlock>> {
    @Inject
    lateinit var virtualGrid: VirtualGrid
    private lateinit var captureFragment: OcrCaptureFragment

    @BindView(R.id.btn_startGame_calibrationModeActivity)
    lateinit var startGameButton : Button

    @BindView(R.id.btn_clear_calibrationModeActivity)
    lateinit var clearButton : Button

    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.activity_calibrate_mode)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        ButterKnife.bind(this)
        (application as PiFloorApplication).component.inject(this)
        loadFragment()
    }

    private fun loadFragment() {
        captureFragment = OcrCaptureFragment.newInstance()

        supportFragmentManager.beginTransaction()
                .add(R.id.container_calibrate_fragment_holder, captureFragment)
                .runOnCommit {
                    captureFragment.subscribe(this)
                }.commit()

    }

    override fun onOcrGraphicTap(ocrGraphic: OcrGraphic, graphicOverlay: OcrGraphicOverlay<OcrGraphic>): Boolean {
        graphicOverlay.remove(ocrGraphic)
        val text = ocrGraphic.value

        Log.d(TAG, "Calibrating:$text")
        graphicOverlay.add(CalibratedOcrGraphic(graphicOverlay, ocrGraphic.textBlock))
        virtualGrid.addTile(ocrGraphic.textBlock)

        if (virtualGrid.size == GRID_SIZE) {
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

            // Check if the text was detected before.
            // If it was detected: update location
            // If it was calibrated then went out of view: Add calibrated
            when {
                virtualGrid.contains(item) -> newFrameGraphics.add(CalibratedOcrGraphic(graphicOverlay, item))
                maybeGraphic != null -> newFrameGraphics.add(PreviewOcrGraphic(graphicOverlay, item))
                else -> newFrameGraphics.add(PreviewOcrGraphic(graphicOverlay, item))
            }
        }

        graphicOverlay.clear()
        for (g in newFrameGraphics) {
            graphicOverlay.add(g)
        }
    }


    @OnClick(R.id.btn_startGame_calibrationModeActivity)
    fun startGameActivity() {
        val intent = Intent(this, GameModeActivity::class.java)
        intent.putExtra("AutoFocus", findViewById<CompoundButton>(R.id.switch_autoFocus)?.isChecked)
        intent.putExtra("UseFlash", findViewById<CompoundButton>(R.id.switch_useFlash)?.isChecked)
        finish()
        startActivity(intent)
    }

    @OnClick(R.id.btn_clear_calibrationModeActivity)
    fun clearCalibration() {
        virtualGrid.clear()
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
        private const val GRID_SIZE = 7 // TODO: fix this later
    }
}
