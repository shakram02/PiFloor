package pifloor.graphcis

import android.graphics.Color
import android.graphics.Paint
import com.google.android.gms.vision.text.TextBlock
import pifloor.ui.camera.GraphicOverlay

class PreviewOcrGraphic(overlay: GraphicOverlay<*>, text: TextBlock) : OcrGraphic(overlay, text) {

    init {
        if (rectPaint == null) {
            rectPaint = Paint()
            rectPaint!!.color = Color.WHITE
            rectPaint!!.style = Paint.Style.STROKE
            rectPaint!!.strokeWidth = 4.0f
        }

        if (textPaint == null) {
            textPaint = Paint()
            textPaint!!.color = Color.BLACK
            textPaint!!.textSize = 54.0f
        }
    }

    override fun getRectPaint(): Paint {
        return rectPaint!!
    }

    override fun getTextPaint(): Paint {
        return textPaint!!
    }

    companion object {
        private var rectPaint: Paint? = null
        private var textPaint: Paint? = null
    }
}
