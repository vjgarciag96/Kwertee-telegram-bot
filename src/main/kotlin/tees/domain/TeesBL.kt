package tees.domain

import publications.PublicationsRepository
import tees.data.repository.TeesRepository

class TeesBL(
    private val teesRepository: TeesRepository,
    private val publicationsRepository: PublicationsRepository
) {

    suspend fun fetchPromoted(): PromotedTees {
        val promotedTees = teesRepository.fetchPromoted()
        publicationsRepository.lastPublicationTimeToLive = promotedTees.timeToLive
        return promotedTees.toDomainModel()
    }
}