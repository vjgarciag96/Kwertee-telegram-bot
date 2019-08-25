package publications.domain

import publications.data.repository.PublicationsRepository

class SetLastPublicationTimeToLive(
    private val publicationsRepository: PublicationsRepository
) {

    suspend operator fun invoke(timeToLive: Int) {
        publicationsRepository.setLastPublicationTimeToLive(timeToLive)
    }
}