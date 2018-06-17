package ocrreader

import com.google.android.gms.vision.text.TextBlock

class GridItemHolder {
    private val entries = HashSet<String>()
    val size
        get() = entries.size
    val itemsAsString
        get() = entries.asIterable()

    fun addGridItem(item: String): Boolean {
        return entries.add(item.toLowerCase().replace(" ", ""))
    }

    fun contains(item: String): Boolean {
        return entries.contains(item)
    }

    fun clear() {
        entries.clear()
    }

    fun diff(items: ArrayList<TextBlock>): Set<String> {
        return entries.subtract(items
                .filter { item -> item.value != null }
                .map { item -> item.value.toLowerCase() }
                .toHashSet())
    }
}