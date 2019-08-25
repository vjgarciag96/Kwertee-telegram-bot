package tees.domain

import publications.domain.SetLastPublicationTimeToLive
import tees.data.repository.TeesRepository

class TeesBL(
    private val teesRepository: TeesRepository,
    private val setLastPublicationTimeToLive: SetLastPublicationTimeToLive
) {

    suspend fun fetchPromoted(): PromotedTees {
        val promotedTees = teesRepository.fetchPromoted()
        setLastPublicationTimeToLive(promotedTees.timeToLive)
        return promotedTees.toDomainModel()
    }
}