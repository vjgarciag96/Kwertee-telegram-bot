package domain

import data.repository.SubscriptionsRepository

class Unsubscribe(
    private val subscriptionsRepository: SubscriptionsRepository
) {

    operator fun invoke(userId: Long) {
        subscriptionsRepository.remove(userId)
    }
}