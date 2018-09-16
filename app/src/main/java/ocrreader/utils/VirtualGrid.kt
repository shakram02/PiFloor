package ocrreader.utils

import android.graphics.Point
import android.graphics.Rect
import com.google.android.gms.vision.text.Text

class VirtualGrid {
    private val tiles = HashSet<GridTile>()
    val size
        get() = tiles.size

    val tilesAsString
        get() = tiles.asIterable().map { i -> i.value }

    fun addTile(tile: Text): Boolean {
        return tiles.add(GridTile(tile))
    }

    fun contains(tile: Text): Boolean {
        return tiles.any { i -> i.value == preProcess(tile.value) }
    }

    fun clear() {
        tiles.clear()
    }

    fun diff(tiles: List<Text>): Set<Pair<String, Point>> {
        val detectedTiles = tiles
                .filter { tile -> tile.value != null }
                .map { tile -> GridTile(tile) }
                .toHashSet()

        return this.tiles.subtract(detectedTiles).toLabeledTiles()
    }

    companion object {
        private fun preProcess(s: String): String {
            return s.toLowerCase().replace(" ", "")
        }
    }

    private fun Iterable<GridTile>.toLabeledTiles(): Set<Pair<String, Point>> {
        return this.map { tile -> Pair(tile.value, tile.getCenter()) }.toHashSet()
    }

    /**
     * Instantiating an instance of a [Text] is not possible, this class
     * gives us this ability (just a wrapper class)
     */
    private data class GridTile(private val textItem: Text) : Text {
        private val textBoundingBox: Rect = textItem.boundingBox
        private val textComponents: MutableList<out Text> = textItem.components
        private val textCornerPoints: Array<Point> = textItem.cornerPoints
        private val textValue: String = VirtualGrid.preProcess(textItem.value)

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
