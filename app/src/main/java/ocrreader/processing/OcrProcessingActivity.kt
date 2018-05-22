package ocrreader.processing

import android.content.Intent
import android.util.Log

import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.vision.text.TextBlock

import ocrreader.graphcis.OcrGraphic

class OcrProcessingActivity : OcrCaptureActivity() {
    internal override fun onTap(rawX: Float, rawY: Float): Boolean {
        val graphic = mGraphicOverlay.getGraphicAtLocation(rawX, rawY) as OcrGraphic
        var text: TextBlock? = null
        if (graphic != null) {
            text = graphic.textBlock
            if (text != null && text.value != null) {
                val data = Intent()
                data.putExtra(OcrCaptureActivity.TextBlockObject, text.value)
                setResult(CommonStatusCodes.SUCCESS, data)
                finish()
            } else {
                Log.d(OcrCaptureActivity.TAG, "text data is null")
            }
        } else {
            Log.d(OcrCaptureActivity.TAG, "no text detected")
        }
        return text != null
    }
}
