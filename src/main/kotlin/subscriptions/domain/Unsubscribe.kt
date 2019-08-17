package subscriptions.domain

import subscriptions.data.repository.SubscriptionsRepository

class Unsubscribe(
    private val subscriptionsRepository: SubscriptionsRepository
) {

    operator fun invoke(userId: Long): Boolean =
        subscriptionsRepository.remove(userId)
}