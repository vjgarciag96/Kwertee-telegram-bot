package tees.domain

import java.util.Timer

class ScheduleTeesFetching(
    private val fetchTeesTask: FetchTeesTask
) {

    operator fun invoke() {
        Timer().scheduleAtFixedRate(
            fetchTeesTask,
            NO_DELAY,
            DAY_IN_MILLIS
        )
    }

    companion object {
        private const val NO_DELAY: Long = 0
        private const val DAY_IN_MILLIS: Long = 24 * 3600 * 1000
    }
}