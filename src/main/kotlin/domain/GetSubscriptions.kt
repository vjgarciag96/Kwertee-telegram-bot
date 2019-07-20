package domain

import data.SubscriptionsRepository

class GetSubscriptions(
    private val subscriptionsRepository: SubscriptionsRepository
) {

    operator fun invoke(): List<Subscription> = subscriptionsRepository.fetchAll()
}