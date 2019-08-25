package publications.domain

import publications.data.repository.PublicationsRepository

class GetLastPublicationInfo(
    private val publicationsRepository: PublicationsRepository
) {

    suspend operator fun invoke(): Publication? = publicationsRepository.getLastPublicationInfo()
}