package domain

import data.repository.SubscriptionsRepository

class Subscribe(private val subscriptionsRepository: SubscriptionsRepository) {

    operator fun invoke(userId: Long, username: String) {
        subscriptionsRepository.put(Subscription(userId, username))
    }
}