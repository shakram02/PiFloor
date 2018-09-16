package ocrreader.utils

import android.graphics.Point
import android.graphics.Rect
import android.util.Log
import com.google.android.gms.vision.text.Text

class GridItemHolder {
    private val entries = HashSet<GridText>()
    val size
        get() = entries.size

    val itemsAsString
        get() = entries.asIterable().map { i -> i.value }

    fun addGridItem(item: Text): Boolean {
        return entries.add(GridText(item))
    }

    fun contains(item: Text): Boolean {
        return entries.any { i -> i.value == preProcess(item.value) }
    }

    fun clear() {
        entries.clear()
    }

    fun diff(items: List<Text>): Set<Pair<String, Point>> {
        val externalEntrySet = items
                .filter { item -> item.value != null }
                .map { item -> GridText(item) }
                .toHashSet()

        return entries.subtract(externalEntrySet).toLabeledPoints()
    }

    companion object {
        private fun preProcess(s: String): String {
            return s.toLowerCase().replace(" ", "")
        }
    }

    private fun Iterable<GridText>.toLabeledPoints(): Set<Pair<String, Point>> {
        return this.map { label -> Pair(label.value, label.getCenter()) }.toHashSet()
    }

    /**
     * Instantiating an instance of a [Text] is not possible, this class
     * gives us this ability (just a wrapper class)
     */
    private data class GridText(private val textItem: Text) : Text {
        private val textBoundingBox: Rect = textItem.boundingBox
        private val textComponents: MutableList<out Text> = textItem.components
        private val textCornerPoints: Array<Point> = textItem.cornerPoints
        private val textValue: String = GridItemHolder.preProcess(textItem.value)

        override fun getBoundingBox(): Rect {
            return textBoundingBox
        }

        override fun getComponents(): MutableList<out Text> {
            return textComponents
        }

        override fun getCornerPoints(): Array<Point> {
            return textCornerPoints
        }

        override fun getValue(): String {
            return textValue
        }

        fun getCenter(): Point {
            return Point(textBoundingBox.centerX(), textBoundingBox.centerY())
        }

        override fun hashCode(): Int {
            return textValue.hashCode()
        }

        override fun equals(other: Any?): Boolean {
            return this.hashCode() == other!!.hashCode()
        }
    }
}
