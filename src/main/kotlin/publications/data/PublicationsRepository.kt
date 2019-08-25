package publications.data

import core.TimeProvider

class PublicationsRepository(private val timeProvider: TimeProvider) {

    var lastPublicationTimestamp: Long? = null
        private set
    var lastPublicationTimeToLive: Int? = null
        set(value) {
            lastPublicationTimestamp = timeProvider.currentTimeMillis()
            field = value
        }
}