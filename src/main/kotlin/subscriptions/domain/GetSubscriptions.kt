package subscriptions.domain

import subscriptions.data.repository.SubscriptionsRepository

class GetSubscriptions(private val subscriptionsRepository: SubscriptionsRepository) {

    operator fun invoke(): List<Subscription> = subscriptionsRepository.fetchAll()
}