package ocrreader

import android.app.Application

class GridItemHolder(private val application: Application) {
    private val entries = HashSet<String>()

    fun addGridItem(item: String): Boolean {
        return entries.add(item.toLowerCase())
    }

}