package subscriptions.domain

import subscriptions.data.repository.SubscriptionsRepository

class Subscribe(private val subscriptionsRepository: SubscriptionsRepository) {

    operator fun invoke(userId: Long, username: String): Boolean =
        subscriptionsRepository.put(Subscription(userId, username))
}