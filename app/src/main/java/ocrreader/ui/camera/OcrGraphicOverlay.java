package ocrreader.ui.camera;

import android.content.Context;
import android.util.AttributeSet;

import com.google.common.base.Optional;

import ocrreader.graphcis.OcrGraphic;

public class OcrGraphicOverlay<U extends OcrGraphic> extends GraphicOverlay {

    public OcrGraphicOverlay(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Optional<OcrGraphic> getByContent(String text) {

        for (OcrGraphic g : (Iterable<U>) mGraphics) {
            if (g.getValue().equals(text)) {
                return Optional.of(g);
            }
        }

        return Optional.absent();
    }

    public void add(U graphic) {
        super.add(graphic);
    }

    public void remove(U graphic) {
        super.remove(graphic);
    }
}
