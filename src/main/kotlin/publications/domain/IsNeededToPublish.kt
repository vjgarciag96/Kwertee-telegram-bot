package publications.domain

import publications.data.repository.PublicationsRepository
import tees.domain.TimeToLiveHandler

class IsNeededToPublish(
    private val timeToLiveHandler: TimeToLiveHandler,
    private val publicationsRepository: PublicationsRepository
) {

    suspend operator fun invoke(): Boolean {
        val lastPublicationInfo = publicationsRepository.getLastPublicationInfo() ?: return true

        return !timeToLiveHandler.isValid(lastPublicationInfo.timestamp, lastPublicationInfo.timeToLive)
    }
}