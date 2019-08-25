package publications.domain

import publications.data.PublicationsRepository

class GetLastPublicationTimeToLive(
    private val publicationsRepository: PublicationsRepository
) {

    suspend operator fun invoke(): Int? = publicationsRepository.lastPublicationTimeToLive
}