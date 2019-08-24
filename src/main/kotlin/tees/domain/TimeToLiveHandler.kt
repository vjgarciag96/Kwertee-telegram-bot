package tees.domain

import core.TimeProvider

class TimeToLiveHandler(private val timeProvider: TimeProvider) {

    fun isValid(dataTimestamp: Long, timeToLive: Int): Boolean {
        val currentTimeMillis = timeProvider.currentTimeMillis()
        return currentTimeMillis.minus(dataTimestamp) < sanitizedTimeToLive(timeToLive)
    }

    fun delayUntilExpiration(dataTimestamp: Long, timeToLive: Int): Long {
        val currentTimeMillis = timeProvider.currentTimeMillis()
        return maxOf(0, dataTimestamp + sanitizedTimeToLive(timeToLive) - currentTimeMillis)
    }

    private fun sanitizedTimeToLive(timeToLive: Int): Int = (timeToLive - QWERTEE_TTL_OFFSET) * 1000

    private companion object {
        const val QWERTEE_TTL_OFFSET = 24 * 3600
    }
}