package pifloor.processing


import android.content.Intent
import android.hardware.Camera
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import co.dift.ui.SwipeToAction
import com.google.android.gms.vision.text.TextBlock
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import pifloor.R
import pifloor.graphcis.CalibratedOcrGraphic
import pifloor.graphcis.OcrGraphic
import pifloor.graphcis.PreviewOcrGraphic
import pifloor.injection.PiFloorApplication
import pifloor.ui.camera.OcrGraphicOverlay
import pifloor.utils.VirtualGrid
import javax.inject.Inject

class CalibrationModeActivity : AppCompatActivity(), OcrCaptureFragment.OcrSelectionListener, Subscriber<ArrayList<TextBlock>> {
    @Inject
    lateinit var virtualGrid: VirtualGrid
    private lateinit var captureFragment: OcrCaptureFragment

    var mTopToolbar: Toolbar? = null
    var focus: Boolean = false
    var flash: Boolean = false

    @BindView(R.id.recycler)
    lateinit var recyclerView: RecyclerView
    var swipeToAction: SwipeToAction? = null

    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.activity_calibrate_mode)
        mTopToolbar = findViewById(R.id.my_toolbar)
        setSupportActionBar(mTopToolbar)
        ButterKnife.bind(this)
        (application as PiFloorApplication).component.inject(this)
        loadFragment()
        loadList()
    }

    private fun loadList() {
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = captureFragment.adapter

        swipeToAction = SwipeToAction(recyclerView, object : SwipeToAction.SwipeListener<String> {
            override fun onClick(itemData: String?) {
                displaySnackbar(itemData, null, null)
            }

            override fun swipeRight(itemData: String?): Boolean {
                removeTile(itemData!!)
                return true
            }

            override fun onLongClick(itemData: String?) {}

            override fun swipeLeft(itemData: String?): Boolean {
                removeTile(itemData!!)
                return true
            }

        })
    }

    private fun removeTile(str: String) {
        val position = captureFragment.tiles!!.indexOf(str)
        captureFragment.tiles!!.remove(str)
        captureFragment.adapter!!.notifyItemRemoved(position)
        virtualGrid.removeTile(str)
    }

    private fun displaySnackbar(text: String?, actionName: String?, action: View.OnClickListener?) {
        val snack: Snackbar = Snackbar.make(findViewById(android.R.id.content), text!!, Snackbar.LENGTH_LONG)
                .setAction(actionName, action)

        val v: View = snack.view
        v.setBackgroundColor(resources.getColor(R.color.green))
        snack.show()
    }

    private fun loadFragment() {
        captureFragment = OcrCaptureFragment.newInstance(focus, flash)

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
        val intent = Intent(this, AssignmentActivity::class.java).apply {
            putExtra("AutoFocus", focus)
            putExtra("UseFlash", flash)
            putExtra("tiles", captureFragment.tiles)
        }
        startActivity(intent)
    }

    private fun clearCalibration() {
        virtualGrid.clear()
        captureFragment.tiles!!.clear()
        captureFragment.adapter!!.notifyDataSetChanged()
        captureFragment.counter = 0
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.calb_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.flash) {
            if (!flash) {
                flash = true
                item.setIcon(R.drawable.flash_on)
                captureFragment.mCameraSource!!.flashMode = Camera.Parameters.FLASH_MODE_TORCH
            } else {
                flash = false
                item.setIcon(R.drawable.flash)
                captureFragment.mCameraSource!!.flashMode = Camera.Parameters.FLASH_MODE_OFF
            }
            return true
        } else if (id == R.id.focus) {
            if (!focus) {
                focus = true
                item.setIcon(R.drawable.focus_on)
                captureFragment.mCameraSource!!.focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO
            } else {
                focus = false
                item.setIcon(R.drawable.focus)
                captureFragment.mCameraSource!!.focusMode = null
            }
        } else if (id == R.id.restart) {
            clearCalibration()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
