package publications

import core.TimeProvider
import tees.domain.TimeToLiveHandler

class PublicationsRepository(
    private val timeProvider: TimeProvider,
    private val timeToLiveHandler: TimeToLiveHandler
) {

    var lastPublicationTimestamp: Long? = null
        private set
    var lastPublicationTimeToLive: Int? = null
        set(value) {
            lastPublicationTimestamp = timeProvider.currentTimeMillis()
            field = value
        }

    fun isNeededToPublish(): Boolean {
        val timestamp = lastPublicationTimestamp ?: return true
        val timeToLive = lastPublicationTimeToLive ?: return true

        return !timeToLiveHandler.isValid(timestamp, timeToLive)
    }
}