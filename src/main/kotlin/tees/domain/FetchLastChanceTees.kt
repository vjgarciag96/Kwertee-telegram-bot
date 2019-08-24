package tees.domain

import tees.data.repository.TeeData
import tees.data.repository.TeesRepository

class FetchLastChanceTees(
    private val teesRepository: TeesRepository
) {

    suspend operator fun invoke(): List<TeeData> = teesRepository.fetchLastChance()
}