package subscriptions.data.local

import subscriptions.domain.Subscription

fun SubscriptionDO.toDomain(): Subscription = Subscription(userId, username)