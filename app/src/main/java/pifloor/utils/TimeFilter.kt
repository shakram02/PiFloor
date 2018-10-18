package pifloor.utils

class TimeFilter<T>(private val coolDownMillis: Long) {
    private val entries: HashMap<T, Long> = hashMapOf()

    fun isColdEnough(value: T): Boolean {
        val now = System.currentTimeMillis()
        // New entry, add it
        if (!entries.contains(value)) {
            entries[value] = now
            return false
        }

        val insertionTime = entries[value]!!
        // Already existing entry, check temperature
        if (now - insertionTime > coolDownMillis) {
            entries.clear()
            return true // Cold enough, passed timeout
        }

        // Noisy value, ignore it
        return false
    }
}