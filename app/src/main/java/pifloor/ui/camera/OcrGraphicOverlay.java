package pifloor.ui.camera;

import android.content.Context;
import android.util.AttributeSet;

import pifloor.graphcis.OcrGraphic;

public class OcrGraphicOverlay<U extends OcrGraphic> extends GraphicOverlay {

    public OcrGraphicOverlay(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OcrGraphic getByContent(String text) {

        for (OcrGraphic g : (Iterable<U>) mGraphics) {
            if (g.getValue().equals(text)) {
                return g;
            }
        }

        return null;
    }

    public void add(U graphic) {
        super.add(graphic);
    }

    public void remove(U graphic) {
        super.remove(graphic);
    }
}
