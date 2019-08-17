package tees.domain

import tees.data.repository.TeeData
import tees.data.repository.TeesRepository

class FetchGoneForeverTees(private val teesRepository: TeesRepository) {

    suspend operator fun invoke(): List<Tee> = teesRepository.fetchGoneForever().map(TeeData::toDomainModel)
}