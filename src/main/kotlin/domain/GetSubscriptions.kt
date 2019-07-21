package domain

import data.repository.SubscriptionsRepository

class GetSubscriptions(private val subscriptionsRepository: SubscriptionsRepository) {

    operator fun invoke(): List<Subscription> = subscriptionsRepository.fetchAll()
}