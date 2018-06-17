package ocrreader

import android.app.Application

class GridItemHolder(private val application: Application) {
    private val entries = HashSet<String>()
    val size
        get() = entries.size
    val items
        get() = entries.joinToString()

    fun addGridItem(item: String): Boolean {
        return entries.add(item.toLowerCase())
    }

    fun contains(item: String): Boolean {
        return entries.contains(item)
    }
}