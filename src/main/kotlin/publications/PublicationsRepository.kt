package publications

import core.TimeProvider

class PublicationsRepository(
    private val timeProvider: TimeProvider
) {

    var lastPublicationTimestamp: Long? = null
        private set
    var lastPublicationTimeToLive: Int? = null
        set(value) {
            lastPublicationTimestamp = timeProvider.currentTimeMillis()
            field = value
        }

    fun isNeededToPublish(): Boolean {
        if (lastPublicationTimestamp == null || lastPublicationTimeToLive == null) {
            return true
        }

        val currentTimeMillis = timeProvider.currentTimeMillis()

        return (currentTimeMillis - lastPublicationTimestamp!!) > lastPublicationTimeToLive!!
    }
}