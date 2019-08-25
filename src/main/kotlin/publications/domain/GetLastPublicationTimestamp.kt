package publications.domain

import publications.data.PublicationsRepository

class GetLastPublicationTimestamp(
    private val publicationsRepository: PublicationsRepository
) {

    suspend operator fun invoke(): Long? = publicationsRepository.lastPublicationTimestamp
}