package domain

import java.util.Timer

class FetchGoneForeverTShirts(
    private val fetchGoneForeverTShirtsTask: FetchGoneForeverTShirtsTask
) {

    operator fun invoke() {
        Timer().scheduleAtFixedRate(
            fetchGoneForeverTShirtsTask,
            NO_DELAY,
            DAY_IN_MILLIS
        )
    }

    companion object {
        private const val NO_DELAY: Long = 0
        private const val DAY_IN_MILLIS: Long = 24 * 3600 * 1000
    }
}