package ocrreader.processing

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.util.Log
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.vision.text.TextRecognizer

import java.io.IOException

import ocrreader.R
import ocrreader.graphcis.OcrGraphic
import ocrreader.ui.camera.CameraSource
import ocrreader.ui.camera.CameraSourcePreview
import ocrreader.ui.camera.OcrGraphicOverlay


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [OcrSelectionListener] interface
 * to handle interaction events.
 * Use the [OcrCaptureFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OcrCaptureFragment : Fragment(), View.OnTouchListener {

    private var mCameraSource: CameraSource? = null
    private var mPreview: CameraSourcePreview? = null
    protected var mGraphicOverlay: OcrGraphicOverlay<OcrGraphic>

    // Helper objects for detecting taps and pinches.
    private var scaleGestureDetector: ScaleGestureDetector? = null
    private var gestureDetector: GestureDetector? = null

    private var autoFocus: Boolean? = null
    private var useFlash: Boolean? = null

    private var mListener: OcrSelectionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            autoFocus = arguments.getBoolean(AutoFocus, false)
            useFlash = arguments.getBoolean(UseFlash, false)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val fragmentView = inflater!!.inflate(R.layout.fragment_ocr_capture, container, false)

        mPreview = fragmentView.findViewById(R.id.preview) as CameraSourcePreview
        mGraphicOverlay = fragmentView.findViewById(R.id.graphicOverlay) as OcrGraphicOverlay<OcrGraphic>

        val applicationContext = this.activity.applicationContext

        // Check for the camera permission before accessing the camera.  If the
        // permission is not granted yet, request permission.
        val rc = ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)
        if (rc == PackageManager.PERMISSION_GRANTED) {
            createCameraSource(autoFocus!!, useFlash!!)
        } else {
            requestCameraPermission()
        }

        fragmentView.setOnTouchListener(this)
        gestureDetector = GestureDetector(this.context, CaptureGestureListener(mListener))
        scaleGestureDetector = ScaleGestureDetector(this.context, ScaleListener())

        Snackbar.make(mGraphicOverlay, "Tap to capture. Pinch/Stretch to zoom",
                Snackbar.LENGTH_LONG)
                .show()

        return fragmentView
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        startCameraSource()
        if (context is OcrSelectionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OcrSelectionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
        if (mPreview != null) {
            mPreview!!.release()
        }
    }

    override fun onPause() {
        super.onPause()
        if (mPreview != null) {
            mPreview!!.stop()
        }
    }

    override fun onResume() {
        super.onResume()
        startCameraSource()
    }

    /**
     * Handles the requesting of the camera permission.  This includes
     * showing a "Snackbar" message of why the permission is needed then
     * sending the request.
     */
    private fun requestCameraPermission() {
        Log.w(TAG, "Camera permission is not granted. Requesting permission")

        val permissions = arrayOf(Manifest.permission.CAMERA)
        val thisActivity = this.activity

        if (!ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
                        Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(thisActivity, permissions, RC_HANDLE_CAMERA_PERM)
            return
        }


        val listener = View.OnClickListener {
            ActivityCompat.requestPermissions(thisActivity, permissions,
                    RC_HANDLE_CAMERA_PERM)
        }

        Snackbar.make(mGraphicOverlay, R.string.permission_camera_rationale,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.ok, listener)
                .show()
    }

    /**
     * Creates and starts the camera.  Note that this uses a higher resolution in comparison
     * to other detection examples to enable the ocr detector to detect small text samples
     * at long distances.
     *
     *
     * Suppressing InlinedApi since there is a check that the minimum version is met before using
     * the constant.
     */
    @SuppressLint("InlinedApi")
    private fun createCameraSource(autoFocus: Boolean, useFlash: Boolean) {
        val context = this.activity.applicationContext

        // A text recognizer is created to find text.  An associated processor instance
        // is set to receive the text recognition results and display graphics for each text block
        // on screen.
        val textRecognizer = TextRecognizer.Builder(context).build()
        textRecognizer.setProcessor(OcrDetectorProcessor(mGraphicOverlay))

        if (!textRecognizer.isOperational) {
            // Note: The first time that an app using a Vision API is installed on a
            // device, GMS will download a native libraries to the device in order to do detection.
            // Usually this completes before the app is run for the first time.  But if that
            // download has not yet completed, then the above call will not detect any text,
            // barcodes, or faces.
            //
            // isOperational() can be used to check if the required native libraries are currently
            // available.  The detectors will automatically become operational once the library
            // downloads complete on device.
            Log.w(TAG, "Detector dependencies are not yet available.")

            // Check for low storage.  If there is low storage, the native library will not be
            // downloaded, so detection will not become operational.
            val lowstorageFilter = IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW)
            val hasLowStorage = context.registerReceiver(null, lowstorageFilter) != null

            if (hasLowStorage) {
                Toast.makeText(this.activity, R.string.low_storage_error, Toast.LENGTH_LONG).show()
                Log.w(TAG, getString(R.string.low_storage_error))
            }
        }

        // Creates and starts the camera.  Note that this uses a higher resolution in comparison
        // to other detection examples to enable the text recognizer to detect small pieces of text.
        mCameraSource = CameraSource.Builder(context, textRecognizer)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1280, 1024)
                .setRequestedFps(2.0f)
                .setFlashMode(if (useFlash) Camera.Parameters.FLASH_MODE_TORCH else null)
                .setFocusMode(if (autoFocus) Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE else null)
                .build()
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {

        val b = scaleGestureDetector!!.onTouchEvent(event)

        val c = gestureDetector!!.onTouchEvent(event)

        return b || c || this.activity.onTouchEvent(event)
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OcrSelectionListener {
        /**
         * onOcrGraphicTap is called to capture the first TextBlock under the tap location and return it to
         * the Initializing Activity.
         *
         * @param ocrGraphic     - tapped Ocr item
         * @param graphicOverlay - graphic overlay layer.
         * @return true if the activity is ending.
         */
        fun onOcrGraphicTap(ocrGraphic: OcrGraphic, graphicOverlay: OcrGraphicOverlay<OcrGraphic>): Boolean
    }


    private inner class CaptureGestureListener internal constructor(private val ocrSelectionListener: OcrSelectionListener) : GestureDetector.SimpleOnGestureListener() {

        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            val graphic = mGraphicOverlay.getGraphicAtLocation(e.rawX, e.rawY) as OcrGraphic

            if (graphic == null) {
                Log.i(TAG, "No graphic detected")
                return super.onSingleTapConfirmed(e)
            }

            return ocrSelectionListener.onOcrGraphicTap(graphic, mGraphicOverlay)
        }
    }

    private inner class ScaleListener : ScaleGestureDetector.OnScaleGestureListener {

        /**
         * Responds to scaling events for a gesture in progress.
         * Reported by pointer motion.
         *
         * @param detector The detector reporting the event - use this to
         * retrieve extended info about event state.
         * @return Whether or not the detector should consider this event
         * as handled. If an event was not handled, the detector
         * will continue to accumulate movement until an event is
         * handled. This can be useful if an application, for example,
         * only wants to update scaling factors if the change is
         * greater than 0.01.
         */
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            return false
        }

        /**
         * Responds to the beginning of a scaling gesture. Reported by
         * new pointers going down.
         *
         * @param detector The detector reporting the event - use this to
         * retrieve extended info about event state.
         * @return Whether or not the detector should continue recognizing
         * this gesture. For example, if a gesture is beginning
         * with a focal point outside of a region where it makes
         * sense, onScaleBegin() may return false to ignore the
         * rest of the gesture.
         */
        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            return true
        }

        /**
         * Responds to the end of a scale gesture. Reported by existing
         * pointers going up.
         *
         *
         * Once a scale has ended, [ScaleGestureDetector.getFocusX]
         * and [ScaleGestureDetector.getFocusY] will return focal point
         * of the pointers remaining on the screen.
         *
         * @param detector The detector reporting the event - use this to
         * retrieve extended info about event state.
         */
        override fun onScaleEnd(detector: ScaleGestureDetector) {
            mCameraSource!!.doZoom(detector.scaleFactor)
        }
    }

    /**
     * Starts or restarts the camera source, if it exists.  If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    @Throws(SecurityException::class)
    private fun startCameraSource() {
        // Check that the device has play services available.
        val activity = this.activity

        val code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                activity.applicationContext)
        if (code != ConnectionResult.SUCCESS) {
            val dlg = GoogleApiAvailability.getInstance().getErrorDialog(activity, code, RC_HANDLE_GMS)
            dlg.show()
        }

        if (mCameraSource != null) {
            try {
                mPreview!!.start(mCameraSource, mGraphicOverlay)
            } catch (e: IOException) {
                Log.e(TAG, "Unable to start camera source.", e)
                mCameraSource!!.release()
                mCameraSource = null
            }

        }
    }

    companion object {
        protected val TAG = "OcrCaptureActivity"

        // Intent request code to handle updating play services if needed.
        private val RC_HANDLE_GMS = 9001

        // Permission request codes need to be < 256
        private val RC_HANDLE_CAMERA_PERM = 2

        // Constants used to pass extra data in the intent
        val AutoFocus = "AutoFocus"
        val UseFlash = "UseFlash"

        /**
         * Ocr capture activity that has a surface view to capture text
         *
         * @param autoFocus Toggles auto focus.
         * @param useFlash  Toggles flash.
         * @return A new instance of fragment OcrCaptureFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(autoFocus: Boolean, useFlash: Boolean): OcrCaptureFragment {
            val fragment = OcrCaptureFragment()
            val args = Bundle()
            args.putBoolean(AutoFocus, autoFocus)
            args.putBoolean(UseFlash, useFlash)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
