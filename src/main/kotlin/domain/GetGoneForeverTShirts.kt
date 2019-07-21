package domain

import data.repository.TShirtRepository

class GetGoneForeverTShirts(private val tShirtRepository: TShirtRepository) {

    suspend operator fun invoke(): List<TShirt> = tShirtRepository.fetchGoneForever()
}