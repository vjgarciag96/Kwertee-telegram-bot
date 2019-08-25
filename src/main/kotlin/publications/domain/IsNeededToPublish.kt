package publications.domain

import publications.data.PublicationsRepository
import tees.domain.TimeToLiveHandler

class IsNeededToPublish(
    private val timeToLiveHandler: TimeToLiveHandler,
    private val publicationsRepository: PublicationsRepository
) {

    suspend operator fun invoke(): Boolean {
        val timestamp = publicationsRepository.lastPublicationTimestamp ?: return true
        val timeToLive = publicationsRepository.lastPublicationTimeToLive ?: return true

        return !timeToLiveHandler.isValid(timestamp, timeToLive)
    }
}