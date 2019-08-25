package publications.domain

import publications.data.PublicationsRepository

class SetLastPublicationTimeToLive(
    private val publicationsRepository: PublicationsRepository
) {

    suspend operator fun invoke(timeToLive: Int) {
        publicationsRepository.lastPublicationTimeToLive = timeToLive
    }
}